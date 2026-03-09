package Login;

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

//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    
		PrintWriter out = response.getWriter();
		
        String appName = getServletContext().getInitParameter("appName");
        
    	out.println("<html><body>");
		out.println("<h1>" + appName + "</h1>");
		
        String username = request.getParameter("username");
        String passowrd = request.getParameter("password");
        
        try {
        	Connection con = OracleDBConnection.getConnection();	
           	if (con == null) {
        	    throw new Exception("Database connection failed");
        	}
           	
        	String sql = "SELECT * FROM KaamChaluDB WHERE USERNAME=? AND PASSWORD=?";
        	PreparedStatement ps = con.prepareStatement(sql);
        	
        	ps.setString(1, username);
        	ps.setString(2, passowrd);
        	
        	ResultSet rs = ps.executeQuery();
        		
        	if(rs.next()) {
        		out.println("<p style='color:green;'>Login Successfully!</p>");
        		RequestDispatcher rd = request.getRequestDispatcher("deshboard.html");
        		rd.forward(request, response);
        	}else {
        		out.println("<p style='color:red;'>Invalid username or password</p>");
        		RequestDispatcher rd = request.getRequestDispatcher("login.html");
        		rd.include(request, response);
        	}
         	
    		out.println("</body></html>");
        	rs.close();
        	ps.close();
        	con.close();

        }catch (Exception e) {
        	System.out.println(e);
        	 out.println("<p style='color:red;'>Login failed</p>");
		}
       
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
