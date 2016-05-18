package com.example.xavi.myapplication1.conf;

import okhttp3.MediaType;

public final class HttpPostLocationSenderConfiguration {

    private String URL = "http://endpoint_that_accepts_post_with_location_params";
    private MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public String getURL() {
        return URL;
    }

    public MediaType getMediaType() {
        return MEDIA_TYPE;
    }
}
