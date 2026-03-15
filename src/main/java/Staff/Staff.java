package Staff;

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

public class Staff extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Staff() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!SessionUtil.isLogin(request)) {
			response.sendRedirect("login.html");
			return;
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/staff.html");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String role = request.getParameter("role");

		try {

			Connection con = OracleDBConnection.getConnection();

			if (id != null && !id.trim().isEmpty()) {

				String sql = "UPDATE STAFF SET NAME=?,EMAIL=?,PHONE=?,ROLE=? WHERE ID=?";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, phone);
				ps.setString(4, role);
				ps.setInt(5, Integer.parseInt(id));

				ps.executeUpdate();
				ps.close();

				response.sendRedirect("dashboard?msg=Staff Updated Successfully");

			} else {

				String sql = "INSERT INTO STAFF (NAME,EMAIL,PHONE,ROLE) VALUES (?,?,?,?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, name);
				ps.setString(2, email);
				ps.setString(3, phone);
				ps.setString(4, role);

				ps.executeUpdate();
				ps.close();

				response.sendRedirect("dashboard?msg=Staff Added Successfully");
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("dashboard?msg=Error Saving Staff");
		}
	}
}