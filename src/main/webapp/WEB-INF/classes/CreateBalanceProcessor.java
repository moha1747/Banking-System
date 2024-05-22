import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateBalanceProcessor extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out           = response.getWriter();
        HttpSession session       = request.getSession();
        String      usernameID    = (String) session.getAttribute("usernameID");
        String      accountType   = (String) request.getParameter("new-Balance-type");
        String      initialAmount = (String) request.getParameter("new-Balance-initial-deposit");
        String      username = (String)session.getAttribute("username");

        // Adds a new Balance to the Main account
        MainAccount mainAccount = new MainDatabase().getMainObjectByUsernameID(usernameID);
        UserAccount   newChildAccount = new UserAccount(mainAccount.getUsernameID(), accountType, Double.parseDouble(initialAmount));
        mainAccount.addBalance(newChildAccount);

        // Stores the new sub account into BalanceDatabase.txt
        File subDatabase = new File("BalanceDatabase.txt");
        if (subDatabase.length() == 0) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(subDatabase, true))) {
                outputStream.writeObject(newChildAccount);
            }
        } else {
            try (AppendingObjectOutputStream outputStream = new AppendingObjectOutputStream(new FileOutputStream(subDatabase, true))) {
                outputStream.writeObject(newChildAccount);
            }
        }

        List<MainAccount> allMainObjects = new MainDatabase().getAllMainObjects();

        // Rewrites every object to MainDatabase except the modified Main
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("MainDatabase.txt"))) {
            // Clears the Main database
            outputStream.reset();
            for (MainAccount MainObject : allMainObjects) {
                if (MainObject.getUsernameID().equals(usernameID)) {
                    // Writes the modified Main
                    outputStream.writeObject(mainAccount);
                } else {
                    // Rewrites existing Main(s)
                    outputStream.writeObject(MainObject);
                }
            }
        } catch(Exception e) {
            e.printStackTrace(); // Better error handling
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html><html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8' />");
        out.println("<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>");
        out.println("<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>");
        out.println("<META HTTP-EQUIV='Expires' CONTENT='0'>");
        out.println("<title>Opening an Account: Success!</title>");
        out.println("<meta http-equiv = 'refresh' content = '3; url = AccountBalances' />");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");

        Logs log = new Logs();
        log.appendToLog(username, "Successfully created a " + accountType + " account with $" + String.format("%.2f", Double.parseDouble(initialAmount)));

        out.println("<h2 class='success'>Successfully created a " + accountType + " account with $" + String.format("%.2f", Double.parseDouble(initialAmount)) + "!</h2>");
        out.println("<h4>Redirecting to Account Summary after 3 seconds...</h4>");
        out.println("</div>");
        out.println(getStyles());
        out.println("</body>");
        out.println("</html>");
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; color: #fff; background-size: cover; }" +
                "header { position: absolute; top: 10px; left: 10px; font-size: 1.2em; color: #fff; }" +
                ".container { text-align: center; background-color: #67C6E3; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin: auto; width: 350px; }" +
                ".success { color: #367E18; }" +
                "button { background-color: #004e8a; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }" +
                "button:hover { background-color: #0056a7; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
