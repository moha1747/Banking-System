import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateBalance extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String usernameID = (String)session.getAttribute("usernameID");
        if (usernameID == null) {
            usernameID = request.getParameter("userID");
            session.setAttribute("usernameID", usernameID);
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        initPage(out);

        out.println("<div class='container'>");
        out.println("  <h1>Create a New Sub-Account</h1>");
        out.println("  <form method='POST' action='CreateBalanceProcessor'>");
        out.println("    <div class='input-group'>");
        out.println("      <i class='fas fa-briefcase'></i>");
        out.println("      <select name='new-Balance-type'>");
        out.println("        <option value='checking'>Checking</option>");
        out.println("        <option value='saving'>Saving</option>");
        out.println("        <option value='brokerage'>Brokerage</option>");
        out.println("      </select>");
        out.println("    </div>");
        out.println("    <div class='input-group'>");
        out.println("      <i class='fas fa-dollar-sign'></i>");
        out.println("      <input type='number' name='new-Balance-initial-deposit' placeholder='Initial Deposit ($)' step='10' min='0' required>");
        out.println("    </div>");
        out.println("    <div class='buttons'>");
        out.println("      <button type='submit'>Submit</button>");
        out.println("    </div>");
        out.println("  </form>");
        out.println("</div>");
        endPage(out);
    }

    private void initPage(PrintWriter out) {
        out.println("<!DOCTYPE html><html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>Create New Sub-Account</title>");
        out.println("  <meta http-equiv='Cache-Control' content='no-cache'>");
        out.println("  <meta http-equiv='Pragma' content='no-cache'>");
        out.println("  <meta http-equiv='Expires' content='0'>");
        out.println("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>");
        out.println(getStyles());
        out.println("</head><body>");

    }

    private void endPage(PrintWriter out) {
        out.println("</body></html>");
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #fff; }" +
                "header { position: absolute; top: 10px; left: 10px; font-size: 1.2em; color: #fff; display: flex; align-items: center; }" +
                "header i { margin-right: 10px; }" +
                ".container { text-align: center; background-color: #67C6E3; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 350px; margin: auto; }" +
                ".input-group { margin: 10px 0; display: flex; align-items: center; border: 1px solid #ccc; border-radius: 5px; overflow: hidden; }" +
                ".input-group i { padding: 10px; background-color: #DFF5FF; color: #666; }" +
                "select, input[type='number'] { flex: 1; border: none; padding: 10px; }" +
                ".buttons { display: flex; justify-content: center; margin-top: 20px; }" +
                "button { background-color: #378CE7; color: white; padding: 10px 50px; border: none; border-radius: 5px; cursor: pointer; }" +
                "button:hover { background-color: #67a9ea; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
