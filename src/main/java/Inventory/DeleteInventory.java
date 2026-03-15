package Inventory;

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

@WebServlet("/DeleteInventory")
public class DeleteInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteInventory() {
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

			String sql = "DELETE FROM INVENTORY WHERE ID=?";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, Integer.parseInt(id));

			ps.executeUpdate();

			ps.close();
			con.close();

			response.sendRedirect("dashboard?msg=Inventory Deleted");

		}catch(Exception e){
			e.printStackTrace();
			response.sendRedirect("dashboard?msg=Error Deleting Inventory");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
