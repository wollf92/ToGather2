package com.example.wollf.togather;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by Mark on 19. 12. 2017.
 */

class Tikkie {

    private String platform_id = "d06da34d-fffb-4b04-a28b-de3a8def4ea6";
    private String api_key = "a6hf4T8ZIylfOOqjQxC14yOB5AvZ15uO";
    private String api_url = "https://api-sandbox.abnamro.com";
    private String access_token = null;

    private Context ctx;

    public Tikkie(Context c) {
        this.ctx = c;
        access_token = this.get_access_token();
    }

    public String get_api_key(){
        return this.api_key;
    }

    private JSONObject create_json(Map<String, Object> data) {
        JSONObject json = new JSONObject();
        if (data.isEmpty())
            return json;
        for (Map.Entry<String, Object> pair : data.entrySet()) {
            try {
                json.put(pair.getKey(), pair.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json;
    }

    private String get_access_token() {

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer")
                .appendQueryParameter("grant_type", "client_credentials")
                .appendQueryParameter("client_assertion", this.get_json_web_token());
        String query = builder.build().getEncodedQuery();

        Requests r = new Requests(this);

        JSONObject res;
        try {
             res = r.execute("https://api-sandbox.abnamro.com/v1/oauth/token",
                                         "POST",
                                         "application/x-www-form-urlencoded",
                                         query,
                                        this.access_token).get();
            return res.getString("access_token");
        } catch (InterruptedException e) {
            Log.d("exception", e.toString());
        } catch (ExecutionException e) {
            Log.d("exception", e.toString());
        } catch (JSONException e) {
            Log.d("exception", e.toString());
        }
        return null;
    }

    private Key get_private_key() {

        try {
            AssetManager assetManager = this.ctx.getAssets();
            InputStream buf = assetManager.open("pkcs8_key");
            byte[] keyBytes = IOUtils.toByteArray(buf);
            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.toString());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        } catch (InvalidKeySpecException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    private String get_json_web_token() {

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        Date oneMinute = new Date(t + 60000);
        Date now = new Date(t - 60000);
        return Jwts.builder()
                .setSubject(this.api_key)
                .setHeaderParam("typ", "JWT")
                .setExpiration(oneMinute)
                .setNotBefore(now)
                .setIssuer("Togather")
                .setAudience("https://auth-sandbox.abnamro.com/oauth/token")
                .signWith(SignatureAlgorithm.RS256, this.get_private_key())
                .compact();
    }

    public JSONObject add_user(String name, String phone_number, String iban){
        Requests r = new Requests(this);

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("phoneNumber", phone_number);
        data.put("iban", iban);
        data.put("bankAccountLabel", "Personal account");
        JSONObject user_json = create_json(data);
        JSONObject res;
        try {
            res = r.execute("https://api-sandbox.abnamro.com/v1/tikkie/platforms/" + platform_id + "/users",
                    "POST",
                    "application/json",
                    user_json.toString(),
                    this.access_token).get();
            return res;
        } catch (InterruptedException e) {
            Log.d("Exception", e.toString());
        } catch (ExecutionException e) {
            Log.d("Exception", e.toString());
        }
        return null;
    }

    public JSONObject get_payment_request(String user_token, String bank_account_token,
                                          int amount /* in cents */){
        Requests r = new Requests(this);

        Map<String, Object> data = new HashMap<>();
        data.put("amountInCents", amount);
        data.put("currency", "EUR");
        data.put("description", "Payment from togather");
        JSONObject payment_request_json = create_json(data);

        JSONObject res;
        try {
            res = r.execute("https://api-sandbox.abnamro.com/v1/tikkie/platforms/" +
                            platform_id + "/users/" + user_token +"/bankaccounts/" +
                            bank_account_token + "/paymentrequests",
                    "POST",
                    "application/json",
                    payment_request_json.toString(),
                    this.access_token).get();
            return res;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Requests extends AsyncTask<String, Void, JSONObject> {
    private Exception mException = null;
    private Tikkie tikkie;

    Requests(Tikkie t){
        this.tikkie = t;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.mException = null;
    }

    @Override
    protected JSONObject doInBackground(String... params)
    {
        // param1 = url
        // param3 = method
        // param2 = type_of_post_data
        // param4 = data
        // param5 = access token
        StringBuilder urlString = new StringBuilder();
        urlString.append(params[0]);

        HttpURLConnection urlConnection = null;
        URL url;
        JSONObject object = null;
        InputStream inStream = null;
        try {
            url = new URL(urlString.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(params[1]);
            if (params[1].toUpperCase().equals("POST"))
                urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", params[2]);
            urlConnection.setRequestProperty("API-Key", this.tikkie.get_api_key());
            if (!params[0].contains("token")) // access_token request
                urlConnection.setRequestProperty("Authorization", "Bearer " + params[4]);

            // adding body to request
            if (params[1].toUpperCase().equals("POST")) {
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(params[3]);
                writer.flush();
                writer.close();
                os.close();
            }
            urlConnection.connect();

            // READING RESPONSE
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK ||
                    responseCode == HttpURLConnection.HTTP_CREATED) {
                inStream = urlConnection.getInputStream();
            } else {
                inStream = urlConnection.getErrorStream();
            }

            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp;StringBuilder response = new StringBuilder();
            while ((temp = bReader.readLine()) != null){
                response.append(temp);
            }

            bReader.close();
            inStream.close();
            urlConnection.disconnect();
            object = (JSONObject) new JSONTokener(response.toString()).nextValue();
            object.put("response_code", responseCode);
        } catch (Exception e) {
            this.mException = e;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException ignored) {
                    Log.d("Exception", ignored.toString());
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return (object);
    }

    @Override
    protected void onPostExecute(JSONObject result)
    {
        super.onPostExecute(result);
        if (this.mException != null)
            Log.d("error", this.mException.toString());
    }
}