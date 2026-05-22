package com.example.demo.controller;

import java.util.ArrayList;
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
		List<String> errorlist = new ArrayList<>();
		boolean check = false;
		if (name.equals("")) {
			errorlist.add("名前は必須です");
			check = true;
		}
		if (email.equals("")) {
			errorlist.add("メールアドレスは必須です");
			check = true;
		}
		if (password.equals("")) {
			errorlist.add("パスワードは必須です");
			check = true;
		} else if (password_confirm.equals("")) {
			errorlist.add("確認用パスワードは必須です");
			check = true;
		} else if (!password.equals(password_confirm)) {
			errorlist.add("パスワードが一致していません");
			check = true;
		}

		if (check) {
			model.addAttribute("error", errorlist);
			model.addAttribute("name", name);
			model.addAttribute("email", email);
			model.addAttribute("password", password);
			model.addAttribute("password_confirm", password_confirm);
			return "addusers";
		}

		Users user = new Users(name, email, password);
		usersRepository.save(user);
		return "loginForm";
	}

	//ログイン画面表示
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
				musers.setId(u.getId());
				musers.setName(u.getName());
				return "redirect:/limits";
			}
		}
		model.addAttribute("msg", "メールアドレスまたはパスワードが違います");
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		return "loginForm";
	}

}
