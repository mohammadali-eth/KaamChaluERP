package Inventory;

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


//@WebServlet("/EditInventory")
public class EditInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EditInventory() {
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

			String sql = "SELECT * FROM INVENTORY WHERE ID=?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1,Integer.parseInt(id));

			ResultSet rs = ps.executeQuery();

			if(rs.next()){

				request.setAttribute("id", rs.getInt("ID"));
				request.setAttribute("item", rs.getString("ITEM_NAME"));
				request.setAttribute("category", rs.getString("CATEGORY"));
				request.setAttribute("qty", rs.getInt("QUANTITY"));
				request.setAttribute("price", rs.getInt("PRICE"));
				request.setAttribute("supplier", rs.getString("SUPPLIER"));

			}

			rs.close();
			ps.close();
			con.close();

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inventory.html");
			rd.forward(request, response);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
