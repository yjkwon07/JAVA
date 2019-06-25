// �ֳ����̼��� ����ǵ��� �Ϸ��� �ش� Ŭ������ �ݵ�� <component-scan>���� ������ ��Ű���� ���� ��Ű���� ���� �ؾ� �Ѵ�.
package com.spring.ex01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/*
 * MainController Ŭ������ �ϴ� ���� ������ ����
 * @Controller("mainController") �ֳ����̼��� �ַ� Controller Ŭ������ ��ġ�� ���� �����.
 * @RequestMapping("/test")�� URL ��û �� ù ��° �ܰ��� ��û�� /test�̸� mainController �󿡰� ��û�� ����.
 * @RequestMapping(value="/main1.do", method=RequestMethod.GET)�� �޼ҵ忡 ��ġ��Ų �� �� ��û�� �����Ͽ� �޼ҵ带 ȣ��
 */

// @Controller �̿��� MainController Ŭ������ ������ �ڵ� ��ȯ, �� id�� mainController
@Controller("mainController")

// @RequsetMapping�� �̿��� ù ��° �ܰ��� URL ��û�� /test�̸� mainController���� ��û
@RequestMapping("/test")
public class MainController {
	// @RequsetMapping�� �̿��� �� ��° �ܰ��� URL��û�� /main1.do�̸� mainController ���� main1() �޼ҵ忡�� ��û
	// method = RequestMethod.GET���� �����ϸ� GET ������� ��û �� �ش� �޼ҵ尡 ȣ��
   @RequestMapping(value="/main1.do" ,method=RequestMethod.GET)
   public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","main1");
      mav.setViewName("test/main");
      return mav;
   }

   // @RequestMapping�� �̿��� �� ��° �ܰ��� URL ��û�� /main2.do�̸� mainController ���� main2() �޼ҵ忡�� ��û
   // method=RequestMethod.GET���� �����ϸ� GET�ݽ����� ��û �� �ش� �޼ҵ尡 ȣ��
   @RequestMapping(value="/main2.do" ,method = RequestMethod.GET)
   public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","main2");
      mav.setViewName("test/main");
      return mav;
   }
   
}
