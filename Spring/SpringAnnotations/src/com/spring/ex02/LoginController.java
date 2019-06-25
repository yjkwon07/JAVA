//  com.spring 하위 패키지에 클래스가 위치해야 애너테이션이 저굥
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
 *  스프링 애너테이션 기능을 이용해 로그인 시 전송된 ID와 이름을 JSP에 출력하도록 LoginController 클래스를 작성.
 *  method = {RequsetMethod.GET, RequsetMethod.POST}) 설정은 GET 방식과 POST방식을 모두 처리 가능.
 *  또한 @RequsetMapping(...)을 사용하면 한 메소드에 여러 개의  요청 URL을 설정하여 동시에 호출 가능.
 */

// 컨트롤 빈을 자동으로 생성
@Controller("loginController")
public class LoginController {
	
	
	// /test/loginForm.do와 /test/loginForm2.do로 요청시 loginForm()을 호출
	@RequestMapping(value = { "/test/loginForm.do", "/test/loginForm2.do" }, method = { RequestMethod.GET })
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/loginForm");
		return mav;
	}
	
	//  getParameter() 메소드를 이용할 필요가 없다.
	// GET방식과 POST방식 요청 시 모두 처리
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
     * 메소드에 @RequestParam 적용하기
     * 
     * 지금까지는 브라우저에서 매개변수를 전송하면 getParameter() 메소드를 이용해 값을 얻었다.
     * 그런데 전송되어 온 매개변수의 수가 많아지면 일일이 getParmeter() 메소드를 이용하는 방법은 불편하다.
     * 이번에는 @RequestParm을 메소드에 적용해 쉽게 값을 얻는 방법을 알아본다.
     * @RequestParam을 이용해 로그인창에서 전송받은 매개변수를 설정한 후 브라우저에서 매개변수를 전달하면 지정한
     * 변수에 자동으로 값이 설정. 그러면 getParameter() 메소드를 이용하지 않아도 된다. 
     */

	@RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @RequestParam을 이용해 매개변수가 userID이면 그 값을 변수 userID에 자동으로 설정
	// @RequestParam을 이용해 매개변수가 userName이면 그 값을 변수 userName에 자동으로 설정
	public ModelAndView login2(@RequestParam("userID") String userID, 
			                  @RequestParam("userName") String userName,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test/result");
		
		// getParameter() 메소드를 이용할 필요가 없다.
		// String userID = request.getParameter("userID");
		// String userName = request.getParameter("userName");
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);

		return mav;
	}
	
	/*
	 * @RequestParam의 required 속성 사용하기
	 * 
	 * 로그인하는 경우 ID와 비밀번호 같은 정보는 반드시 컨트롤러에 전달되어야 한다.
	 * @RequestParam의 required 속성을 이용하면 반드시 전달해야 하는 필수 매개변수인 경우와 그렇지 않은 경우를 설정할 수 있다.
	 * 
	 * @RequestParam 적용 시 required속성을 생략하면 기본값은 true
	 * required 속성을 true로 설정하면 메소드 호출 시 반드시 지정한 이름의 매개변수를 전달해야 한다.(매개변수가 없으면 예외가 발생)
	 * required 속성을 false로 설정하면 메소드 호출 시 지정한 이름의 매개변수가 전달되면 값을 저장하고 없으며 null을 할당
	 */
	
	@RequestMapping(value = "/test/login3.do", method = { RequestMethod.GET, RequestMethod.POST })
	// required 속성을 생략하면 required의 기본값은 true (userID)
	// required 속성을 명시적으로 true로 설정 (userName)
	// required 속성을 명시적으로 false로 설정 (email)
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
	 * @RequestParam 이용해 Map에 매개변수 값 설정하기
	 * 
	 * 전송되는 매개변수의 수가 많을 경우 일일이 변수를 지정해서 사용하려면 불편하다.
	 * 이번에는 전잗로디는 매개변수 값들을 Map에 저장한다.
	 * @RequsetParam Map<String, String> info는 이름이 info인 Map에 매개변수 이름을 key로, 매개변수 값을 value로 저장한다. 
	 */
	@RequestMapping(value = "/test/login4.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @RequsetParam을 이용해 Map에 전송된 매개변수 이름을 key, 값을 value로 저장
	public ModelAndView login3(@RequestParam Map<String, String> info,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		
		// Map에 저장된 매개변수의 이름으로 전달된 값을 가져온다.
		String userID = info.get("userID");
		String userName = info.get("userName");
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		
		// @RequestParam에서 설정한 Map 이름으로 바인딩
		mav.addObject("info", info);
		mav.setViewName("test/result");
		return mav;
	}
	
	/*
	 * @ModelAttribute 이용해 VO에 매개변수 값 설정하기
	 * 
	 * 여러 개의 매개변수를 전달한다.
	 * 우선 @ModelAttribute를 이용해 VO클래스의 속성에 매개변수 값이 자동으로 설정되도록 한다.
	 * @ModelAttribut("info") LoginVO loginVO는 전달된 매개변수에 대해 LoginVO 클래스 객체를 생성.
	 * 이어서 매개변수 이름과 같은 속성에 매개변수 값을 설정한 후 info 이름으로 바인딩.
	 * 예를 들어 로그인창에서 전달된 매개변수 이름이 userID이고, 값이 hong일 경우, @ModelAttribute로 LoginVO를 지정하면 
	 * 전달 시LoginVO의 속성 userID에 전달된 값 hong을 자동으로 설정 
	 */
	@RequestMapping(value = "/test/login5.do", method = { RequestMethod.GET, RequestMethod.POST })
	// @ModelAttribute를 이용해 전달되는 매개변수 값을 LoginVO 클래스와 이름이 같은 속성에 자동으로 설정.
	// addObject()를 이용할 필요 없이 info를 이용해 바로 JSP에서 LoginVO 속성에 접근할 수 있다.
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
	 * Model 클래스 이용해 값 전달하기
	 * Model 클래스를 이용하면 메소드 호출 시 JSP로 값을 바로 바인딩하여 전달할 수 있다.
	 * Model 클래스의 addAttribute() 메소드는 ModelAndView의 addObject() 메소드와 같은 기능을 한다.
	 * Model 클래스는 따로 뷰 정보를 전달할 필요가 없을 때 사용하면 편리하다.
	 */
	@RequestMapping(value = "/test/login6.do", method = { RequestMethod.GET, RequestMethod.POST })
	// Model :  메소드 호출 시 Model 클래스 객체를 생성
	public String login5(Model model,
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		// JSP에서 전달할 데이터를 model에 addAttribute() 메소드를 이용해서 바인딩
		model.addAttribute("userID", "hong");
		model.addAttribute("userName", "홍길동");
		return "test/result";
	}	
}
