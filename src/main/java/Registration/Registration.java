package Registration;

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

public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Registration() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (SessionUtil.isLogin(request)) {
			response.sendRedirect("dashboard");
			return;
		}

		response.sendRedirect("registration.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String appName = getServletContext().getInitParameter("appName");

		out.println("<html><body>");
		out.println("<h1>" + appName + "</h1>");

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {

			Connection con = OracleDBConnection.getConnection();

			if (con == null) {
				throw new Exception("Database connection failed");
			}

			String checkSql = "SELECT * FROM KAAMCHALUDB WHERE USERNAME=? OR EMAIL=?";
			PreparedStatement checkPs = con.prepareStatement(checkSql);

			checkPs.setString(1, username);
			checkPs.setString(2, email);

			ResultSet rs = checkPs.executeQuery();

			if (rs.next()) {

				out.println("<p style='color:red;'>Username or Email already exists</p>");

				RequestDispatcher rd = request.getRequestDispatcher("registration.html");
				rd.include(request, response);

			} else {

				String sql = "INSERT INTO KAAMCHALUDB (USERNAME, EMAIL, PASSWORD) VALUES (?, ?, ?)";

				PreparedStatement ps = con.prepareStatement(sql);

				ps.setString(1, username);
				ps.setString(2, email);
				ps.setString(3, password);

				ps.executeUpdate();
				ps.close();

				out.println("<p style='color:green;'>Registration Successful!</p>");

				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}

			out.println("</body></html>");

			rs.close();
			checkPs.close();
			con.close();

		} catch (Exception e) {

			e.printStackTrace();
			out.println("<p style='color:red;'>Registration failed</p>");
		}
	}
}