package com.example.xavi.myapplication1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.xavi.myapplication1.conf.HttpPostLocationSenderConfiguration;

import org.json.JSONException;

import java.io.IOException;


public class BackgroundService extends Service
{
    private static final String TAG = "XJS";
    private String uuid;
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 1f;


    HttpPostLocationSender sender;

    public BackgroundService() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sender = new HttpPostLocationSender(new HttpPostLocationSenderConfiguration());

    }

    private class LocationListener implements android.location.LocationListener{
        Location mLastLocation;


        public LocationListener(String provider)
        {
            mLastLocation = new Location(provider);

        }
        @Override
        public void onLocationChanged(Location location)
        {
            Log.i(TAG, "onLocationChanged: " + location.toString());

            try {
                sender.storeLocation(location, uuid);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            mLastLocation.set(location);
            Toast.makeText(getApplicationContext(), location.toString(), Toast.LENGTH_LONG).show();
        }
        @Override
        public void onProviderDisabled(String provider)
        {
            Log.i(TAG, "onProviderDisabled: " + provider);
        }
        @Override
        public void onProviderEnabled(String provider)
        {
            Log.i(TAG, "onProviderEnabled: " + provider);
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.i(TAG, "onStatusChanged: " + provider);
        }
    }
    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER),
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    @Override
    public void onCreate()
    {
        Log.i(TAG, "onCreate");
        initializeLocationManager();

        try {
            getGPSlocation();
            getNetworkLocation();
            getPassiveLocation();
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "provider does not exist " + ex.getMessage());
        }
    }
    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.e(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }
    private void initializeLocationManager() {
        Log.i(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            uuid = tManager.getDeviceId();
        }
    }

    private void getGPSlocation() {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[0]);
    }

    private void getNetworkLocation(){
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[1]);
    }

    private void getPassiveLocation() {
        mLocationManager.requestLocationUpdates(
                LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                mLocationListeners[2]);
    }
}