package GetHeader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/GetHeader")
public class GetHeader extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		out.println("<html><head>");
		out.println("<title>Get Header</title>");
		out.println("<link rel='stylesheet' href='css/styles.css'>");
		out.println("</head><body>");

		out.println("<h2>Request Headers</h2>");

		out.println("<table border='1'>");
		out.println("<tr><th>No</th><th>Header</th><th>Value</th></tr>");

		Enumeration<String> headers = request.getHeaderNames();

		int i = 1;

		while (headers.hasMoreElements()) {
			String name = headers.nextElement();
			String value = request.getHeader(name);

			out.println("<tr>");
			out.println("<td>" + i++ + "</td>");
			out.println("<td>" + name + "</td>");
			out.println("<td>" + value + "</td>");
			out.println("</tr>");
		}

		out.println("</table>");

		out.println("<br><a href='index.html'>Back to Home</a>");

		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
