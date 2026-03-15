package Login;

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

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (SessionUtil.isLogin(request)) {
			response.sendRedirect("dashboard");
			return;
		}

		response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String appName = getServletContext().getInitParameter("appName");

		out.println("<html><body>");
		out.println("<h1>" + appName + "</h1>");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {

			Connection con = OracleDBConnection.getConnection();

			if (con == null) {
				throw new Exception("Database connection failed");
			}

			String sql = "SELECT * FROM KAAMCHALUDB WHERE USERNAME=? AND PASSWORD=?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				SessionUtil.createSession(request, username);
				response.sendRedirect("dashboard");
				return;

			} else {

				out.println("<p style='color:red;'>Invalid username or password</p>");

				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}

			out.println("</body></html>");

			rs.close();
			ps.close();
			con.close();

		} catch (Exception e) {

			System.out.println(e);
			out.println("<p style='color:red;'>Login failed</p>");
		}
	}
}