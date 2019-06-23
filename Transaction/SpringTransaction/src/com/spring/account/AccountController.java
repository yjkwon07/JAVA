package com.spring.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
/*
 * ��Ʈ�ѷ������� �Ӽ� accService�� ���� �����ϱ� ���� setter�� ����
 *	/account/sendMoney.do�� ��û �� sendMoney() �޼ҵ带 ȣ���� ���� ��ü �۾� ����
 */
public class AccountController  extends MultiActionController  {
	   private AccountService accService ;
	   // �Ӽ� accServcie�� ���� �����ϱ� ���� .setter�� ����
	   public void setAccService(AccountService accService){
	     this.accService = accService;
	   }	

	   public ModelAndView sendMoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      ModelAndView mav=new ModelAndView();
	      // �ݾ��� ��ü
	      accService.sendMoney();
	      mav.setViewName("result");
	      return mav;
	   }
}
