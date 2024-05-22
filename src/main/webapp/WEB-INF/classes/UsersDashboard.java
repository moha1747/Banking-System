import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class UsersDashboard extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        out.println("<!DOCTYPE html><html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>List of all users</title>");
        out.println("  <meta http-equiv='Cache-Control' content='no-cache'>");
        out.println("  <meta http-equiv='Pragma' content='no-cache'>");
        out.println("  <meta http-equiv='Expires' content='0'>");
        out.println("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>");
        out.println(getStyles());
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("  <h1>All Users</h1>");

        // LIST OUT ALL USERS
        MainDatabase database = new MainDatabase();
        List<MainAccount> accountList = database.getAllMainObjects();

        out.println("  <table>");
        out.println("    <tr><th>Account Username</th><th>UsernameID</th><th>Available Balance(s)</th></tr>");

        for (MainAccount account : accountList) {
            List<UserAccount> BalancesFromMain = account.getBalances();
            out.println("    <tr>");
            out.println("      <td>" + account.getUsername() + "</td>");
            out.println("      <td>" + account.getUsernameID() + "</td>");
            out.println("      <td>");
            out.println("        <ul>");
            for (UserAccount Balance : BalancesFromMain) {
                out.println("          <li><strong>" + Balance.getAccountType() + "</strong>: " + Balance.getBalanceString() + " (" + Balance.getAccountID() + ")</li>");
            }
            out.println("        </ul>");
            out.println("      </td>");
            out.println("    </tr>");
        }
        out.println("  </table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                "header { position: absolute; top: 10px; left: 10px; font-size: 1.5em; color: #fff; display: flex; align-items: center; }" +
                "header i { margin-right: 10px; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 80%; margin: auto; }" +
                "table { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
                "th, td { border: 1px solid #ffffff; padding: 8px; text-align: left; color: #ffffff; }" +
                "th { background-color: #378CE7; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
