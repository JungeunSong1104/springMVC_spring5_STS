package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member") // 접근경로 : /user/member
public class UserMemberController {

	@Autowired
	UserMemberService userMemberService;

	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[UserMembercontroller] createAccountForm");
		// 단순하게 뷰페이지로 연결하는 메서드

		String nextPage = "user/member/create_account_form";

		return nextPage;
	}

	@PostMapping("/createAccountConfirm") // form으로 데이터가 넘어오기 때문에 포스트 매핑
	public String createAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberController] createAccountConfirm()");
		String nextPage = "user/member/create_account_ok";

		int result = userMemberService.createAccountConfirm(userMemberVO);

		if (result <= 0) {
			nextPage = "user/member/create_account_ng";
		}

		return nextPage;
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[UserMemberController] loginForm()");
		String nextPage = "user/member/login_form";
		return nextPage;
	}

	@PostMapping("/loginConfirm")
	public String lginConfirm(UserMemberVO userMemberVO, HttpSession session) {
		// 로그인하면 세션에 정보를 저장하니까 httpsession으로 객체 만들어줌
		System.out.println("[UserMemberController] loginConfirm()");

		String nextPage = "user/member/login_ok";

		UserMemberVO loginedUserMemberVO = userMemberService.loginConfirm(userMemberVO);// 로그인된 사용자의 정보를 가져와야겠지

		if (loginedUserMemberVO == null) {
			nextPage = "user/member/login_ng";
		} else {
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60 * 30);
		}
		return nextPage;
	}

	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountForm()");

		String nextPage = "user/member/modify_account_form";

		UserMemberVO loginedUserMemberVO = (UserMemberVO) session.getAttribute("loginedUserMemberVO");

		if (loginedUserMemberVO == null) {
			nextPage = "redirect:/user/member/loginForm";
		}
		return nextPage;
	}

	@PostMapping("/modifyAccountConfirm")//요청이니까 포스트패밍
	public String modifyAccountConfirm(UserMemberVO userMemberVO, HttpSession session){//세션을 들고다녀야함
		System.out.println("[UserMemberController] modifyAccountConfirm()");

		String nextPage = "user/member/modify_account_ok";

		int result = userMemberService.modifyAccountConfirm(userMemberVO);

		if(result > 0){//데이터가 수정도면 로그인된 사용자의 ㅈ어보가 바뀔테니까 정보를 새로 업데이트해줌
			UserMemberVO loginedUserMemberVO = userMemberService.getLoginedUserMemberVO(userMemberVO.getU_m_no());
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60*30);
		}else {
			return "user/member/modify_account_ng";
		}
		return nextPage;
	}

	@GetMapping("/logoutConfirm")//로그인 된상태에서는 언제나 세션을 들고다녀야함
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserMemberController] logoutConfirm()");

		String nextPage = "redirect:/";
		session.invalidate();
		return nextPage;
	}

	@GetMapping("/findPasswordForm")//요청을 받아주는 겟매핑
	public String findPasswordForm() {
		System.out.println("[UserMemberController] findPasswordForm");

		String nextPage = "user/member/find_password_form";

		return nextPage;
	}

	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberController] findPasswordConfirm()");

		String nextPage = "user/member/find_password_ok";

		int result = userMemberService.findPasswordConfirm(userMemberVO);//데이터베이스에 입력받으 아이디와이름이메일을 대죃서 맞는유저가있으면 그 유저의 이메일로 새로운 비번을 전송해줌

		if(result <= 0) {
			nextPage = "user/member/find_password_ng";
		}
		return nextPage;
	}
}
