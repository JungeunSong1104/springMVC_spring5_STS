package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

	@Autowired
	AdminMemberService adminMemberService;// 외부에서 객체 주입해서 생성할거기 때문에 객체를 선언만 해줌

	@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	/// admin/member가 입력되면 최종적으로 뷰로 연결되어야 하니까 메서드 이름과 같이 만들어줌
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		String nextPage = "admin/member/create_account_form";

		return nextPage;
	}

	// @RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	// // 생략하면안됨 post메서드와 get메서드를 구분해야함
	// post 요청 받아서 사용할 때는 @PostMapping("/createAccountConfirm")이렇게 해줘도됨
	// get은GetMapping 써주면됨
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVO adminMemberVO) {
		// 메서드가 실행될때 입력값을 담아서 전달이 되는데 그 입력값이 담기는 vessel이 adminMemberVO인거임
		// 그르면 스프링이 알아서 데이터를 담아줌 대신 조건이 있음
		// createAccountConfirm 주소 매칭이 됐을 때 데이터를 받아올수 있도록 매개변수를 받아줘야함
		// AdminMemberVO adminMemberVO 이렇게 매개변수를 받아주기만 해도 알아서 가져오게됨
		System.out.println("[AdminMemberController] createAccountConfirm()");

		String nextPage = "admin/member/create_account_ok";
		// 정상적으로 처리됐다면 초기값인 성공페이지가 뜨게됨

		int result = adminMemberService.createAccountConfirm(adminMemberVO);
		// createAccountConfirm의 자료형이 int니까 int로 넣어줌
		// 이 메서드가 반환하는 값은 정수(int)로서, 일반적으로 성공 시 양수를 반환하고 실패 시 0 또는 음수를 반환합니다.

		if (result <= 0) {// 결과를 받아올거기 때문에 그 결과를 정상적으로 받아왔는지 못햇는지 판별을해줌
			nextPage = "admin/member/create_account_ng";
		}
		return nextPage;
	}

	@GetMapping("/loginForm")
	public String loginFrom() {
		System.out.println("[AdminMemberController] loginForm()");

		String nextPage = "admin/member/login_form";

		return nextPage;
	}

	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVO adminMemberVO, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		// 넘어오는 아디와 비번을 담을 객체가 필요함 adminmembervo객체에 담아옴 물론 당아오는건 스프링이 알아서해줌
		// 전달하는 값들은 id 같은 경우에는 a_m_id와 VO에 있는 변수이름와 같아야함

		String nextPage = "admin/member/login_ok";

		AdminMemberVO loginedAdminMemberVO = adminMemberService.loginConfirm(adminMemberVO);
		// adminMemberService가 가지고 있는 로그인컨펌메서드실행
		if (loginedAdminMemberVO == null) {
			nextPage = "admin/member/login_ng";
		} else {
			session.setAttribute("loginedAdminMemberVO", loginedAdminMemberVO);
			// loginedAdminMemberVO에 int가 들어가든 string이 들어가든 상관없은 개발자맘임
			session.setMaxInactiveInterval(60 * 30);// 초단위로 계산함
		}
		return nextPage;
	}

	@GetMapping("/logoutConfirm") // 어차피 겟요청으로 올테니까? 뭐가..? 겟매핑해줌
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		String nextPage = "redirect:/admin";// 로그아웃이되면 이동할 페이지 : 관리자페이지

		session.invalidate();// 세션에 들어가있는 정보가 초기화됨;

		return nextPage;
	}

	// 관리자 목록
	@GetMapping("/listupAdmin")
	public ModelAndView listupAdmin() {
		System.out.println("[AdminMemberController] listupAdmin()");

		String nextPage = "admin/member/listup_admins";

		List<AdminMemberVO> adminMemberVOs = adminMemberService.listupAdmin();

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminMemberVOs", adminMemberVOs);
		modelAndView.setViewName(nextPage);
		return modelAndView;
	}

	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");

		String nextPage = "redirect:/admin/member/listupAdmin";
		// 리턴할페이지문자열 jsp view페이지로 가는게아니라 리디렉트로 다시실행? 하는거라서 메소 호출을 하는것
		// 승인해준다음에 다시 원래페이지(관리자목록)으로 돌아가게함 : 승인이 잘 됐는지 확인을 해야하니까

		adminMemberService.setAdminApproval(a_m_no);
		return nextPage;
	}

	// 로그인 확인 => 계정 수정창
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountForm()");
		String nextPage = "admin/member/modify_account_form";

		AdminMemberVO loginedAdminMemberVO = (AdminMemberVO) session.getAttribute("loginedAdminMemberVO");

		if (loginedAdminMemberVO == null) {
			nextPage = "redirect:/admin/member/loginForm";
		}
		return nextPage;
	}

	// 계정 수정
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfrim(AdminMemberVO adminMemberVO, HttpSession session) {
		System.out.println("[AdminMemberController] modifyAccountConfrim()");

		String nextPage = "admin/member/modify_account_ok";

		int result = adminMemberService.modifyAccountConfirm(adminMemberVO);
		// 데이터 수정(업데이트)을 위해 서비스를 이용해서 DAO접근해야함.

		if (result > 0) {
			AdminMemberVO loginedAdminMemberVO = adminMemberService.getLoginedAdminMemberVO(adminMemberVO.getA_m_no());

			// 세션의 정보도 수정을 해야합니다.
			session.setAttribute("loginedAdminMemberVO", loginedAdminMemberVO);
			session.setMaxInactiveInterval(60 * 30);
		} else {
			nextPage = "admin/member/modify_account_ng";
		}
		return nextPage;
	}

	@RequestMapping("/findPasswordForm") // 겟이든 포스트든 상관없으니 리퀘스트로 해줌
	public String findPasswordForm() {// 단슌하게 뷰페이지를 연결해주는 메서드
		System.out.println("[0AdminMemberController] findPasswordForm()");

		String nextPage = "admin/member/find_password_form";

		return nextPage;
	}

	@PostMapping("/findPasswordConfirm") // 요청 폼으로 오기 때문에 포스트요청
	public String findPasswordConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberController] findPasswordConfirm()");

		String nextPage = "admin/member/find_password_ok";

		int result = adminMemberService.findPasswordConfirm(adminMemberVO);
		// adminMemberService객체에 findPasswordConfirm 메서드 호출함

		if (result <= 0) {
			nextPage = "admin/member/find_password_ng";
		}
		return nextPage;
	}
}