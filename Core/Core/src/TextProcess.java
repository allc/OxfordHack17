import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import javax.json.*;

public class TextProcess {

    public String getApiResult() {
        try {
            URL endpoint = new URL("https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/keyPhrases");
            HttpsURLConnection connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.TEXT_API_KEY);
            connection.setDoOutput(true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

    }

}
