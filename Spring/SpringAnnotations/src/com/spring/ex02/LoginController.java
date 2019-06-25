//  com.spring ���� ��Ű���� Ŭ������ ��ġ�ؾ� �ֳ����̼��� ����
package com.spring.ex02;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/*
 *  ������ �ֳ����̼� ����� �̿��� �α��� �� ���۵� ID�� �̸��� JSP�� ����ϵ��� LoginController Ŭ������ �ۼ�.
 *  method = {RequsetMethod.GET, RequsetMethod.POST}) ������ GET ��İ� POST����� ��� ó�� ����.
 *  ���� @RequsetMapping(...)�� ����ϸ� �� �޼ҵ忡 ���� ����  ��û URL�� �����Ͽ� ���ÿ� ȣ�� ����.
 */

// ��Ʈ�� ���� �ڵ����� ����
@Controller("loginController")
public class LoginController {
	
	
	// /test/loginForm.do�� /test/loginForm2.do�� ��û�� loginForm()�� ȣ��
	@RequestMapping(value = { "/test/loginForm.do", "/test/loginForm2.do" }, method = { RequestMethod.GET })
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/loginForm");
		return mav;
	}
	
	//  getParameter() �޼ҵ带 �̿��� �ʿ䰡 ����.
	// GET��İ� POST��� ��û �� ��� ó��
    @RequestMapping(value = "/test/login1.do", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/result");
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);
		return mav;
	}
    
	
    /*
     * �޼ҵ忡 @RequestParam �����ϱ�
     * 
     * ���ݱ����� ���������� �Ű������� �����ϸ� getParameter() �޼ҵ带 �̿��� ���� �����.
     * �׷��� ���۵Ǿ� �� �Ű������� ���� �������� ������ getParmeter() �޼ҵ带 �̿��ϴ� ����� �����ϴ�.
     * �̹����� @RequestParm�� �޼ҵ忡 ������ ���� ���� ��� ����� �˾ƺ���.
     * @RequestParam�� �̿��� �α���â���� ���۹��� �Ű������� ������ �� ���������� �Ű������� �����ϸ� ������
     * ������ �ڵ����� ���� ����. �׷��� getParameter() �޼ҵ带 �̿����� �ʾƵ� �ȴ�. 
     */

	@RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @RequestParam�� �̿��� �Ű������� userID�̸� �� ���� ���� userID�� �ڵ����� ����
	// @RequestParam�� �̿��� �Ű������� userName�̸� �� ���� ���� userName�� �ڵ����� ����
	public ModelAndView login2(@RequestParam("userID") String userID, 
			                  @RequestParam("userName") String userName,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/result");
		
		// getParameter() �޼ҵ带 �̿��� �ʿ䰡 ����.
		// String userID = request.getParameter("userID");
		// String userName = request.getParameter("userName");
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);

		return mav;
	}
	
	/*
	 * @RequestParam�� required �Ӽ� ����ϱ�
	 * 
	 * �α����ϴ� ��� ID�� ��й�ȣ ���� ������ �ݵ�� ��Ʈ�ѷ��� ���޵Ǿ�� �Ѵ�.
	 * @RequestParam�� required �Ӽ��� �̿��ϸ� �ݵ�� �����ؾ� �ϴ� �ʼ� �Ű������� ���� �׷��� ���� ��츦 ������ �� �ִ�.
	 * 
	 * @RequestParam ���� �� required�Ӽ��� �����ϸ� �⺻���� true
	 * required �Ӽ��� true�� �����ϸ� �޼ҵ� ȣ�� �� �ݵ�� ������ �̸��� �Ű������� �����ؾ� �Ѵ�.(�Ű������� ������ ���ܰ� �߻�)
	 * required �Ӽ��� false�� �����ϸ� �޼ҵ� ȣ�� �� ������ �̸��� �Ű������� ���޵Ǹ� ���� �����ϰ� ������ null�� �Ҵ�
	 */
	
	@RequestMapping(value = "/test/login3.do", method = { RequestMethod.GET, RequestMethod.POST })
	// required �Ӽ��� �����ϸ� required�� �⺻���� true (userID)
	// required �Ӽ��� ��������� true�� ���� (userName)
	// required �Ӽ��� ��������� false�� ���� (email)
	public ModelAndView login2(@RequestParam("userID") String userID, 
                               @RequestParam(value="userName", required=true) String userName,
			                   @RequestParam(value="email", required=false) String email,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/result");
		
		// String userID = request.getParameter("userID");
		// String userName = request.getParameter("userName");
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		System.out.println("email: "+ email);
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);
		return mav;
	}
		
	/*
	 * @RequestParam �̿��� Map�� �Ű����� �� �����ϱ�
	 * 
	 * ���۵Ǵ� �Ű������� ���� ���� ��� ������ ������ �����ؼ� ����Ϸ��� �����ϴ�.
	 * �̹����� ���޷ε�� �Ű����� ������ Map�� �����Ѵ�.
	 * @RequsetParam Map<String, String> info�� �̸��� info�� Map�� �Ű����� �̸��� key��, �Ű����� ���� value�� �����Ѵ�. 
	 */
	@RequestMapping(value = "/test/login4.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @RequsetParam�� �̿��� Map�� ���۵� �Ű����� �̸��� key, ���� value�� ����
	public ModelAndView login3(@RequestParam Map<String, String> info,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		
		// Map�� ����� �Ű������� �̸����� ���޵� ���� �����´�.
		String userID = info.get("userID");
		String userName = info.get("userName");
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		
		// @RequestParam���� ������ Map �̸����� ���ε�
		mav.addObject("info", info);
		mav.setViewName("test/result");
		return mav;
	}
	
	/*
	 * @ModelAttribute �̿��� VO�� �Ű����� �� �����ϱ�
	 * 
	 * ���� ���� �Ű������� �����Ѵ�.
	 * �켱 @ModelAttribute�� �̿��� VOŬ������ �Ӽ��� �Ű����� ���� �ڵ����� �����ǵ��� �Ѵ�.
	 * @ModelAttribut("info") LoginVO loginVO�� ���޵� �Ű������� ���� LoginVO Ŭ���� ��ü�� ����.
	 * �̾ �Ű����� �̸��� ���� �Ӽ��� �Ű����� ���� ������ �� info �̸����� ���ε�.
	 * ���� ��� �α���â���� ���޵� �Ű����� �̸��� userID�̰�, ���� hong�� ���, @ModelAttribute�� LoginVO�� �����ϸ� 
	 * ���� ��LoginVO�� �Ӽ� userID�� ���޵� �� hong�� �ڵ����� ���� 
	 */
	@RequestMapping(value = "/test/login5.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @ModelAttribute�� �̿��� ���޵Ǵ� �Ű����� ���� LoginVO Ŭ������ �̸��� ���� �Ӽ��� �ڵ����� ����.
	// addObject()�� �̿��� �ʿ� ���� info�� �̿��� �ٷ� JSP���� LoginVO �Ӽ��� ������ �� �ִ�.
	public ModelAndView login4(@ModelAttribute("info") LoginVO loginVO,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		System.out.println("userID: "+loginVO.getUserID());
		System.out.println("userName: "+loginVO.getUserName());
		mav.setViewName("test/result");
		return mav;
	}
	
	/*
	 * Model Ŭ���� �̿��� �� �����ϱ�
	 * Model Ŭ������ �̿��ϸ� �޼ҵ� ȣ�� �� JSP�� ���� �ٷ� ���ε��Ͽ� ������ �� �ִ�.
	 * Model Ŭ������ addAttribute() �޼ҵ�� ModelAndView�� addObject() �޼ҵ�� ���� ����� �Ѵ�.
	 * Model Ŭ������ ���� �� ������ ������ �ʿ䰡 ���� �� ����ϸ� ���ϴ�.
	 */
	@RequestMapping(value = "/test/login6.do", method = { RequestMethod.GET, RequestMethod.POST })
	// Model :  �޼ҵ� ȣ�� �� Model Ŭ���� ��ü�� ����
	public String login5(Model model,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		// JSP���� ������ �����͸� model�� addAttribute() �޼ҵ带 �̿��ؼ� ���ε�
		model.addAttribute("userID", "hong");
		model.addAttribute("userName", "ȫ�浿");
		return "test/result";
	}	
}
