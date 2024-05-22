import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CloseBalance extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out         = response.getWriter();
        HttpSession session     = request.getSession(false);
        String  usernameID   = (String)session.getAttribute("usernameID");
        String  username   = (String)session.getAttribute("username");
        String  accountID    = request.getParameter("accountID");

        MainAccount mainAccount = new MainDatabase().getMainObjectByUsernameID(usernameID);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\" />");
        out.println("<meta http-equiv='Cache-Control' content='no-cache'>");
        out.println("<meta http-equiv='Pragma' content='no-cache'>");
        out.println("<meta http-equiv='Expires' content='0'>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>");
        out.println("<title>Closing Account</title>");
        out.println(getStyles());
        out.println("</head><body>");
        out.println("<div class='container'>");
        out.println("<h1>Account Closure</h1>");

        int isRemoved = mainAccount.removeBalanceByAccountID(accountID);
        if (isRemoved == 0) {
            updateDatabase(mainAccount, usernameID);
            out.println("<h2 class='success'>Account successfully closed!</h2>");
            out.println("<script>setTimeout(function() { window.location.href = 'AccountBalances'; }, 5000);</script>");
        } else {
            out.println("<h2 class='error'>Failed to close the account. Please try again.</h2>");
        }
        Logs log = new Logs();
        log.appendToLog(username, "Account " + accountID +  " successfully closed! ");

        out.println("<a href='AccountBalances'><button>Return to Account Summary</button></a>");
        out.println("</div></body></html>");
    }

    private void updateDatabase(MainAccount mainAccount, String usernameID) throws IOException {
        List<MainAccount> allMainObjects = new MainDatabase().getAllMainObjects();
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("MainDatabase.txt"));
        outputStream.reset();
        for (MainAccount MainObject : allMainObjects) {
            if (MainObject.getUsernameID().equals(usernameID)) {
                outputStream.writeObject(mainAccount);
            } else {
                outputStream.writeObject(MainObject);
            }
        }
        outputStream.close();
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 400px; margin: auto; }" +
                ".error { color: red; }" +
                ".success { color: green; }" +
                "a { color: #ffffff; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "button { background-color: #378CE7; color: white; padding: 10px 50px; border: none; border-radius: 5px; cursor: pointer; }" +
                "button:hover { background-color: #67a9ea; }" +

                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
