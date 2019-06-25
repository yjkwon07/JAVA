package com.myspring.SpringMaven_test.ex03;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {
	// mail-context,xml���� ������ ���� �ڵ����� ����
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage preConfiguredMessage;

	@Async
	public void sendMail(String to, String subject, String body) {
		// MimeMessage Ÿ�� ��ü�� ����
		MimeMessage message = mailSender.createMimeMessage();
		try {
			// ������ ������ ���� MimeMessageHelper��ü������
			MimeMessageHelper messageHelper = 
					new MimeMessageHelper(message, true, "UTF-8");
			//messageHelper.setCc("zzzzzz@naver.com");
			// ���ϼ��� �� ������ �̸����� ǥ�õǰ� �Ѵ�.
			// �������� ������ �ۼ��� ���� �ּҰ� ǥ��
			messageHelper.setFrom("yjkwon9073@gmail.com", "ȫ�浿");
			// ����, ����ó, ������ ������ ������ ������.
			messageHelper.setSubject(subject);
			messageHelper.setTo(to); 
			messageHelper.setText(body);
			mailSender.send(message);  
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// mail-context.xml���� �̸� ������ ���� �ּҷ� ���� ������ ������.
	@Async
	public void sendPreConfiguredMail(String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}
}

