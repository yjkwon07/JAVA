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
 * �������� ���ͼ��� ����� ���������� �����ϴ� HandlerInterceptorAdapterŬ������ ��� �ްų� �������̽��� 
 * HandlerInterceptor�� �����ؼ� ����Ѵ�. preHandle() �޼ҵ�� ��Ʈ�ѷ��� �����ϱ� ���� ȣ��Ǿ� ����
 */
// ����� ���� ���ͼ��ʹ� �ݵ�� HandlerInterceptorAdapter�� ��ӹ޾ƾ� �Ѵ�.
public class LocaleInterceptor extends  HandlerInterceptorAdapter{
	   @Override
	   // ��Ʈ�ѷ� ���� �� ȣ�� �ȴ�.
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
	      HttpSession session=request.getSession();
	      // ���������� ������ locale ������ �����´�.
	      String locale=request.getParameter("locale");
	      if(locale==null)
	    	  // ���� ��û �� locale�� �ѱ���� ����
	         locale="ko";
	      // LOCALE �Ӽ� ���� ���ǿ� ������ SessionLocaleResolver�� ����� �� �ְ� �Ѵ�.
	      session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE",new Locale(locale));
	      return true;
	   }

	   @Override
	   // ��(JSP)�� ������ �� ȣ�� �ȴ�.
	   public void postHandle(HttpServletRequest request, HttpServletResponse response,
	                           Object handler, ModelAndView modelAndView) throws Exception {
	   }

	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	                                    Object handler, Exception ex)    throws  Exception {
	   }
	}
