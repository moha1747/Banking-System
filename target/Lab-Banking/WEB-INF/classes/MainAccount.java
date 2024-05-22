import java.io.*;
import java.util.*;

public class MainAccount implements Serializable {
    private static final long serialVersionUID = -299482035708790407L;
    private String username;
    private String password; // Added password field
    private String usernameID;
    private List<UserAccount> BalanceList = new ArrayList<>();

    public MainAccount(String username, String password) { // Updated constructor
        this.username = username;
        this.password = password; // Initialize password
        this.usernameID = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password; // Getter for password
    }

    public String getUsernameID() {
        return usernameID;
    }

    public List<UserAccount> getBalances() {
        return BalanceList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) { // Setter for password
        this.password = password;
    }

    public void setUsernameID(String usernameID) {
        this.usernameID = usernameID;
    }

    public void addBalance(UserAccount newAccount) {
        BalanceList.add(newAccount);
    }

    public int removeBalanceByAccountID(String accountID) {
        for (UserAccount account : BalanceList) {
            if (account.getAccountID().equals(accountID)) {
                BalanceList.remove(account);
                return 0;
            }
        }
        return -1;
    }
}
