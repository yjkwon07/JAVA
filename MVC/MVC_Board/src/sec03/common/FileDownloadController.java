package sec03.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadController
 */
@WebServlet("/download.do")
public class FileDownloadController extends HttpServlet {
	private static String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		// 이미지 파일 이름과 글 번호를 가져온다.
		String imageFileName = (String) request.getParameter("imageFileName");
		String articleNO = request.getParameter("articleNO");
		System.out.println("File down-> imageFileName=" + imageFileName);
		
		// OutputStream, PrintWriter중 하나만 선언
		// 이미  OutputStream선언이 되었으므로 PrintWriter불가
		// out만 돌아감
		OutputStream out = response.getOutputStream();
//		PrintWriter pw = new PrintWriter(out);
		
		// 에러!! 둘 중 하나만 get하기 
//		PrintWriter pw = response.getWriter();

/*	
		out.write(new String("<html><body>").getBytes());
		out.write(new String("Gooddf").getBytes());
		out.write(new String("</body></html>").getBytes());
*/
		// 글 번호에 대한 파일 경로를 설정
		String path = ARTICLE_IMAGE_REPO + "\\" + articleNO + "\\" + imageFileName;
		File imageFile = new File(path);

		response.setHeader("Cache-Control", "no-cache");
		// 이미지 파일을 내려 받는 데 필요한 response에 헤더 정보를 설정
		response.addHeader("Content-disposition", "attachment;fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(imageFile);
		// 버퍼를 이용해 한 번에 8kb씩 전송
		byte[] buffer = new byte[1024 * 8];
		while (true) {
			int count = in.read(buffer);
			if (count == -1)
				break;
			out.write(buffer, 0, count);
		}
		
/*	
		// 그냥 출력 (text)용 
		// img태그에서 인식 안됨 (binary 식으로 받아야 하는데 text로 인코딩 되어 받아지게 되므로 출력이 안됨)
		// reader writer
		BufferedInputStream bf = new BufferedInputStream(in);
		int i = -1;
		byte[] buffer = new byte[1024 * 8];
		while((i = bf.read()) != -1) {
			pw.write(i);
		}
*/	
		
/*
		String a = "gooddfdfdfdfdfdsfsfassdfasdfasdf";
		byte b[] = a.getBytes(); 
		
		out.write(b);
		// out이 먼저 선언이 될경우 pw응답 없음
		pw.println(a);
		pw.println(a);		
		pw.println(a);
*/	
		in.close();
		out.close();
//		pw.close();
	}
}
 