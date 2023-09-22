package com.office.library;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		String formattedDate = dateFormat.format(date);
//		model.addAttribute("serverTime", formattedDate);
//
//		return "home";
//	}

	@GetMapping({"", "/"})
	public String home() {
		System.out.println("[HomeController] home()");

		String nextPage = "redirect:/user/";//리디렉트라고 요청이 가기 때문에 다시 user라는 주소로
		return nextPage;
	}

}
