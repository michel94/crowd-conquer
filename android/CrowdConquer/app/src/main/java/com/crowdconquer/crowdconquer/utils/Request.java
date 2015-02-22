package com.crowdconquer.crowdconquer.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.crowdconquer.crowdconquer.data.StaticData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class Request {
    private String type;
    private String url;
    private Object data;

    HttpClient httpClient;
    InputStream inputStream = null;

    public Request (String type, String url) {
        this.type = type;
        this.url = url;
        this.data = null;
        httpClient = new DefaultHttpClient();
    }

    public Request(String type, String url, Object data) {
        this.type = type;
        this.url = url;
        this.data = data;
        httpClient = new DefaultHttpClient();
    }

    public String execute() {
        try {
            return new HttpAsyncTask().execute().get();
        } catch (InterruptedException | ExecutionException e) { }
        return "Exception";
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String ... strings) {
            try {
                return executeRequest();
            } catch (IOException e) {
                return "error";
            }
        }
        @Override
        protected void onPostExecute(String result) {  }
    }

    public String executeRequest() throws IOException {
        String result = "";

        if (type.equals("post")) {
            try {
                Log.v("post", StaticData.SERVER_1 + url);
                HttpPost httpPost = new HttpPost(StaticData.SERVER_1 + url);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setEntity(processData());

                HttpResponse httpResponse = httpClient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null) {
                    result = inputStreamToString(inputStream);
                    String [] resultsParted = result.split(" ");
                    StaticData.user.setTerritoryOwner(resultsParted[0]);
                    StaticData.user.setTimeToWin(resultsParted[1]);

                }else result = "error";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("get")) {
            Log.v("get", StaticData.SERVER_1 + url);
            HttpResponse httpResponse = httpClient.execute(new HttpGet(StaticData.SERVER_1 + url));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = inputStreamToString(inputStream);
            else result = "error";
        }
        return result;
    }

    private synchronized StringEntity processData() {
        StringEntity se = null;
        try {
            JSONObject argsObject = new JSONObject();
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", StaticData.user.getEmail());
            //TODO...
            jsonObject.accumulate("lat", StaticData.user.getLocation().getLatitude());
            jsonObject.accumulate("lon", StaticData.user.getLocation().getLongitude());
            argsObject.accumulate("args", jsonObject);
            String json = argsObject.toString();
            se = new StringEntity(json, "UTF-8");
        } catch (JSONException | UnsupportedEncodingException ignored) { }
        return se;
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }


    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Object getData() {
        return data;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setData(Object data) {
        this.data = data;
    }
}