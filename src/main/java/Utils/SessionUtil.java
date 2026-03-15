package Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void createSession(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
    }

    public static String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            return (String) session.getAttribute("username");
        }
        return null;
    }

    public static boolean isLogin(HttpServletRequest request) {
        return getSession(request) != null;
    }

    public static void destroySession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}