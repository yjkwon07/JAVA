package com.myspring.SpringMaven_test.ex05;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
/*
 * 스프링의 인터셉터 기능은 스프링에서 제공하는 HandlerInterceptorAdapter클래스를 상속 받거나 인터페이스인 
 * HandlerInterceptor를 구현해서 사용한다. preHandle() 메소드는 컨트롤러를 실행하기 전에 호출되어 수행
 */
// 사용자 정의 인터셉터는 반드시 HandlerInterceptorAdapter를 상속받아야 한다.
public class LocaleInterceptor extends  HandlerInterceptorAdapter{
	   @Override
	   // 컨트롤러 실행 전 호출 된다.
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
	      HttpSession session=request.getSession();
	      // 브라우저에서 전달한 locale 정보를 가져온다.
	      String locale=request.getParameter("locale");
	      if(locale==null)
	    	  // 최초 요청 시 locale을 한국어로 설정
	         locale="ko";
	      // LOCALE 속성 값을 세션에 저장해 SessionLocaleResolver가 사용할 수 있게 한다.
	      session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE",new Locale(locale));
	      return true;
	   }

	   @Override
	   // 뷰(JSP)를 수행한 후 호출 된다.
	   public void postHandle(HttpServletRequest request, HttpServletResponse response,
	                           Object handler, ModelAndView modelAndView) throws Exception {
	   }

	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	                                    Object handler, Exception ex)    throws  Exception {
	   }
	}
