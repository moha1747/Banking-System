import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate the current session
        HttpSession session = request.getSession(false); // Get session if exists
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }

        // Redirect to the login page or home page
        response.sendRedirect("login.htm");
    }
}
