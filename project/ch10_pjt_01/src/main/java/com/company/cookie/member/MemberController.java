package com.company.cookie.member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping({ "", "/" })
	public String home() {
		System.out.println("[MemberController] home()");
		String nextPage = "member/home";
		return nextPage;
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[Membercontroller] loginForm()");
		String nextPage = "member/login_form";
		return nextPage;
	}

	@PostMapping("/loginConfirm")
	public String loginConfirm(@RequestParam("m_id") String m_id, 
							   @RequestParam("m_pw") String m_pw,
							   HttpServletResponse response) {
		//login_form.jsp의 name값을 받아서 컨트롤러가 @RequestParam을 지정해서 직접 받고있음
		//HttpServletResponse response 쿠키를.. 저장할.. 그릇?
		
		System.out.println("[MemberController] loginConfirm()");

		String nextPage = "member/login_ok";

		if (m_id.equals("user") && m_pw.equals("1234")) {
			Cookie cookie = new Cookie("loginMember", m_id);
			cookie.setMaxAge(60 * 30);//쿠키의 유효기간을 정해줌, setMaxAge : 유효기간이라고 생각해주면됨
			response.addCookie(cookie);
		} else {
			nextPage = "member/login_ng";
		}
		return nextPage;
	}
	
	@GetMapping("/logoutForm")
	public String logoutForm(@CookieValue(value="loginMember", required=false) String loginMember,
															   HttpServletResponse response) {
		//required=false을 안하면 예외가 발생해서 오류뜸? 정상적으로 메서드가 발생할수있도록 false로 해줌
		System.out.println("[MemberController] loginConfirm()");
		
		String nextPage = "redirect:/member/";
		
		Cookie cookie = new Cookie("loginMember", loginMember);//loginMember라는 쿠키를 가지고있음
		cookie.setMaxAge(0);
		
		response.addCookie(cookie);
		
		return nextPage;
	}
}
