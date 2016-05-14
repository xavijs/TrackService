package com.example.xavi.myapplication1;

/**
 * Created by quentin on 24/01/2016.
 */
public class ProviderError extends Throwable {
    String provider;

    public ProviderError(String provider, String detailMessage) {
        super(detailMessage);
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    @Override
    public String toString() {
        return super.toString() + " | ProviderError{" +
                "provider='" + provider + '\'' +
                '}';
    }
}