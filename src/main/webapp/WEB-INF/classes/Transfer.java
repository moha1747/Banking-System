import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Transfer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        MainDatabase database = new MainDatabase();
        String transferFromID = request.getParameter("transfer-from-ID");
        String transferToID = request.getParameter("transfer-to-ID");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String usernameID = (String)session.getAttribute("usernameID");
        String  username = (String)session.getAttribute("username");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html><html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>");
        out.println("  <META HTTP-EQUIV='Pragma' CONTENT='no-cache'>");
        out.println("  <META HTTP-EQUIV='Expires' CONTENT='0'>");
        out.println("  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'>");
        out.println("  <title>Transfer</title>");
        out.println(getStyles());
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");

        MainAccount mainAccount = database.getMainObjectByUsernameID(usernameID);
        if (mainAccount == null) {
            out.println("<h2 class='error'>Could not find the Main/main account (not transfer FROM or TO account).</h2>");
        } else {
            List<UserAccount> Balances = mainAccount.getBalances();
            UserAccount BalanceFrom = null;
            UserAccount BalanceTo = null;

            for (UserAccount account : Balances) {
                if (account.getAccountID().equals(transferFromID)) {
                    BalanceFrom = account;
                }
                if (account.getAccountID().equals(transferToID)) {
                    BalanceTo = account;
                }
            }

            if (BalanceFrom != null && BalanceTo != null) {
                if (amount > BalanceFrom.getBalance()) {
                    out.println("<h2 class='error'>Insufficient funds to transfer.</h2>");
                } else {
                    BalanceFrom.withdraw(amount);
                    BalanceTo.deposit(amount);

                    Logs log = new Logs();
                    log.appendToLog(username, "Successfully transferred $" + String.format("%.2f", amount) + "from " + BalanceFrom + "(type :" + BalanceFrom.getAccountType()+ ")" + " to " + BalanceTo + "(type :" + BalanceTo.getAccountType()+ ")");
                    out.println("<h2 class='success'>Successfully transferred $" + String.format("%.2f", amount) + " to " + BalanceTo.getAccountType() + ".</h2>");

                    updateDatabase(database, mainAccount);
                }
            } else {
                out.println("<h2 class='error'>One or both accounts could not be found.</h2>");
            }
        }

        out.println("<a href='AccountBalances'><button>Return to Account Summary</button></a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void updateDatabase(MainDatabase database, MainAccount modifiedMain) throws IOException {
        List<MainAccount> allMains = database.getAllMainObjects();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MainDatabase.txt"))) {
            oos.reset();
            for (MainAccount Main : allMains) {
                oos.writeObject(Main.getUsernameID().equals(modifiedMain.getUsernameID()) ? modifiedMain : Main);
            }
        }
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                "header { position: absolute; top: 10px; left: 10px; font-size: 1.5em; color: #fff; }" +
                "header h2 { display: flex; align-items: center; }" +
                "header i { margin-right: 10px; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); width: 50%; margin: auto; }" +
                ".error { color: #FF1E00; }" +
                ".success { color: #367E18; }" +
                "button { background-color: #378CE7; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }" +
                "button:hover { background-color: #67a9ea; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
