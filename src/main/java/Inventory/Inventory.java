package Inventory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.OracleDBConnection;
import Utils.SessionUtil;

public class Inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Inventory() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!SessionUtil.isLogin(request)) {
			response.sendRedirect("login.html");
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inventory.html");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String item = request.getParameter("item");
		String category = request.getParameter("category");
		String qty = request.getParameter("qty");
		String price = request.getParameter("price");
		String supplier = request.getParameter("supplier");
		String type = request.getParameter("ptype");
		String warranty = request.getParameter("warranty");

		String[] featuresArr = request.getParameterValues("features");
		String features = "";

		if (featuresArr != null) {
			features = String.join(",", featuresArr);
		}

		try {

			Connection con = OracleDBConnection.getConnection();

			if (id != null && !id.trim().isEmpty()) {

				String sql = "UPDATE INVENTORY SET ITEM_NAME=?,CATEGORY=?,QUANTITY=?,PRICE=?,SUPPLIER=?,PURCHASE_TYPE=?,WARRANTY=?,FEATURES=? WHERE ID=?";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, item);
				ps.setString(2, category);
				ps.setInt(3, Integer.parseInt(qty));
				ps.setInt(4, Integer.parseInt(price));
				ps.setString(5, supplier);
				ps.setString(6, type);
				ps.setString(7, warranty);
				ps.setString(8, features);
				ps.setInt(9, Integer.parseInt(id));

				ps.executeUpdate();
				ps.close();

				response.sendRedirect("dashboard?msg=Inventory Updated Successfully");

			} else {

				String sql = "INSERT INTO INVENTORY (ITEM_NAME,CATEGORY,QUANTITY,PRICE,SUPPLIER,PURCHASE_TYPE,WARRANTY,FEATURES) VALUES (?,?,?,?,?,?,?,?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, item);
				ps.setString(2, category);
				ps.setInt(3, Integer.parseInt(qty));
				ps.setInt(4, Integer.parseInt(price));
				ps.setString(5, supplier);
				ps.setString(6, type);
				ps.setString(7, warranty);
				ps.setString(8, features);

				ps.executeUpdate();
				ps.close();

				response.sendRedirect("dashboard?msg=Inventory Added Successfully");
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard?msg=Error Saving Inventory");
		}
	}
}