package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Foods;
import com.example.demo.entity.Users;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.FoodsRepository;
import com.example.demo.repository.UsersRepository;

@Controller
public class LimitsController {
	private final CategoriesRepository categoriesRepository;
	private final FoodsRepository foodsRepository;
	private final UsersRepository usersRepository;

	public LimitsController(CategoriesRepository categoriesRepository, FoodsRepository foodsRepository,
			UsersRepository usersRepository) {
		this.categoriesRepository = categoriesRepository;
		this.foodsRepository = foodsRepository;
		this.usersRepository = usersRepository;
	}

	//食品一覧表示
	@GetMapping("/limits")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
			Model model) {

		// 全カテゴリー一覧を取得
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		List<Users> userList = usersRepository.findAll();
		model.addAttribute("users", userList);

		// 食品一覧情報の取得
		List<Foods> foodList = null;
		if (categoryId == null) {
			foodList = foodsRepository.findAll();
		} else {
			// itemsテーブルをカテゴリーIDを指定して一覧を取得
			foodList = foodsRepository.findByCategoryId(categoryId);
		}
		model.addAttribute("foods", foodList);

		return "limits";
	}

	//新規登録画面の表示
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
			@RequestParam(defaultValue = "") Date limitdate,
			@RequestParam(defaultValue = "") String quantity) {

		Integer quantity2 = Integer.parseInt(quantity);
		Categories cate = categoriesRepository.findById(categoryId).get();
		Foods food = new Foods(usersId, cate, foodname, limits, LocalDateTime.now(), limitdate, quantity2);
		foodsRepository.save(food);
		return "redirect:/limits";
	}

	@GetMapping("/limits/{foodId}/edit")
	public String edit(@PathVariable Integer foodId, Model model) {
		Foods foods = foodsRepository.findById(foodId).get();
		model.addAttribute("foods", foods);
		return "editlimits";
	}

	@PostMapping("/limits/{foodId}/edit")
	public String update(@PathVariable Integer foodId,
			@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String foodname,
			@RequestParam(defaultValue = "") Boolean limits,
			@RequestParam(defaultValue = "") Date limitdate,
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

	@PostMapping("/limits/{foodId}/delete")
	public String delete(@PathVariable Integer foodId) {
		foodsRepository.deleteById(foodId);

		return "redirect:/limits";
	}
}
