package com.team11.ssisandroid.util;

import com.team11.ssisandroid.models.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginParser {

    public static AccessToken postLoginStream(String email, String password, String urlString) {

        URL url;
        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        AccessToken token = null;

        try {
            url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");

            data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                    + URLEncoder.encode(password, "UTF-8");

            data += "&" + URLEncoder.encode("grant_type", "UTF-8") + "="
                    + URLEncoder.encode("password", "UTF-8");

            urlConnection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(data);
            wr.flush();

            stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }

            JSONObject result = new JSONObject(sb.toString());

            token = new AccessToken(
                    result.getString("access_token"),
                    result.getString("token_type"),
                    result.getInt("expires_in")
            );

        } catch (IOException | JSONException e) {
            try {
                if (stream == null)
                    return null;

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }

                JSONObject result = new JSONObject(sb.toString());

                token = new AccessToken(
                        result.getString("error"),
                        result.getString("error_description")
                );
            } catch (IOException | JSONException e1) {
                e1.printStackTrace();
                return null;
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return token;

    }
}
