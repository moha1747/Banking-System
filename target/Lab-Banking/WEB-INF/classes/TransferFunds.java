import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class TransferFunds extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        String usernameID = (String)session.getAttribute("usernameID");
        if (usernameID == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if session is not found
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        initPage(out);

        // Assuming 'MainDatabase' and 'UserAccount' classes exist to handle database operations
        MainDatabase database = new MainDatabase();
        List<UserAccount> Balances = database.getMainObjectByUsernameID(usernameID).getBalances();

        out.println("<div class='container'>");
        out.println("  <h1>Transfer Funds</h1>");
        out.println("  <form method='POST' action='Transfer'>");

        // Dropdown for 'Transfer From'
        out.println("    <div class='input-group'>");
        out.println("      <label for='fromAccount'>Transfer From:</label>");
        out.println("      <select id='fromAccount' name='transfer-from-ID' onchange='updateToAccount(this.value)'>");
        for (UserAccount account : Balances) {
            out.println("        <option value='" + account.getAccountID() + "'>" + account.getAccountType() + " - " + account.getAccountID().substring(0, 4) + "</option>");
        }
        out.println("      </select>");
        out.println("    </div>");

        // Dropdown for 'Transfer To'
        out.println("    <div class='input-group'>");
        out.println("      <label for='toAccount'>Transfer To:</label>");
        out.println("      <select id='toAccount' name='transfer-to-ID'>");
        for (UserAccount account : Balances) {
            out.println("        <option value='" + account.getAccountID() + "'>" + account.getAccountType() + " - " + account.getAccountID().substring(0, 4) + "</option>");
        }
        out.println("      </select>");
        out.println("    </div>");

        // Input for amount
        out.println("    <div class='input-group'>");
        out.println("      <label for='amount'>Amount ($):</label>");
        out.println("      <input type='number' id='amount' name='amount' placeholder='Enter amount' step='10' min='0' required>");
        out.println("    </div>");

        // Submit button
        out.println("    <div class='buttons'>");
        out.println("      <button type='submit'>Transfer</button>");
        out.println("    </div>");

        out.println("  </form>");
        out.println("</div>");
        endPage(out);
    }

    private void initPage(PrintWriter out) {
        out.println("<!DOCTYPE html><html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>Transfer Funds</title>");
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
                ".container { text-align: center; background-color: #67C6E3; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 350px; margin: auto; }" +
                ".input-group { margin: 10px 0; display: flex; align-items: center; flex-direction: column; }" +
                "label { color: #000; font-weight: bold; margin-bottom: 5px; }" +
                "select, input[type='number'] { width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ccc; }" +
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
