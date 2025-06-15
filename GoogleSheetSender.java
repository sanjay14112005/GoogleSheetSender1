import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GoogleSheetSender {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Event Date (YYYY-MM-DDTHH:MM): ");
        String eventDate = scanner.nextLine();

        System.out.print("Number of Guests: ");
        String guests = scanner.nextLine();

        System.out.print("Event Type: ");
        String eventType = scanner.nextLine();

        System.out.print("Menu Items (comma separated): ");
        String menuItems = scanner.nextLine();

        System.out.print("Special Requirements: ");
        String specialRequirements = scanner.nextLine();

        // Replace with your deployed Apps Script Web App URL
        String scriptUrl = "https://script.google.com/macros/s/AKfycbxkSOskObCTkS-43UmwspWYE0CJHTKKjXDohS3SS8kiu7_DxizTUNzQ7XkOHNYSESIx/exec";

        // Prepare data as URL-encoded string
        String data = String.format(
            "name=%s&email=%s&phone=%s&eventDate=%s&guests=%s&eventType=%s&menuItems=%s&specialRequirements=%s",
            urlEncode(name), urlEncode(email), urlEncode(phone), urlEncode(eventDate),
            urlEncode(guests), urlEncode(eventType), urlEncode(menuItems), urlEncode(specialRequirements)
        );

        try {
             URL url = new URI(scriptUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(data.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String urlEncode(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8");
        } catch (Exception e) {
            return value;
        }
    }
}