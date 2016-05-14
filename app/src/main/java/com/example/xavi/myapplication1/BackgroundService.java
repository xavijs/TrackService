package com.example.xavi.myapplication1;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

class BackgroundService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundService(String name) {
        super(name);
        System.out.println("Este constructor rarooo XJS");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        System.out.println("Empieza el servicio GUAYYYYY   XJS");

        String dataString = workIntent.getDataString();
        // Do work here, based on the contents of dataString

        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setUsePassive(true)
                        .setTimeBetweenUpdates(10 * 1000)
                        .setMetersBetweenUpdates(1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            System.out.println("PERMISIOONSS CHECCKKK");
            return;
        }
        LocationTracker tracker = new LocationTracker(this, settings) {

            @Override
            public void onLocationFound(Location location) {
                // Do some stuff when a new location has been found.

                System.out.println("Location foouundddd!! toma yaaa");

            }

            @Override
            public void onTimeout() {
                System.out.println("TIMEEOUUTTT JOPETAS");
            }
        };

        tracker.startListening();


    }
}