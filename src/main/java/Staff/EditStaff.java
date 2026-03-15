package Staff;

import java.io.IOException;
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
import Utils.SessionUtil;

//@WebServlet("/EditStaff")
public class EditStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SessionUtil.isLogin(request)){
			response.sendRedirect("login.html");
			return;
		}

		String id = request.getParameter("id");

		try{

			Connection con = OracleDBConnection.getConnection();

			String sql = "SELECT * FROM STAFF WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1,Integer.parseInt(id));

			ResultSet rs = ps.executeQuery();

			if(rs.next()){

				request.setAttribute("id", rs.getInt("ID"));
				request.setAttribute("name", rs.getString("NAME"));
				request.setAttribute("email", rs.getString("EMAIL"));
				request.setAttribute("phone", rs.getString("PHONE"));
				request.setAttribute("role", rs.getString("ROLE"));

			}

			rs.close();
			ps.close();
			con.close();

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/staff.html");
			rd.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
