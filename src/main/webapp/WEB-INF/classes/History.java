import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class History extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        String username = (String) session.getAttribute("username");

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        out.println("<!DOCTYPE html><html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<META HTTP-EQUIV='Cache-Control' CONTENT='no-cache'>");
        out.println("<META HTTP-EQUIV='Pragma' CONTENT='no-cache'>");
        out.println("<META HTTP-EQUIV='Expires' CONTENT='0'>");
        out.println("<title>" + username + "'s History</title>");
        out.println(getStyles());
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("<h1>" + username + "'s Account History</h1>");

        File databaseFile = new File("logs/" + username + ".log");
        if (!databaseFile.exists() || databaseFile.length() == 0) {
            out.println("<h3 class='error'>No history found for " + username + ". Make some transactions/actions.</h3>");
        } else {
            out.println("<table>");
            out.println("<tr><th>Date & Time</th><th>Activity</th></tr>");
            try (Scanner myReader = new Scanner(databaseFile)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    out.println("<tr><td>" + data.substring(data.indexOf("<strong>") + 8, data.indexOf("</strong>")) + "</td><td>" + data.substring(data.indexOf("</strong>") + 10) + "</td></tr>");
                }
            } catch (FileNotFoundException e) {
                out.println("<h3 class='error'>An error occurred. Please try again later.</h3>");
                out.println("<a href='AccountBalances'><button>Return to Account Summary</button></a>");

            }
            out.println("</table>");
        }
        out.println("<a href='AccountBalances'><button>Return to Account Summary</button></a>");
        out.println("</div>"); // Close container
        out.println("</body>");
        out.println("</html>");
    }

    private String getStyles() {
        return "<style>" +
                "body, html { height: 100%; margin: 0; font-family: Arial, sans-serif; background: url('https://static.vecteezy.com/system/resources/previews/010/518/840/original/digital-finance-and-banking-service-in-futuristic-background-bank-building-with-online-payment-transaction-secure-money-and-financial-innovation-technology-vector.jpg') no-repeat center center fixed; background-size: cover; color: #ffffff; }" +
                "header { position: absolute; top: 10px; left: 10px; font-size: 1.5em; color: #fff; }" +
                "header h2 { display: flex; align-items: center; }" +
                "header i { margin-right: 10px; }" +
                ".container { text-align: center; padding: 20px; border-radius: 10px; background-color: rgba(103, 198, 227, 0.9); box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin: auto; width: 50%; }" +
                ".error { color: #FF1E00; }" +
                ".success { color: #367E18; }" +
                "button { background-color: #378CE7; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; }" +
                "button:hover { background-color: #67a9ea; }" +
                "table { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
                "th, td { border: 1px solid #ffffff; padding: 8px; text-align: left; }" +
                "th { background-color: #378CE7; }" +
                "</style>";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
}
