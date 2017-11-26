import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GetWikipedia {

    public String getWikiSummary(String page) {
        try {
            String endpoint = "https://en.wikipedia.org/api/rest_v1/page/summary/" + page;
            String urlParameters = "?redirect=true";
            URL endpointURL = new URL(endpoint + urlParameters);
            HttpsURLConnection connection = (HttpsURLConnection) endpointURL.openConnection();
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Api-User-Agent", "hack_20201@outlook.com");
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

    public static void main(String[] args) {
        GetWikipedia w = new GetWikipedia();
        System.out.println(w.getWikiSummary("nouvelle AI"));
    }

}
