package com.company.hello.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {// 컨트롤러 : 뷰 페이지를 리턴해줌

	// MemberService memberService = new MemberService();//강하게 의존하는 MemberService
	// 객체를 만듦

	// 생성자 지우고 autowired 임포트
	@Autowired // 이제 스프링이 알아서 주입을 해줌
	MemberService memberService;

	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}

	@RequestMapping("/signIn") // 현재는 경로만 연결해주는거라 value라는 속성값 안써줘도됨
	public String signIn() {
		return "sign_in";
	}

	@RequestMapping("/signUpConfirm") // 이 주소가 연결된 메소드를 아래에 작성해줌
	public String signUpConfirm(MemberVO memberVO) {// 단순히 메서드를 호출만 하고있음
		// 메서드가 실행될떄매개변수로 받아줘야함 매개변수들은 뷰에서 넘겨주는 아이디와 동일하게 해주는게 좋음
		// 단순히 m_pw 이런식으로 매개변수를 넣기만해서는 실행이 안됨
		// sign_up.jsp 에서 넘어온 내용을 이용할수있게 끔 스프링이 데이터를 넣어줄수 있게끔 리퀘스트파람을 사용해야줘야함
		// @RequestParam String m_pw 이런식으로
		// 게터세터가 없으면 객체에 접근할수없음 꼭 게터세터가 필요함

		System.out.println("[MemberController] signUpConfirm()");

		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());

		memberService.signUpConfirm(memberVO);
		// return null;// 리턴 널 해줘서 그 페이지로 연결해주지는 않을거임
		return "sign_up_ok";
		// 리턴자료형이 문자형이기 때문에 스프링이 자동으로 web-inf/views/sign_up_ok.jsp를 만들어주는것
	}

	@RequestMapping("/signInConfirm") // signInConfirm메서드가 실행되면서
	public String signInConfirm(MemberVO memberVO) {
		System.out.println("[MemberController] signInConfirm()");

		MemberVO signedInMember = memberService.signInConfirm(memberVO);// 서비스객체에는 디오객체 전달해야함
		if (signedInMember != null) {// 만약 도려받은 데이터가 널이 아니면 로그인 성공
			return "sign_in_ok";
		} else {
			return "sign_in_ng";
		}//메서드가 끝나면 signedInMember 이 값은 사라짐
	}
	
	  @RequestMapping("/signout")
	    public ModelAndView signout(HttpSession session) {
	        session.invalidate();
	        ModelAndView mv = new ModelAndView("/sign_in");
	        return mv;
	    }
}
