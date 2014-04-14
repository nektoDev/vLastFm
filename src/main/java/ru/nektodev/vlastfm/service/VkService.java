package ru.nektodev.vlastfm.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Service to work with VK.com API
 *
 * @author nektodev
 */
public class VkService {

    //TODO remove access token
    public static final String ACCESS_TOKEN = "a10ae9759ff882ec6df6fe45bb1f65dbfdcbd840a6298b7aa88dab700e610edbbcc7f9484fa13a33379f7";

    public static Long lastCallTime = 0L;

    /**
     * Method to find download URL on VK.com
     *
     * @param query query to search track. Typically looks like Artist - Track
     *
     * @return url string
     */
    //TODO make more readable. Split it!
    public static synchronized String getURL(String query) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {

            long timeLast = System.currentTimeMillis() - lastCallTime;
            if (timeLast < 350) {
                Thread.sleep(350 - timeLast);
            }

            //TODO make more readable and add more params
            HttpGet getRequest = new HttpGet(
                    "https://api.vk.com/method/audio.search?access_token=" + ACCESS_TOKEN +
                            "&q=" + URLEncoder.encode(query) +
                            "&auto_complete=1&lyrics=0&performer_only=0&sort=2&search_own=0&offset=0&count=10&v=5.16");
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            lastCallTime = System.currentTimeMillis();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            StringBuffer result = new StringBuffer();

            while ((output = br.readLine()) != null) {
                result.append(output);
            }
            JSONObject o = new JSONObject(result.toString());

            //TODO add another response types
            long count = o.optJSONObject("response").optLong("count");
            if (count != 0) {

                JSONArray responseItems = o.optJSONObject("response").optJSONArray("items");
                URL maxURL = getUrlWithMaxSize(responseItems);
                return maxURL.toString();
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

    private static URL getUrlWithMaxSize(JSONArray responseItems) throws IOException {
        long maxBpm = 0;
        URL maxURL = null;
        for (int i = 0; i<responseItems.length() && maxBpm < 300; i++) {
            JSONObject item = responseItems.getJSONObject(i);
            URL tryURL = new URL(item.optString("url"));
            long bpm = tryURL.openConnection().getContentLengthLong()/item.optLong("duration")*8/1000;

            if (bpm > maxBpm) {
                maxBpm = bpm;
                maxURL = tryURL;
            }

        }
        return maxURL;
    }


}
