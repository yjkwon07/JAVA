package com.myspring.SpringMaven_test.ex03;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*
 * @EnableAsymc를 지정해서 메소드를 호출할 경우 비동기로 동작하게 하는 @Async 애너테이션 기능을 사용할 수 있다.
 */
@Controller
@EnableAsync
public class MailController {
    @Autowired
    private MailService mailService;
 
    @RequestMapping(value = "/sendMail.do", method = RequestMethod.GET)
    public void sendSimpleMail(HttpServletRequest request, HttpServletResponse response) 
                                                          throws Exception{
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        // mailService의 sendMail() 메소드로 메일 관련 값(주소, 제목, 내용)을 전달
        mailService.sendMail("yjkwon9073@naver.com","테스트 메일","안녕하세요.보낸 메일 내용입니다.");
        // mail-context.xml에 설정한 메일 주소로 내용을 보낸다.
        mailService.sendPreConfiguredMail("테스트 메일입니다.");
        out.print("메일을 보냈습니다!!");
    }
}


