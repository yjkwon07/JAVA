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
 * ��� ��û�� ���� home() �޼ҵ带 ȣ���Ͽ� ��û �ð��� home.jsp�� ������ �Ѵ�.
 */
// @Controller�� �����Ѵ�.
@Controller("homeController")
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	/*
	// ��� ��û�� ���� home() �޼ҵ带 ȣ���Ѵ�.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		// ���������� ��û�� �ð��� JSP�� �����Ѵ�.
		model.addAttribute("serverTime", formattedDate );
		// �丮������ JSP �̸��� ��ȯ�Ѵ�.
		return "home";
	}
	*/
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {		
		// /main.do�� ��û �� ��Ʈ�ѷ����� <definion> �±׿��� ������ ���̸� main�� Ÿ���� �丮������ ��ȯ
		return "main"; 
	}
}
