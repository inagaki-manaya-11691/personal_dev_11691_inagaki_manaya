package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Users;
import com.example.demo.model.Musers;
import com.example.demo.repository.UsersRepository;

@Controller
public class UserController {
	private final UsersRepository usersRepository;
	private final HttpSession session;
	private final Musers musers;

	public UserController(UsersRepository usersRepository, HttpSession session, Musers musers) {
		this.usersRepository = usersRepository;
		this.session = session;
		this.musers = musers;
	}

	//登録フォームを表示
	@GetMapping("/users/new")
	public String create() {

		return "addusers";
	}

	//送られてきたデータを登録
	@PostMapping("/users/add")
	public String add(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			@RequestParam(defaultValue = "") String password_confirm,
			Model model) {

		if (!password.equals(password_confirm)) {
			model.addAttribute("msg", "パスワードが一致していません");
			return "redirect:/users/new";
		}

		Users user = new Users(name, email, password);
		usersRepository.save(user);
		return "loginForm";
	}

	//ログイン画面を表示
	@GetMapping({ "/", "/logout" })
	public String index() {
		session.invalidate();
		return "loginForm";
	}

	//送られてきたデータでログイン
	@PostMapping("/login")
	public String login(@RequestParam(defaultValue = "") String email,
			@RequestParam(defaultValue = "") String password,
			Model model) {

		List<Users> ulist = usersRepository.findAll();

		for (Users u : ulist) {
			if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
				musers.setName(u.getName());
				return "redirect:/limits";
			}

		}
		model.addAttribute("msg", "メールアドレスまたはパスワードが違います");
		return "redirect:/";
	}

}
