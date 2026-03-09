package Employees;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.OracleDBConnection;


//@WebServlet("/Employees")
public class Employees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		try {

			Connection con = OracleDBConnection.getConnection();

			String sql = "SELECT * FROM EMPLOYEES";

			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			out.println("<table>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>USERNAME</th>");
			out.println("<th>EMAIL</th>");
			out.println("<th>ACTIONS</th>");
			out.println("</tr>");

			while(rs.next()){

				out.println("<tr>");

				out.println("<td>"+rs.getInt("ID")+"</td>");
				out.println("<td>"+rs.getString("USERNAME")+"</td>");
				out.println("<td>"+rs.getString("EMAIL")+"</td>");

				out.println("<td>");
				out.println("<button>UPDATE</button>");
				out.println("<button>DELETE</button>");
				out.println("</td>");

				out.println("</tr>");

			}

			out.println("</table>");

			rs.close();
			ps.close();
			con.close();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    try {

	        Connection con = OracleDBConnection.getConnection();

	        String sql = "INSERT INTO EMPLOYEES (ID, USERNAME, EMAIL, PASSWORD) VALUES (EMP_SEQ.NEXTVAL, ?, ?, ?)";

	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setString(1, username);
	        ps.setString(2, email);
	        ps.setString(3, password);

	        ps.executeUpdate();

	        ps.close();
	        con.close();

	        response.sendRedirect("employees");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
