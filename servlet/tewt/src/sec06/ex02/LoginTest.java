package sec06.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * Servlet implementation class LoginTest
 */
@WebServlet("/loginsessions")
public class LoginTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ServletContext context = null;
	List<String> user_list = new ArrayList<>();
	String ischange = "istrue";
	List<User> user_check = new ArrayList<>();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *      
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		context = getServletContext();
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		LoginImpl loginUser = new LoginImpl(user_id, user_pw);
		
		if (session.isNew()) {
			session.setAttribute("loginUser", loginUser);
			user_list.add(user_id);
			ischange += "true";
			user_check.add(new User(loginUser));
			context.setAttribute("user_check", user_check);
			context.setAttribute("user_list", user_list);
			context.setAttribute("ischange", ischange);
		}

		out.println("<html><body>");
		out.println("아이디는 " + loginUser.user_id + "<br>");
		out.println("총 접속자수는" + LoginImpl.total_user + "<br><br>");
		out.println("접속 아이디:<br>");

		List<String> list = (ArrayList<String>)context.getAttribute("user_list");
		for (int i = 0; i < list.size(); i++) {
			out.println(list.get(i) + "<br>");
		}
		
		
		List<User> checks =(ArrayList<User>)context.getAttribute("user_check");
		for(User lists : checks) {
			out.println(lists._getuser() + "<br>");
		}
		
		// 공유 변수 들인가?
		out.println("list size :"+ list.size());
		out.println("user_list size :" + user_list.size());
		out.print(ischange);
		out.println("<a href='seeslogout?user_id=" + user_id + "'>로그아웃 </a>");
		out.println("</body></html>");
	}
}