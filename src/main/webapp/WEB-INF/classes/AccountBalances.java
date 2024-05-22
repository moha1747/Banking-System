import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AccountBalances extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session  = request.getSession();


        String  username = request.getParameter("login-username");
        String  password = request.getParameter("login-password");

        if (username == null) {
            username = (String)session.getAttribute("username");
        } else {
            session.setAttribute("username", username);
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        if ("admin".equals(username) && "admin".equals(password)) {
            response.sendRedirect("UsersDashboard"); // Redirect to the dashboard page
            return;
        }

        PrintWriter out = response.getWriter();
        initPage(out, username);

        MainDatabase database = new MainDatabase();

        if (username != null && password != null) {

            MainAccount user = database.validateLogin(username, password);

            if (user != null) {
                session.setAttribute("username", username);
                session.setAttribute("usernameID", user.getUsernameID());
                List<UserAccount> listOfBalances = database.getMainObject(username).getBalances();
                displayAccountDetails(out, username, listOfBalances, database);

            } else {
                redirectLogin(out);
            }
        } else { //Signup (new user)

            List<UserAccount> listOfBalances = database.getMainObject(username).getBalances();
            displayAccountDetails(out, username, listOfBalances, database);
        }

        session.setAttribute("usernameID", database.getMainID(username));
        session.setAttribute("username", username);


        endPage(out);
    }

    private void initPage(PrintWriter out, String username) {
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");
        out.println("<meta http-equiv='Cache-Control' content='no-cache'>");
        out.println("<meta http-equiv='Pragma' content='no-cache'>");
        out.println("<meta http-equiv='Expires' content='0'>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>");
        out.println("<title>Account Summary</title>");
        out.println(getStyles());
        out.println("</head><body>");
        out.println("<div class='container'>");

    }

    private void redirectLogin(PrintWriter out) {
        out.println("<title>Invalid Username</title>");
        out.println("<meta http-equiv='refresh' content='5; url=login.htm' />");
        out.println("<h2 style='color: #FF1E00'>Invalid username or password.<br>Please check your Credentials or Create an Account</h2>");
        out.println("</div></body></html>");
    }

    private String renderAccountTable(List<UserAccount> Balances) {
        StringBuilder sb = new StringBuilder("<table>");
        sb.append("<tr><th>Balance ID</th><th>Balance Type</th><th>Available Balance</th><th>Control</th></tr>");
        for (UserAccount account : Balances) {
            sb.append("<tr><td>").append(account.getAccountID(), 0, 5).append("...").append("</td>");  // Shortened for display
            sb.append("<td>").append(account.getAccountType().toUpperCase()).append("</td>");
            sb.append("<td>$").append(String.format("%.2f", account.getBalance())).append("</td>");
            sb.append("<td>");
            sb.append("<button onclick=\"location.href='CloseBalance?accountID=").append(account.getAccountID()).append("';\" style='display: block; margin: 0 auto; margin-left: 10px; background-color: #ff5847;'><i class='fas fa-times'></i> Close Account</button>");
            sb.append("</td></tr>");
        }
        sb.append("</table>");
        sb.append("<div class='button-group'>"); // Group buttons for spacing
        sb.append("<button onclick=\"location.href='CreateBalance';\"><i class='fas fa-plus'></i> Open a new account</button>");
        sb.append("<button onclick=\"location.href='TransferFunds';\" style='margin-left: 20px;' ><i class='fas fa-arrows-alt-h'></i> Transfer Funds</button>");
        sb.append("<button onclick=\"location.href='History';\" style='margin-left: 20px;'><i class='fas fa-history'></i> See History</button>");
        sb.append("</div>");

        return sb.toString();
    }

    private void displayAccountDetails(PrintWriter out, String username, List<UserAccount> Balances, MainDatabase database) {
        double totalBalance = Balances.stream().mapToDouble(UserAccount::getBalance).sum();
        String color = totalBalance >= 500 ? "#367E18" : "#FF1E00";
        out.println("<h1>Welcome to your account, " + username + "</h1>");
        out.println("<h3 style='color: " + color + "; margin: 20px;'>Total Balance: $" + String.format("%.2f", totalBalance) + "</h3>");
        out.println(renderAccountTable(Balances));
        out.println("<button style ='background-color: #EB5353;' onclick=\"location.href='Logout';\">Logout</button>");
        out.println("</div>");


    }



    private void endPage(PrintWriter out) {
        out.println("</body></html>");
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin: auto; width: 50%; }" +
                "table { width: 100%; border-collapse: collapse; }" +
                "th, td { border: 1px solid #ffffff; padding: 8px; text-align: left; color: #ffffff; }" +
                "th { background-color: #378CE7; }" +
                "button { background-color: #378CE7; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-top: 20px; }" +
                "button:hover { background-color: #67a9ea; }" +
                ".fa-plus, .fa-history, .fa-money-bill-wave, .fa-times { margin-right: 5px; }" +
                // Styles for form elements within the transfer section
                ".transfer-form { background-color: #f8f9fa; padding: 20px; border-radius: 10px; margin-top: 20px; width: fit-content; margin: auto; display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; }" +
                "select, input[type='number'] { padding: 8px; border-radius: 4px; border: 1px solid #ccc; }" +
                "label { display: block; margin-bottom: 5px; color: #333; }" +
                "input[type='submit'] { background-color: #378CE7; color: white; padding: 10px 50px; border: none; border-radius: 5px; cursor: pointer; flex-grow: 1; margin-top: 20px; }" +
                "input[type='submit']:hover { background-color: #67a9ea; }" +
                "input-row { background-color: #67a9ea; }" +
                ".form-section { display: flex; align-items: center; margin-right: 10px; }" +
                ".error, .success { text-align: center; margin-top: 20px; }" +
                ".error { color: #ff6f7c; }" +
                ".success { color: #4CAF50; }" +
                "</style>";
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
