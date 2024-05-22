import java.util.List;

public class Main {
    public static void main(String[] args) {
        MainDatabase database = new MainDatabase();

        // Import and display all users
        importAndDisplayAllUsers(database);

        // Test: Fetch a specific main object by username
        String testUsername = "123";
        MainAccount testAccount = database.getMainObject(testUsername);
        if (testAccount != null) {
            System.out.println("Account found for username '" + testUsername + "': " + testAccount.getUsernameID());
        } else {
            System.out.println("No account found for username '" + testUsername + "'");
        }

        // Test: Check if a main object exists by username
        boolean exists = database.isMainExist(testUsername);
        System.out.println("Does an account exist for '" + testUsername + "': " + exists);
    }

    private static void importAndDisplayAllUsers(MainDatabase database) {
        List<MainAccount> accounts = database.getAllMainObjects();
        System.out.println("All accounts:");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found in the database.");
        } else {
            for (MainAccount account : accounts) {
                System.out.println("Username: " + account.getUsername() + ", UsernameID: " + account.getUsernameID());
            }
        }
    }
}