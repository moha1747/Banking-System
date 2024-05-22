import java.io.*;
import java.util.*;

public class MainDatabase implements Serializable {
    private static final long serialVersionUID = -299482035708790407L;

    public MainDatabase() {}

    public List<MainAccount> getAllMainObjects() {
        File                database    = new File("MainDatabase.txt");
        List<MainAccount> accountList = new ArrayList<>();

        // CHECKING if file has already created and user already exist
        try {
            if (!database.createNewFile()) {
                ObjectInputStream db = new ObjectInputStream(new FileInputStream(database));
                while(true){
                    try {
                        accountList.add((MainAccount)db.readObject());
                    } catch (Exception e) {
                        db.close();
                        break;
                    }
                }
            }
        } catch(IOException e) {}
        return accountList;
    }

    public MainAccount getMainObject(String username) {
        List<MainAccount> accountList = this.getAllMainObjects();

        for (MainAccount account : accountList) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public MainAccount validateLogin(String username, String password) {
        List<MainAccount> accountList = getAllMainObjects();
        for (MainAccount account : accountList) {
            if (account.getUsername().equalsIgnoreCase(username) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }

    public MainAccount getMainObjectByUsernameID(String usernameID) {
        List<MainAccount> accountList = this.getAllMainObjects();

        for (MainAccount account : accountList) {
            if (account.getUsernameID().equals(usernameID)) {
                return account;
            }
        }
        return null;
    }

    public String getMainID(String username) {
        MainAccount user = this.getMainObject(username);
        return user.getUsernameID();
    }

    public boolean isMainExist(String username) {
        MainAccount user = this.getMainObject(username);
        return user != null;
    }
}
