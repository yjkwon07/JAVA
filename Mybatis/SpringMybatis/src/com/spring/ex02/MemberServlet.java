package com.spring.ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mem2.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws  ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		// selectone
		// MemberDAO의 selectName() 호출
		//String name = dao.selectName();
		// MemberDAO의 selectPwd() 호출
		int pwd = dao.selectPwd();
		PrintWriter pw = response.getWriter();
		pw.write("<script>");
		// 검색 결과
		//pw.write("alert(' 회원 아이디: " + name +"');");
		pw.write("alert(' 비밀번호 : "+ pwd+"');");
		pw.write("</script>");
	}
}
