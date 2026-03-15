package Staff;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.OracleDBConnection;
import Utils.SessionUtil;

@WebServlet("/DeleteStaff")
public class DeleteStaff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteStaff() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!SessionUtil.isLogin(request)){
			response.sendRedirect("login.html");
			return;
		}

		String id = request.getParameter("id");

		try {

			Connection con = OracleDBConnection.getConnection();

			String sql = "DELETE FROM STAFF WHERE ID=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, Integer.parseInt(id));

			ps.executeUpdate();

			ps.close();
			con.close();

			response.sendRedirect("dashboard?msg=Staff Deleted");

		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("dashboard?msg=Error Deleting Staff");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
