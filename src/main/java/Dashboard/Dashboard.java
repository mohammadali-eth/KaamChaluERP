package Dashboard;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Utils.OracleDBConnection;
import Utils.SessionUtil;

public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!SessionUtil.isLogin(request)) {
			response.sendRedirect("login.html");
			return;
		}

		String user = SessionUtil.getSession(request);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String msg = request.getParameter("msg");

		out.println("<div class='container'>");

		if (msg != null) {
			out.println("<p style='color:lime'>" + msg + "</p>");
		}

		out.println("<div class='navbar'>");
		out.println("<h2>KaamChaluERP</h2>");
		out.println("<div>");
		out.println("<span>Welcome " + user + "</span> | ");
		out.println("<a href='logout'>Logout</a>");
		out.println("</div>");
		out.println("</div>");

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dashboard.html");
		rd.include(request, response);

		try {

			Connection con = OracleDBConnection.getConnection();

			// STAFF LIST
			String sqlStaff = "SELECT * FROM STAFF";
			PreparedStatement psStaff = con.prepareStatement(sqlStaff);
			ResultSet rsStaff = psStaff.executeQuery();

			out.println("<div class='card'>");
			out.println("<h2>Staff List</h2>");

			out.println("<table>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>Name</th>");
			out.println("<th>Email</th>");
			out.println("<th>Phone</th>");
			out.println("<th>Role</th>");
			out.println("<th>Action</th>");
			out.println("</tr>");

			while (rsStaff.next()) {
				out.println("<tr>");
				out.println("<td>" + rsStaff.getInt("ID") + "</td>");
				out.println("<td>" + rsStaff.getString("NAME") + "</td>");
				out.println("<td>" + rsStaff.getString("EMAIL") + "</td>");
				out.println("<td>" + rsStaff.getString("PHONE") + "</td>");
				out.println("<td>" + rsStaff.getString("ROLE") + "</td>");

				out.println("<td>");

				out.println("<a href='staff?id=" + rsStaff.getInt("ID")
						+ "&name=" + rsStaff.getString("NAME")
						+ "&email=" + rsStaff.getString("EMAIL")
						+ "&phone=" + rsStaff.getString("PHONE")
						+ "&role=" + rsStaff.getString("ROLE")
						+ "' style='color:green;'>Edit</a> &nbsp; ");

				out.println("<a href='deleteStaff?id=" + rsStaff.getInt("ID") + "' style='color:red;'>Delete</a>");

				out.println("</td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("</div>");

			// INVENTORY LIST
			String sqlInv = "SELECT * FROM INVENTORY";
			PreparedStatement psInv = con.prepareStatement(sqlInv);
			ResultSet rsInv = psInv.executeQuery();

			out.println("<div class='card'>");
			out.println("<h2>Inventory List</h2>");

			out.println("<table>");
			out.println("<tr>");
			out.println("<th>ID</th>");
			out.println("<th>Item</th>");
			out.println("<th>Category</th>");
			out.println("<th>Qty</th>");
			out.println("<th>Price</th>");
			out.println("<th>Supplier</th>");
			out.println("<th>Purchase Type</th>");
			out.println("<th>Action</th>");
			out.println("</tr>");

			while (rsInv.next()) {
				out.println("<tr>");
				out.println("<td>" + rsInv.getInt("ID") + "</td>");
				out.println("<td>" + rsInv.getString("ITEM_NAME") + "</td>");
				out.println("<td>" + rsInv.getString("CATEGORY") + "</td>");
				out.println("<td>" + rsInv.getInt("QUANTITY") + "</td>");
				out.println("<td>" + rsInv.getInt("PRICE") + "</td>");
				out.println("<td>" + rsInv.getString("SUPPLIER") + "</td>");
				out.println("<td>" + rsInv.getString("PURCHASE_TYPE") + "</td>");

				out.println("<td>");

				out.println("<a href='inventory?id=" + rsInv.getInt("ID")
						+ "&item=" + rsInv.getString("ITEM_NAME")
						+ "&category=" + rsInv.getString("CATEGORY")
						+ "&qty=" + rsInv.getInt("QUANTITY")
						+ "&price=" + rsInv.getInt("PRICE")
						+ "&supplier=" + rsInv.getString("SUPPLIER")
						+ "&ptype=" + rsInv.getString("PURCHASE_TYPE")
						+ "'style='color:green;'>Edit</a> &nbsp ");

				out.println("<a href='deleteInventory?id=" + rsInv.getInt("ID") + "'style='color:red;'>Delete</a>");

				out.println("</td>");
				out.println("</tr>");
			}

			out.println("</table>");
			out.println("</div>");

			rsStaff.close();
			psStaff.close();
			rsInv.close();
			psInv.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			out.println("<p style='color:red;'>Error loading data</p>");
		}

		out.println("</div>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
}