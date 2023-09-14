package com.company.hello;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {//클래스 이름에 컨트롤러가 있든 없든 상관없음
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	//@Test
	@RequestMapping(value = "/", method = RequestMethod.GET)
	//@RequestMapping : 컨트롤러가 호출될 때 메서드가 실행이 되야함 이때 실행되야할 메서드의 경로를 웹상에 지정?
	
	public String home(Locale locale, Model model) {
	//home이라는 메서드가 실행되는것
	//view를 사욯할때 Model model은 필수 
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		//현재 시간을 구해서 dateFormat 이라는 객첼르 만들어줌
		
		String formattedDate = dateFormat.format(date);
		//formattedDate 문자열에 시간을 집어넣어줌
		
		model.addAttribute("serverTime", formattedDate );
		//addAttribute : 값저장
		
		return "home";//view
		//리턴에 home을 넣어주면 home 앞에 스프링이 알아서 webinf 밑에 view라는 디렉토리를 붙여줌 web-inf/views/home.jsp
		//web-inf : prefix
		//home.jsp : suffix
		
		/* <컨트롤러 만드는 방법>
		 * 1. 컨트롤러 아노테이션 붙여줌
		 * 2. 모델추가
		 * 3. 메서드에 리퀘스트매핑아노테이션을 붙여줌
		 * 4. 리턴할 때 뷰의 이름을 지정해줌 */
	}
	
}
