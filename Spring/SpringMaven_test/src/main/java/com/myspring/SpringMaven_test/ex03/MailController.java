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
 * @EnableAsymc�� �����ؼ� �޼ҵ带 ȣ���� ��� �񵿱�� �����ϰ� �ϴ� @Async �ֳ����̼� ����� ����� �� �ִ�.
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
        // mailService�� sendMail() �޼ҵ�� ���� ���� ��(�ּ�, ����, ����)�� ����
        mailService.sendMail("yjkwon9073@naver.com","�׽�Ʈ ����","�ȳ��ϼ���.���� ���� �����Դϴ�.");
        // mail-context.xml�� ������ ���� �ּҷ� ������ ������.
        mailService.sendPreConfiguredMail("�׽�Ʈ �����Դϴ�.");
        out.print("������ ���½��ϴ�!!");
    }
}


