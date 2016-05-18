package com.example.xavi.myapplication1;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpPostLocationSender {

    public static final String URL = "http://xavijs.no-ip.org:27960/location";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void storeLocation(String latitude, String longitude) throws IOException, JSONException {

        JSONObject json = new JSONObject();
        json.put("latitude", latitude);
        json.put("longitude", longitude);
        String json_string = json.toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json_string);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

    }


}
