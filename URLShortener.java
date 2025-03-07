import java.util.*;

public class URLShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final int SHORT_URL_LENGTH = 6;
    private Map<String, String> urlMap; // Stores short -> long URL
    private Map<String, String> customMap; // Stores long -> short URL
    private Random random;

    public URLShortener() {
        urlMap = new HashMap<>();
        customMap = new HashMap<>();
        random = new Random();
    }

    // Generates a random short URL code
    private String generateShortURL() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortURL = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            shortURL.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortURL.toString();
    }

    // Method to shorten a URL
    public String shortenURL(String longURL, String customCode) {
        if (!isValidURL(longURL)) {
            return "Invalid URL format!";
        }

        // Check if the URL was already shortened
        if (customMap.containsKey(longURL)) {
            return "Already shortened: " + BASE_URL + customMap.get(longURL);
        }

        String shortCode = (customCode != null && !customCode.isEmpty()) ? customCode : generateShortURL();

        // Check if custom short code already exists
        if (urlMap.containsKey(shortCode)) {
            return "Custom short URL is already taken. Try another!";
        }

        urlMap.put(shortCode, longURL);
        customMap.put(longURL, shortCode);

        return "Shortened URL: " + BASE_URL + shortCode;
    }

    // Method to get the original URL
    public String getOriginalURL(String shortURL) {
        String shortCode = shortURL.replace(BASE_URL, "");
        return urlMap.getOrDefault(shortCode, "URL not found!");
    }

    // URL validation method
    private boolean isValidURL(String url) {
        return url.matches("^(https?://)([\\w.-]+)+(:\\d+)?(/[\\w./?%&=-]*)?$");
    }

    // Main method for user interaction
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        URLShortener urlShortener = new URLShortener();

        while (true) {
            System.out.println("\n1. Shorten URL");
            System.out.println("2. Retrieve Original URL");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longURL = scanner.nextLine();
                    System.out.print("Enter a custom short URL (or press Enter for random): ");
                    String customCode = scanner.nextLine();
                    System.out.println(urlShortener.shortenURL(longURL, customCode));
                    break;
                case 2:
                    System.out.print("Enter the short URL: ");
                    String shortURL = scanner.nextLine();
                    System.out.println("Original URL: " + urlShortener.getOriginalURL(shortURL));
                    break;
                case 3:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
