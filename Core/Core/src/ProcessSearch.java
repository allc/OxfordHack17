import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Allen on 2017/11/26.
 */

public class ProcessSearch {

    public String getSearchApiResult(String keyWord) {
        try {
            String endpoint = "https://api.cognitive.microsoft.com/bing/v7.0/search";
            String urlParameters = "?q=" + URLEncoder.encode(keyWord, "UTF-8");
            URL endpointURL = new URL(endpoint + urlParameters);
            HttpsURLConnection connection = (HttpsURLConnection) endpointURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.WEB_SEARCH_API_KEY);
            connection.setDoOutput(true);

//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//            wr.write(getBinary(img), 0, getBinary(img).length);
//            wr.flush();
//            wr.close();

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

    public String getUrl(String keyWord) {
        String resultJson = getSearchApiResult(keyWord);
        JSONObject jsonObject = new JSONObject(resultJson);
        return jsonObject.getJSONObject("webPages").getJSONArray("value").getJSONObject(0).get("url").toString();
    }

    public static void main(String[] args) {
        System.out.println(new ProcessSearch().getUrl("nouvelle AI"));
    }

}
