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
 * 브라우저 URL 요청명에서 뷰리졸버 설정 없이 기능별로 해당 폴더에 쉽게 접근할 수 있도록 
 * MemberControllerImpl 클래스를 열어 getViewName() 메소드를 수정한다. 
 * 요청 URL에서 응답 JSP 파일을 얻는 getViewName()을 호출할 경우 
 * fileName.lastIndexOF("/",1)을 사용해 JSP가 지정죈 폴더 이름에 해당하는 첫 번째 요청부터 가져온다.
 * (/member/listMembers.do로 요청할 경우에는 첫 번째 요청명이 포함된 member/listMembers를 가져온다.
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
		// 인터셉터에서 바인딩된 뷰이름을 가져온다.
//		String viewName = (String)request.getAttribute("viewName");
		
		// Logger 클래스의 info() 메소드로 로그 메시지 레벨을 info로 설정한다.
//		logger.info("viewName: "+ viewName);
		// Logger 클래스의 debug() 메소드로 로그 메시지 레벨을 debug로 설정한다.
		logger.debug("debug레벨 : viewName: "+ viewName);
		
		List membersList = memberService.listMembers();
		// viewName이 <definition>태그에 설정한 뷰이름과 일치한다.
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		// ModelAndView 객체에 설정한 뷰이름을 타일즈 뷰리졸버로 반환한다.
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
	 * RedirectAttributes 클래스를 이용해 리다이렉트 시 로그인창으로 로그인 실패 메시지를 매개변수로 전달.
	 * ID와 비밀번호로 회원 정보를 조회하여 해당 회원 정보가 존재하면 로그인 상태와 회원 정보를 세션에 저장.
	 * 그리고 로그아웃 요청을 받으면 세션의 정보를 모두 삭제
	 */
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	// 로그인창에서 전송된 ID와 비밀번호를 MemberBO 객체인 member에 저장한다.
	// RedirectAttributes 클래스를 이용해 로그인 실패 시 다시 로그인창으로 리다이렉트하여 실패 메시지를 전달한다.
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
				              	RedirectAttributes rAttr,
				              	HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// login() 메소드를 호출하면서 로그인 정보를 전달한다.
		memberVO = memberService.login(member);
		if(memberVO != null) {
			    HttpSession session = request.getSession();
			    // 세션에 회원 정보를 저장한다.
			    session.setAttribute("member", memberVO);
			    // 세션에 로그인 상태를 true로 설정한다.
			    session.setAttribute("isLogOn", true);
			    // memberVO로 반환된 값이 있으면 세션을 이용해 로그인 상태를 true로 한다.
			    mav.setViewName("redirect:/member/listMembers.do");
		}
		else {
				// 로그인 실패 시 실패 메시지를 로그인창으로 전달한다.
			    rAttr.addAttribute("result","loginFailed");
			    // 로그인 실패 시 다시 로그인창으로 리다이렉트한다.
			    mav.setViewName("redirect:/member/loginForm.do");
		}
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		// 로그아웃 요청 시 세션에 저장된 로그인 정보와 회원 정보를 삭제 한다.
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}	

	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	// 로그인창 요청 시 매개변수 reult가 전송되면 변수 result에 값을 저장한다. 
	// 최초로 로그인창을 요청할 때는 매개변수 result가 전송되지 않으므로 무시한다.
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		//String viewName = getViewName(request);
		// 인터셉터에서 바인딩된 뷰이름을 가져온다.
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
		
		// /member/listMembers.do로 요청할 경우 memeber/listMember를 파일 이름으로 가져온다.
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		
		return viewName;
	}


}
