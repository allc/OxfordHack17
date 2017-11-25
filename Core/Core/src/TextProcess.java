import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import javax.json.*;
import com.google.gson.*;

public class TextProcess {

    public String getTextApiResult(Documents documents) {
        try {
            String text = new Gson().toJson(documents);
            System.out.println(text);
            byte[] encoded_text = text.getBytes("UTF-8");

            URL endpoint = new URL("https://westeurope.api.cognitive.microsoft.com/text/analytics/v2.0/keyPhrases");
            HttpsURLConnection connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.TEXT_API_KEY);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(encoded_text, 0, encoded_text.length);
            wr.flush();
            wr.close();

            StringBuilder response = new StringBuilder ();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            return response.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        TextProcess tp = new TextProcess();
        Documents documents = new Documents ();
        documents.add ("1", "en", "I really enjoy the new XBox One S. It has a clean look, it has 4K/HDR resolution and it is affordable.");
        documents.add ("2", "es", "Si usted quiere comunicarse con Carlos, usted debe de llamarlo a su telefono movil. Carlos es muy responsable, pero necesita recibir una notificacion si hay algun problema.");
        documents.add ("3", "en", "The Grand Hotel is a new hotel in the center of Seattle. It earned 5 stars in my review, and has the classiest decor I've ever seen.");
        String response = tp.getTextApiResult(documents);
        System.out.println(response);
    }

}
