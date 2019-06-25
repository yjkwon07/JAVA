// 애너테이션이 적용되도록 하려면 해당 클래스가 반드시 <component-scan>에서 설정한 패키지나 하위 패키지에 존재 해야 한다.
package com.spring.ex01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
 * MainController 클래스가 하는 일은 다음과 같다
 * @Controller("mainController") 애너테이션은 주로 Controller 클래스에 위치해 빈을 만든다.
 * @RequestMapping("/test")은 URL 요청 시 첫 번째 단계의 요청이 /test이면 mainController 빈에게 요청을 전달.
 * @RequestMapping(value="/main1.do", method=RequestMethod.GET)을 메소드에 위치시킨 후 각 요청을 구분하여 메소드를 호출
 */

// @Controller 이용해 MainController 클래스를 빈으로 자동 변환, 빈 id는 mainController
@Controller("mainController")

// @RequsetMapping을 이용해 첫 번째 단계의 URL 요청이 /test이면 mainController빈을 요청
@RequestMapping("/test")
public class MainController {
	// @RequsetMapping을 이용해 두 번째 단계의 URL요청이 /main1.do이면 mainController 빈의 main1() 메소드에게 요청
	// method = RequestMethod.GET으로 지정하면 GET 방식으로 요청 시 해당 메소드가 호출
   @RequestMapping(value="/main1.do" ,method=RequestMethod.GET)
   public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","main1");
      mav.setViewName("test/main");
      return mav;
   }

   // @RequestMapping을 이용해 두 번째 단계의 URL 요청이 /main2.do이면 mainController 빈의 main2() 메소드에게 요청
   // method=RequestMethod.GET으로 지정하면 GET반식으로 요청 시 해당 메소드가 호출
   @RequestMapping(value="/main2.do" ,method = RequestMethod.GET)
   public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","main2");
      mav.setViewName("test/main");
      return mav;
   }
   
}
