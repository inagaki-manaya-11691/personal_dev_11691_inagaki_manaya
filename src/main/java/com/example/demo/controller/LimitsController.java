package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Foods;
import com.example.demo.model.Musers;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.FoodsRepository;

@Controller
public class LimitsController {
	private final CategoriesRepository categoriesRepository;
	private final FoodsRepository foodsRepository;
	private final Musers musers;

	public LimitsController(CategoriesRepository categoriesRepository, FoodsRepository foodsRepository, Musers musers) {
		this.categoriesRepository = categoriesRepository;
		this.foodsRepository = foodsRepository;
		this.musers = musers;
	}

	//食品一覧表示
	@GetMapping("/limits")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "0") Integer eatNumber,
			@RequestParam(defaultValue = "0") Integer quantity,
			Model model) {

		// 全カテゴリー一覧を取得
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 食品一覧情報の取得
		List<Foods> foodList = null;
		if (categoryId == null) {
			foodList = foodsRepository.findAll();
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得
			foodList = foodsRepository.findByCategoryId(categoryId);
		}
		for (Foods food : foodList) {
			long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), food.getLimitdate());
			food.setDaysBetween(daysBetween);
			model.addAttribute("daysBetween", daysBetween);
		}
		//残り日数でソート
		List<Foods> foodListSrot = foodList.stream()
				.sorted(Comparator.comparing(Foods::getDaysBetween))
				.toList();

		model.addAttribute("foods", foodListSrot);

		return "limits";
	}

	//新規登録画面表示
	@GetMapping("/limits/new")
	public String create(Model model) {
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		return "createlimits";
	}

	//新規登録処理
	@PostMapping("/limits/add")
	public String add(@RequestParam(defaultValue = "") Integer usersId,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String foodname,
			@RequestParam(defaultValue = "") Boolean limits,
			@RequestParam(defaultValue = "") LocalDate limitdate,
			@RequestParam(defaultValue = "") String quantity,
			Model model) {
		List<String> error = new ArrayList<>();
		boolean han = false;

		if (limitdate == null) {
			error.add("期限は必須です");
			han = true;
		}
		if (foodname.equals("")) {
			error.add("食品名は必須です");
			han = true;
		}
		if (quantity.equals("")) {
			error.add("個数は必須です");
			han = true;
		}

		if (han) {
			model.addAttribute("error", error);
			model.addAttribute("limitdate", limitdate);
			model.addAttribute("foodname", foodname);
			model.addAttribute("quantity", quantity);
			model.addAttribute("limits", limits);
			return "createlimits";
		} else {
			Integer quantity2 = Integer.parseInt(quantity);
			Categories cate = categoriesRepository.findById(categoryId).get();
			Foods food = new Foods(usersId, cate, foodname, limits, LocalDate.now(), limitdate, quantity2);
			foodsRepository.save(food);
			return "redirect:/limits";
		}
	}

	//データ編集画面表示
	@GetMapping("/limits/{foodId}/edit")
	public String edit(@PathVariable Integer foodId, Model model) {
		Foods foods = foodsRepository.findById(foodId).get();
		model.addAttribute("foods", foods);
		return "editlimits";
	}

	//データ編集処理
	@PostMapping("/limits/{foodId}/edit")
	public String update(@PathVariable Integer foodId,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String foodname,
			@RequestParam(defaultValue = "") Boolean limits,
			@RequestParam(defaultValue = "") LocalDate limitdate,
			@RequestParam(defaultValue = "") String quantity) {
		Foods foods = foodsRepository.findById(foodId).get();
		Categories cate = categoriesRepository.findById(categoryId).get();
		foods.setCategory(cate);
		foods.setFoodname(foodname);
		foods.setLimits(limits);
		foods.setLimitdate(limitdate);
		Integer quantity2 = Integer.parseInt(quantity);
		foods.setQuantity(quantity2);
		foodsRepository.save(foods);

		return "redirect:/limits";
	}

	//データ削除処理
	@PostMapping("/limits/{foodId}/delete")
	public String delete(@PathVariable Integer foodId) {
		foodsRepository.deleteById(foodId);

		return "redirect:/limits";
	}

	//食べた数処理	
	@PostMapping("/limits/{id}/eat")
	public String eat(@RequestParam(defaultValue = "") Integer quantity,
			@RequestParam(defaultValue = "") Integer eatNumber,
			@PathVariable Integer id,
			Model model,
			RedirectAttributes redirectAttributes) {
		Foods food = foodsRepository.findById(id).get();
		Integer getquantity = food.getQuantity();
		Integer result = (getquantity - eatNumber);
		if (getquantity < eatNumber) {
			redirectAttributes.addFlashAttribute("errormessage", "消費数が個数を上回っています");
			return "redirect:/limits";
		}
		food.setQuantity(result);
		foodsRepository.save(food);
		return "redirect:/limits";
	}

	// 期限間近の表示							
	@GetMapping("/shop/lost")
	public String limit(@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "0") Integer eatNumber,
			@RequestParam(defaultValue = "0") Integer quantity,
			Model model) {

		//今日から3日後を取得
		LocalDate threeDaysLater = LocalDate.now().plusDays(3);

		// 全カテゴリー一覧を取得
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 食品一覧情報の取得							
		List<Foods> foodList = null;
		if (categoryId == null) {
			//残り期限が3日以内のものを取得
			foodList = foodsRepository.findByUsersIdAndLimitdateLessThanEqual(musers.getId(), threeDaysLater);
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得							
			foodList = foodsRepository.findByCategoryIdAndUsersIdAndLimitdateLessThanEqual(categoryId, musers.getId(),
					threeDaysLater);
		}

		for (Foods food : foodList) {
			long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), food.getLimitdate());
			food.setDaysBetween(daysBetween);
			model.addAttribute("daysBetween", "あと" + daysBetween + "日");
		}

		//残り日数でソート
		List<Foods> foodListSrot = foodList.stream()
				.sorted(Comparator.comparing(Foods::getDaysBetween))
				.toList();

		model.addAttribute("foods", foodListSrot);
		model.addAttribute("musers", musers.getName());

		return "nearlist";
	}

	// クックパッド検索						
	@PostMapping("/shop/lost")
	public String search(@RequestParam String search, @RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "0") Integer eatNumber,
			@RequestParam(defaultValue = "0") Integer quantity,
			Model model) throws UnsupportedEncodingException {
		// 文字列をURLエンコードする						
		String encodedKeyword = URLEncoder.encode(search, StandardCharsets.UTF_8.toString());
		String url = "https://cookpad.com/jp/search/" + encodedKeyword;

		model.addAttribute("search", search);
		model.addAttribute("url", url);
		//今日から3日後を取得
		LocalDate threeDaysLater = LocalDate.now().plusDays(3);

		// 全カテゴリー一覧を取得
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		// 食品一覧情報の取得							
		List<Foods> foodList = null;
		if (categoryId == null) {
			//残り期限が3日以内のものを取得
			foodList = foodsRepository.findByUsersIdAndLimitdateLessThanEqual(musers.getId(), threeDaysLater);
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得							
			foodList = foodsRepository.findByCategoryIdAndUsersIdAndLimitdateLessThanEqual(categoryId, musers.getId(),
					threeDaysLater);
		}

		//期限までの日数を表示
		for (Foods food : foodList) {
			long daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), food.getLimitdate());
			food.setDaysBetween(daysBetween);
			model.addAttribute("daysBetween", "あと" + daysBetween + "日");
		}

		//残り日数でソート
		List<Foods> foodListSrot = foodList.stream()
				.sorted(Comparator.comparing(Foods::getDaysBetween))
				.toList();

		model.addAttribute("foods", foodListSrot);
		model.addAttribute("musers", musers.getName());

		return "nearlist";
	}

}
