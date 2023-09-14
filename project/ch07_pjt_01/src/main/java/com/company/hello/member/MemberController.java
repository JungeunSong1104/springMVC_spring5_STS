package com.company.hello.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}
	
	@RequestMapping("/signIn")//현재는 경로만 연결해주는거라 value라는 속성값 안써줘도됨
	public String signIn() {
		return "sign_in";
	}
}
