package com.example.xavi.myapplication1;

import android.location.Location;

import com.example.xavi.myapplication1.conf.HttpPostLocationSenderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpPostLocationSender {

    HttpPostLocationSenderConfiguration httpPostLocationSenderConfiguration;

    public HttpPostLocationSender(HttpPostLocationSenderConfiguration configuration) {
        this.httpPostLocationSenderConfiguration = configuration;
    }

    public void storeLocation(Location location) throws IOException, JSONException {


        JSONObject json = new JSONObject();

        json.put("latitude", location.getLatitude());
        json.put("longitude", location.getLongitude());
        json.put("speed", location.getSpeed());
        json.put("accuracy", location.getAccuracy());
        json.put("altitude", location.getAltitude());
        json.put("timestamp", location.getTime());
        json.put("provider", location.getProvider());

        String json_string = json.toString();

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(httpPostLocationSenderConfiguration.getMediaType() , json_string);
        Request request = new Request.Builder()
                .url(httpPostLocationSenderConfiguration.getURL())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

    }


}
