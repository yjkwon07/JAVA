package com.myspring.SpringMaven_test;

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
/*
 * HomeController
 * 모든 요청에 대해 home() 메소드를 호출하여 요청 시각을 home.jsp로 포워딩 한다.
 */
// @Controller을 적용한다.
@Controller("homeController")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	// 모든 요청에 대해 home() 메소드를 호출한다.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		// 브라우저에서 요청한 시각을 JSP로 전달한다.
		model.addAttribute("serverTime", formattedDate );
		// 뷰리졸버로 JSP 이름을 반환한다.
		return "home";
	}
	*/
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {		
		// /main.do로 요청 시 컨트롤러에는 <definion> 태그에서 설정한 뷰이름 main을 타일즈 뷰리졸버로 반환
		return "main"; 
	}
}
