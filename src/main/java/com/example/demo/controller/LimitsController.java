package com.example.demo.controller;

import java.security.Timestamp;
import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Foods;
import com.example.demo.repository.FoodsRepository;

package com.example.demo.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Categories;
import com.example.demo.entity.Category;
import com.example.demo.entity.Food;
import com.example.demo.repository.CategoriesRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FoodRepository;

@Controller
public class LimitsController {
	private final CategoriesRepository categoriesRepository;
	private final FoodsRepository foodsRepository;

	public LimitsController(CategoriesRepository categoriesRepository, FoodsRepository foodsRepository) {
		this.categoriesRepository = categoriesRepository;
		this.foodsRepository = foodsRepository;
	}

	//食品一覧表示
	@GetMapping("/limits")
	public String index(@RequestParam(defaultValue = "") Integer categoryId,
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
		model.addAttribute("foods", foodList);

		return "limits";
	}

	//新規登録画面の表示
	@GetMapping("/limits/new")
	public String create(Model model) {
		List<Categories> categoryList = categoriesRepository.findAll();
		model.addAttribute("categories", categoryList);

		return "addFood";
	}

	//新規登録処理
	@PostMapping("/limits/add")
	public String add(@RequestParam(defaultValue = "") Integer categoryId,
			@RequestParam(defaultValue = "") String foodname,
			@RequestParam(defaultValue = "") Boolean limits,
			@RequestParam(defaultValue = "") Date limitdate,
			@RequestParam(defaultValue = "") String quantity) {

		Categories cate = categoriesRepository.findById(categoryId).get();
		Foods food = new Foods(cate, foodname, limits, LocalDateTime.now(), limitdate, quantity);
		foodsRepository.save(food);
		return "redirect:/limits";
	}
}

}
