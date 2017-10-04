package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_Tag;


/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
class QueryUtils {
     private static final String L0G_TAG = QueryUtils.class.getName();
    /** Sample JSON response for a USGS query */
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_Tag, "error closing input stream", e);
        }
        List<Earthquake> earthquakeList = extractEarthquakesFromJSON(jsonResponse);
        return earthquakeList;
    }

    /**
     *  Helper method to create {@link}URL object
     * @param StringUrl takes a String url
     * @return URL object
     */
    private static URL createUrl(String StringUrl) {
        URL Url = null;
        try {
            Url = new URL(StringUrl);
        }catch(MalformedURLException e) {
            Log.e(LOG_Tag, "Error with creating Url", e);
            return null;
        }
        return Url;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url)throws IOException{
        String JsonResponse = " ";
        // If url is null return early.
        if (url == null) {
            return JsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);
            }else {
                Log.wtf(LOG_Tag, "Http error code:" + urlConnection.getResponseCode());
                return  null;
            }

        }catch (IOException e){
            Log.e(LOG_Tag, "Error reading the input stream", e);

        } finally{
              if(urlConnection != null){
                  urlConnection.disconnect();
              }
              if(inputStream != null) {
                  inputStream.close();
              }
        }
        return JsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the se r ver.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }



    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Earthquake> extractEarthquakesFromJSON(final String JSON_Response) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(JSON_Response);
            JSONArray array = root.getJSONArray("features");
            for(int i = 0;i < array.length();i++){
                JSONObject element = array.getJSONObject(i);
                JSONObject properties = element.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new Earthquake(mag, location, time, url) );
            }
            return earthquakes;
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return null;
    }

}


