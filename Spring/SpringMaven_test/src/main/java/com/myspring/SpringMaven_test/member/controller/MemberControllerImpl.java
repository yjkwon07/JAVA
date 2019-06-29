package com.myspring.SpringMaven_test.member.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.SpringMaven_test.member.service.MemberService;
import com.myspring.SpringMaven_test.member.vo.MemberVO;
/*
 * ������ URL ��û���� �丮���� ���� ���� ��ɺ��� �ش� ������ ���� ������ �� �ֵ��� 
 * MemberControllerImpl Ŭ������ ���� getViewName() �޼ҵ带 �����Ѵ�. 
 * ��û URL���� ���� JSP ������ ��� getViewName()�� ȣ���� ��� 
 * fileName.lastIndexOF("/",1)�� ����� JSP�� ������ ���� �̸��� �ش��ϴ� ù ��° ��û���� �����´�.
 * (/member/listMembers.do�� ��û�� ��쿡�� ù ��° ��û���� ���Ե� member/listMembers�� �����´�.
 */
@Controller("memberController")
//@RequestMapping("/member")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	MemberVO memberVO ;
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String viewName = getViewName(request);
		// ���ͼ��Ϳ��� ���ε��� ���̸��� �����´�.
//		String viewName = (String)request.getAttribute("viewName");
		
		// Logger Ŭ������ info() �޼ҵ�� �α� �޽��� ������ info�� �����Ѵ�.
//		logger.info("viewName: "+ viewName);
		// Logger Ŭ������ debug() �޼ҵ�� �α� �޽��� ������ debug�� �����Ѵ�.
		logger.debug("debug���� : viewName: "+ viewName);
		
		List membersList = memberService.listMembers();
		// viewName�� <definition>�±׿� ������ ���̸��� ��ġ�Ѵ�.
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		// ModelAndView ��ü�� ������ ���̸��� Ÿ���� �丮������ ��ȯ�Ѵ�.
		return mav;
	}

	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  			HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           					HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	/*
	 * RedirectAttributes Ŭ������ �̿��� �����̷�Ʈ �� �α���â���� �α��� ���� �޽����� �Ű������� ����.
	 * ID�� ��й�ȣ�� ȸ�� ������ ��ȸ�Ͽ� �ش� ȸ�� ������ �����ϸ� �α��� ���¿� ȸ�� ������ ���ǿ� ����.
	 * �׸��� �α׾ƿ� ��û�� ������ ������ ������ ��� ����
	 */
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	// �α���â���� ���۵� ID�� ��й�ȣ�� MemberBO ��ü�� member�� �����Ѵ�.
	// RedirectAttributes Ŭ������ �̿��� �α��� ���� �� �ٽ� �α���â���� �����̷�Ʈ�Ͽ� ���� �޽����� �����Ѵ�.
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              	RedirectAttributes rAttr,
				              	HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// login() �޼ҵ带 ȣ���ϸ鼭 �α��� ������ �����Ѵ�.
		memberVO = memberService.login(member);
		if(memberVO != null) {
			    HttpSession session = request.getSession();
			    // ���ǿ� ȸ�� ������ �����Ѵ�.
			    session.setAttribute("member", memberVO);
			    // ���ǿ� �α��� ���¸� true�� �����Ѵ�.
			    session.setAttribute("isLogOn", true);
			    // memberVO�� ��ȯ�� ���� ������ ������ �̿��� �α��� ���¸� true�� �Ѵ�.
			    mav.setViewName("redirect:/member/listMembers.do");
		}
		else {
				// �α��� ���� �� ���� �޽����� �α���â���� �����Ѵ�.
			    rAttr.addAttribute("result","loginFailed");
			    // �α��� ���� �� �ٽ� �α���â���� �����̷�Ʈ�Ѵ�.
			    mav.setViewName("redirect:/member/loginForm.do");
		}
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// �α׾ƿ� ��û �� ���ǿ� ����� �α��� ������ ȸ�� ������ ���� �Ѵ�.
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	// �α���â ��û �� �Ű����� reult�� ���۵Ǹ� ���� result�� ���� �����Ѵ�. 
	// ���ʷ� �α���â�� ��û�� ���� �Ű����� result�� ���۵��� �����Ƿ� �����Ѵ�.
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		//String viewName = getViewName(request);
		// ���ͼ��Ϳ��� ���ε��� ���̸��� �����´�.
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		System.out.println("contextPath :" +contextPath);
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println("uri : "+ uri);
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		System.out.println("uri : "+ uri);
		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} 
		else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} 
		else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		
		// /member/listMembers.do�� ��û�� ��� memeber/listMember�� ���� �̸����� �����´�.
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		
		return viewName;
	}


}
