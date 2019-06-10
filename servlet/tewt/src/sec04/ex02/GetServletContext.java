package sec04.ex02;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( 
urlPatterns = {"/cget"})
public class GetServletContext extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;

	@Override
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/cget");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// getAttribute(String name)
		out.print("<h1>getAttribute(member)</h1>");
		List member = (ArrayList) context.getAttribute("member");
		String name = (String) member.get(0);
		int age = (Integer) member.get(1);
		out.print("<html><body>");
		out.print(name +"<br>");
		out.print(age + "<br>");
		out.print("<hr>");

		// getAttributeNames()
		out.print("<h1>getAttributeNames()</h1>");
		Enumeration<String> names = context.getAttributeNames();
		while(names.hasMoreElements()){
			out.print(names.nextElement()+"<br>");
		}
		out.print("<hr>");

		// getContext(String uripath)
		out.print("<h1>getContext(/tewt)</h1>");
		ServletContext uricontext = context.getContext("/tewt");
		member = (ArrayList)uricontext.getAttribute("member");
		name = (String) member.get(0);
		age = (Integer) member.get(1);
		out.print(name +"<br>");
		out.print(age + "<br>");
		out.print("<hr>");

		// getInitParmeter(String name)
		out.print("<h1>getInitParmeter(mm)</h1>");
		Enumeration<String> enums = context.getInitParameterNames();
		while(enums.hasMoreElements()){
			String check = (String)enums.nextElement();
			String s = context.getInitParameter(check);
			out.print(s+"<br>");
		}
		out.print("<h2>getAttribute(mm)</h2>");
		out.print((String)context.getAttribute("mm")+ "<br>");
		out.print("<h2>getInitParameter(mm)</h2>");
		out.print((String)context.getInitParameter("mm")+"<br>");
		out.print("<hr>");

		// getMajorVersion()
		out.print("<h1>getMajorVersion()</h1>");
		int major = context.getMajorVersion();
		out.print(major +"<br>");
		out.print("<hr>");

		// getRealPath(String path)
		out.print("<h1>getRealPath(/cset)</h1>");
		String realpath = context.getRealPath("/cset");
		out.print(realpath +"<br>");
		out.print("<hr>");

		// getResource(String path)
		out.print("<h1>getResource(popUp.html)</h1>");
		URL url = context.getResource("popUp.html");
		URLConnection urlCon = url.openConnection();
		urlCon.connect();
		InputStream instream = urlCon.getInputStream();
		try {
			int c;
			while((c = instream.read()) != -1 ) {
				out.print((char)c);
			}
			instream.close();
		}catch(IOException e) {

		}
		out.print(urlCon.getContentLengthLong()+"<br>");


		// getServerInfo()
		out.print("<h1>getServerInfo()</h1>");
		String ServerInfo = context. getServerInfo();
		out.print(ServerInfo +"<br>");
		out.print("<hr>");

		// getServletContextName()
		out.print("<h1>getServletContextName()</h1>");
		String getServletContextName = context. getServletContextName();
		out.print(getServletContextName +"<br>");
		out.print("<hr>");

		// log(String msg)
		out.print("<h1>log(good)</h1>");
		context. log("good");
		out.print("<hr>");

		// removeAttribute(String name)
		out.print("<h1>removeAttribute(member)</h1>");
		context.removeAttribute("member");
		out.print("<hr>");

		// setAttribute(String name, Object object)
		out.print("<h1>setAttribyte(String name, Object object)</h1>");
		String members = "gg";
		context.setAttribute("member", members);
		out.print((String)context.getAttribute("member")+"<br>");
		out.print("<hr>");

		// config -> getInitParameter
		out.print("<h1>config -> getInitParameter(mmm)</h1>");
		String check = getInitParameter("mmm");
		out.print(check+"<br>");
		out.print("<hr>");

		out.print("</body></html>");
	} 
} 
