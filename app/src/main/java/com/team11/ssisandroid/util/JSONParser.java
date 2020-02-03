package com.team11.ssisandroid.util;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParser {

    private static String getStream(String url) {
        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (sb.toString());
    }

    public static JSONObject getJSONFromUrl(String url) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }

    public static JSONArray getJSONArrayFromUrl(String url) {
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(getStream(url));
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing array " + e.toString());
        }
        return jArray;
    }

    public static String postStream(String url, String data) {
        InputStream inputStream = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setFixedLengthStreamingMode(data.getBytes().length);
            connection.connect();
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(data.getBytes());
            outputStream.flush();
            inputStream = connection.getInputStream();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStream(inputStream);
    }

    private static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (sb.toString());
    }

//    @NonNull
    public static String postStream2(String url, String data){

        try {
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestMethod("POST");

            DataOutputStream localDataOutputStream = new DataOutputStream(con.getOutputStream());
            localDataOutputStream.writeBytes(data);
            localDataOutputStream.flush();
            localDataOutputStream.close();
        }
        catch (Exception e)
        {
            Log.i("SubmitError", e.toString());
            return "Submit failed";

        }
        return "Submitted";
    }

}
