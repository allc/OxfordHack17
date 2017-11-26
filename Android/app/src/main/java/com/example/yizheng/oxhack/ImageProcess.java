package com.example.yizheng.oxhack;

import android.graphics.Bitmap;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.util.*;
import org.json.*;

public class ImageProcess {

    private String getImageApiResult(Bitmap img) {
        try {
            String endpoint = "https://westeurope.api.cognitive.microsoft.com/vision/v1.0/analyze";
            String urlParameters = "?visualFeatures=Tags";
            URL endpointURL = new URL(endpoint + urlParameters);
            HttpsURLConnection connection = (HttpsURLConnection) endpointURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/octet-stream");
            connection.setRequestProperty("Ocp-Apim-Subscription-Key", Config.IMAGE_API_KEY);
            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(getBinary(img), 0, getBinary(img).length);
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

    private byte[] getBinary(Bitmap img) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageByte = stream.toByteArray();
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageByte;
    }

    public List<String> getKeyWords(Bitmap img) {
        List<String> keyWords = new LinkedList<>();
        String jsonResult = getImageApiResult(img);
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray tags = jsonObject.getJSONArray("tags");
            for (int i = 0; i < tags.length(); i++) {
                keyWords.add((String)tags.getJSONObject(i).get("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyWords;
    }

    public static void main(String[] args) {
//        ImageProcess ip = new ImageProcess();
//        BufferedImage img = null;
//        try {
//            img = ImageIO.read(new File("test.jpg"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ip.getKeyWords(img);
    }

}
