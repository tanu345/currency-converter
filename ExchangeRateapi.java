package package1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class ExchangeRateApi {

    private final String apiKey;
    private final String endpoint;
    private HashMap<String, Double> exchangeRates;
    private String lastUpdateTime;

    public ExchangeRateApi(String apiKey) {
        this.apiKey = apiKey;
        this.endpoint = "https://v6.exchangerate-api.com/v6/"+apiKey+"/latest/USD";
        this.exchangeRates = new HashMap<>();
        this.lastUpdateTime = "";
        loadExchangeRates();
    }

    public String getdate() {
        return lastUpdateTime;
    }

    private void loadExchangeRates() {
        try {
            // Create URL object
            URL url = new URI(endpoint).toURL();

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Read response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("time_last_update_utc")) {
                    lastUpdateTime = line.substring(25, line.length() - 7) + " UTC";
                } else if (line.contains("conversion_rates")) {
                    while ((line = reader.readLine()) != null) {
                        if (line.length() < 3) break;
                        String currency = line.substring(3, 6);
                        double rate = Double.parseDouble(line.substring(8, line.length() - 1));
                        exchangeRates.put(currency, rate);
                    }
                }
            }

            // Close resources
            reader.close();
            connection.disconnect();

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Double> getExchangeRates() {
        return exchangeRates;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }
}
