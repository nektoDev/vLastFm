package ru.nektodev.vlastfm.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Viacheslav on 27.03.2014.
 */
public class VkService {

    private final String USER_AGENT = "Mozilla/5.0";

    public static final String ACCESS_TOKEN = "b7e28a0bcaee17302bf7237fa8b75579bb5c832984c9d9d572ed867811530e534917cbec7982dcf8b4b0d";

    // HTTP GET request
    private void sendGet() throws Exception {

        String url = "https://api.vk.com/method/audio.search?access_token=" + ACCESS_TOKEN + "&q=Сплин - Романс&auto_complete=1&lyrics=0&performer_only=0&sort=2&search_own=0&offset=0&count=1&v=5.16";

        URL obj = new URL(URLDecoder.decode(url, "ISO-8859-1"));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");
        con.getResponseMessage();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + obj.toString());
        System.out.println("Response Code : " + responseCode);
        System.out.println("\nResponse Message : " + con.getResponseMessage());

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
//TODO Максимальное качество

    public static synchronized String getURL(String query) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            Thread.sleep(350);

            HttpGet getRequest = new HttpGet(
                    "https://api.vk.com/method/audio.search?access_token=" + ACCESS_TOKEN + "&q=" + URLEncoder.encode(query) + "&auto_complete=1&lyrics=0&performer_only=0&sort=2&search_own=0&offset=0&count=1&v=5.16");
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer result = new StringBuffer();
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                result.append(output);
            }
            JSONObject o = new JSONObject(result.toString());

            long count = o.optJSONObject("response").optLong("count");
            if (count != 0) {
                return o.optJSONObject("response").optJSONArray("items").optJSONObject(0).optString("url");
            } else {
                return "";
            }

        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();

        }
        return "";
    }

}
