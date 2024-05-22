import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CreateMainAccount extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String initialAmount = request.getParameter("initial-deposit");
        String accountType = request.getParameter("account-type");

        MainDatabase mainDatabase = new MainDatabase();
        boolean isMainExist = mainDatabase.isMainExist(username);

        if (!isMainExist) {
            MainAccount newMainAccount = new MainAccount(username, password);
            UserAccount newChildAccount = new UserAccount(newMainAccount.getUsernameID(), accountType, initialAmount);
            newMainAccount.addBalance(newChildAccount);

            // Save the new account to the database
            File databaseFile = new File("MainDatabase.txt");
            try (ObjectOutputStream outputStream = databaseFile.length() == 0 ?
                    new ObjectOutputStream(new FileOutputStream(databaseFile)) :
                    new AppendingObjectOutputStream(new FileOutputStream(databaseFile, true))) {
                outputStream.writeObject(newMainAccount);
            }

            session.setAttribute("username", username);
            session.setAttribute("usernameID", newMainAccount.getUsernameID());

            out.println("<!DOCTYPE html><html lang='en'><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>");
            out.println("<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>");
            out.println("<META HTTP-EQUIV='Expires' CONTENT='0'>");
            out.println("<title>Sign Up: Success</title>");
            out.println(getStyles());
            out.println("</head><body>");
            out.println("<div class='container'>");
            out.println("<h2 class='success'>Successfully created " + username + "!</h2>");
            out.println("<h4>Redirecting to Account Summary</h4>");
            out.println("<meta http-equiv='refresh' content='3; url=AccountBalances'/>");
            out.println("</div></body></html>");
        } else {
            out.println("<!DOCTYPE html><html lang='en'><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>");
            out.println("<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>");
            out.println("<META HTTP-EQUIV='Expires' CONTENT='0'>");
            out.println("<title>Sign Up: Error</title>");
            out.println(getStyles());
            out.println("</head><body>");
            out.println("<div class='container'>");
            out.println("<h3 class='error'>Unable to create account: Username already exists.</h3>");
            out.println("<h4><a href='signup.htm'>Click here to go back to Sign Up page</a></h4>");
            out.println("</div></body></html>");
        }
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin: auto; width: 50%; }" +
                ".error { color: #FF1E00; }" +
                ".success { color: #367E18; }" +
                "a { color: #ffffff; text-decoration: none; }" +
                "a:hover { text-decoration: underline; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
