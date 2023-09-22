package com.office.library.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")//접근경로 : /user
public class UserHomeController {
	@GetMapping({"", "/"})
	public String home() {
		System.out.println("[UserHomeController] Home()");

		String nextPage = "user/home";

		return nextPage;
	}
}
