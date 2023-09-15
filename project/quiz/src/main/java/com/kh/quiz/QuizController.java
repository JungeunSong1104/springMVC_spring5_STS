package com.kh.quiz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuizController {//컨트롤러가 퀴즈서비스 호출함

	@Autowired//서비스르 사용하기 위해서 객체하나 할당해줌 직접생성해주진않음 스프링에게 맡길것
	private QuizService quizService;

	@RequestMapping("/")//@RequestMapping 이용하면 해당페이지로 연결이 되도록 만들수 잇음
	public String toMainPage() {
		return "index";
	}//리퀘스트매핑을 통해서 연결해줌

	@RequestMapping("quizSubmitted")//quizSubmitted가 오면 아래 메서드를 실행
	public String answerSubmit(QuizDO answer, Model model, HttpServletRequest request) {
	//페이지가변할때는 주로 모델을 사용 특정 페이지로 값을 전달할때는 httpservletrequest를 쓸수있음
		QuizDO quizDO = quizService.submitAnswer(answer);
		//quizDO에 제출한 문제와 답이 들어가있음 null이거나 퀴즈디오에 데이터가 채워져있거나 둘중 하나
		HttpSession session = request.getSession();
		if(quizDO == null) {
			model.addAttribute("msg","Wrong answer");
			//모델, 세션 둘다 이용해도됨 여기서는 세션을 이용함 근데 스프링은 model사용 지향
			return "error";
		}else {
			session.setAttribute("answer",quizDO);
			//모델, 세션 둘다 이용해도됨 여기서는 세션을 이용함
			return "index";//인덱스로 갈때 먼가를 들고가야 정답인걸 보여주겠지 그렇지 않으면 다시 문제를 낼거임
		}
	}
}
