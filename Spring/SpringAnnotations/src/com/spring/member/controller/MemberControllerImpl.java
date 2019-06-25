package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;
/*
 * @Controller �ֳ����̼����� ��Ʈ�ѷ� ���� �ڵ� �����ϰ� @AutoWired �ֳ����̼��� �̿��� setter�� ������� �ʰ� 
 * ������ ���� �Ӽ��� ����. ���� @ModelAttribute �ֳ����̼��� �̿��� ȸ������â���� ���޵� ȸ�� ������ �ٷ� 
 * MemberVO ��ü �Ӽ��� ����. @RequsetMapping(value = "/*Form.do", method =RequestMethod.GET) ������ ���� ��û��
 * ���� �� ���� �ż��带 ȣ���� ��� ���Խ��� �̿��� �����ϴ� ������ �Ѵ�. Form.do�� ������ ��� ��û�� ���� ������ �޼ҵ带 ȣ��
 */


// @Controller�� �̿��� MemberControllerImpl Ŭ������ ���� id�� memberController�� ���� �ڵ� ����
@Controller("memberController")
public class MemberControllerImpl   implements MemberController {
	// @Autowired�� �̿��ؼ� id�� memberService�� ���� �ڵ� ����
	@Autowired
	private MemberService memberService;
	// @Autowired�� �̿��ؼ� id�� memberVO�� ���� �ڵ� ����
	@Autowired
	MemberVO memberVO ;
	// �� �ܰ�� ��û �� �ٷ� �ش� �޼ҵ带 ȣ���ϵ��� ����
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}
	// ȸ�� ����â���� ���۵� ȸ�� ������ �ٷ� MemberVO ��ü�� ����
	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int result = 0;
		//  ������ memberVO��ü�� SQL������ �ܵ��� ȸ�� ����� �Ѵ�.
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	// ���۵� ID�� ���� id�� ����
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	// ���Խ��� �̿��� ��û���� Form.do�� ������ form() �޼ҵ带 ȣ���Ѵ�.
	/*@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)*/
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		/*
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/"), viewName.length());
		}
		*/
		return viewName;
	}


}
