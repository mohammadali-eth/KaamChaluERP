package WebXml;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@WebServlet("/WebXml")
public class WebXml extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

	
		String author = getServletConfig().getInitParameter("author");
		
		out.println("<html><head>");
		out.println("<title>Web XML Info</title>");
		out.println("<link rel='stylesheet' href='css/styles.css'>");
		out.println("</head><body>");

		out.println("<h2>Web.xml Configuration</h2>");

		out.println("<table border='1'>");
		out.println("<tr><th>Type</th><th>Name</th><th>Value</th></tr>");

		String appName = getServletContext().getInitParameter("appName");

		out.println("<tr>");
		out.println("<td>Context Param</td>");
		out.println("<td>appName</td>");
		out.println("<td>" + appName + "</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td>Init Param</td>");
		out.println("<td>author</td>");
		out.println("<td>" + author + "</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td>Request</td>");
		out.println("<td>Context Path</td>");
		out.println("<td>" + request.getContextPath() + "</td>");
		out.println("</tr>");

		out.println("</table>");

		out.println("<br><a href='index.html'>Back to Home</a>");

		out.println("</body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
