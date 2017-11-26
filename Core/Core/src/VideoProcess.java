import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class VideoProcess {

    public String uploadVideo(String name, File file) {
        try {
            String endpoint = "https://videobreakdown.azure-api.net/Breakdowns/Api/Partner/Breakdowns";
            String urlParameters = "?name=" + name + "&privacy=Private";
            String url = endpoint + urlParameters;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Ocp-Apim-Subscription-Key", "b2f8171d08df4579a486054e3cd9b1d2");

            FileBody uploadFilePart = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("upload-file", uploadFilePart);
            httpPost.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(httpPost);

            return EntityUtils.toString(response.getEntity());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProcessingState(String id) {
        try {
            URL endpoint = new URL("https://videobreakdown.azure-api.net/Breakdowns/Api/Partner/Breakdowns/" + id + "/State");
            HttpsURLConnection connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.VIDEO_API_KEY);
            connection.setDoOutput(true);

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

    public String getBreakDown(String id) {
        try {
            URL endpoint = new URL("https://videobreakdown.azure-api.net/Breakdowns/Api/Partner/Breakdowns/" + id);
            HttpsURLConnection connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "text/json");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.VIDEO_API_KEY);
            connection.setDoOutput(true);

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
        VideoProcess vp = new VideoProcess();
//        File f = null;
//        try {
//            f = new File("test.m4v");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println(vp.getBreakDown("98dcbc00d8"));
    }

}
