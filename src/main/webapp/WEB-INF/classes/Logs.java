import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Logs {
    private static final String LOGS_DIR = "logs/";

    public Logs() {
        // Ensure directory exists
        new File(LOGS_DIR).mkdirs();
        ensureDirectoryExists();
    }

    // Method to ensure the logs directory exists
    private void ensureDirectoryExists() {
        File logsDir = new File(LOGS_DIR);
        // Check if the directory exists
        if (!logsDir.exists()) {
            // Try to create the directory
            boolean created = logsDir.mkdir();
            if (created) {
                System.out.println("Logs directory created successfully.");
            } else {
                System.out.println("Failed to create logs directory.");
            }
        }
    }


    public void appendToLog(String username, String contents) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        Date currentDate = new Date();
        String filename = LOGS_DIR + sanitizeUsername(username) + ".log";

        String textToAppend = "<strong>" + formatter.format(currentDate) + "</strong>: " + contents + ".\n";

        try (FileOutputStream outputStream = new FileOutputStream(filename, true)) {
            byte[] strToBytes = textToAppend.getBytes();
            outputStream.write(strToBytes);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }

    private String sanitizeUsername(String username) {
        return username.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
