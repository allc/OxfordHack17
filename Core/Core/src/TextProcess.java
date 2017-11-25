import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;

import com.google.gson.*;

public class TextProcess {

    /**
     * Gets json result from API
     * @param documents
     * @return json result from API
     */
    private String getTextApiResult(TextDocuments documents) {
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

    public List<String> getKeyPhrases(String text, String language) {
        // get
        TextDocuments documents = new TextDocuments();
        documents.add ("1", language, text);
        String jsonResult = getTextApiResult(documents);
        System.out.println(jsonResult);

        // parse
        TextResultDocuments resultDocuments = new Gson().fromJson(jsonResult, TextResultDocuments.class);
        List<String> result = new ArrayList<>();
        for (TextResultDocument resultDocument : resultDocuments.documents) {
            for (String keyParse : resultDocument.keyPhrases) {
                result.add(keyParse);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TextProcess tp = new TextProcess();
        tp.getKeyPhrases("But there is an alternative view, or dogma, variously called nouvelle AI, fundamentalist AI, or in a weaker form situated activity", "en");
    }

}
