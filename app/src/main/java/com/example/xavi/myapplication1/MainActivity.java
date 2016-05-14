package com.example.xavi.myapplication1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    GoogleApiClient mLocationClient;
    TextView txtLong;
    TextView txtLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setUsePassive(true)
                        .setTimeBetweenUpdates( 10 * 1000) // 10 seconds
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
                TextView long_text_view =new TextView(MainActivity.this);
                TextView lat_text_view =new TextView(MainActivity.this);
                TextView speed_text_view =new TextView(MainActivity.this);
                TextView accuracy_text_view =new TextView(MainActivity.this);

                long_text_view=(TextView)findViewById(R.id.txtLong);
                lat_text_view=(TextView)findViewById(R.id.txtLat);
                speed_text_view=(TextView)findViewById(R.id.txtSpeed);
                accuracy_text_view=(TextView)findViewById(R.id.txtAccuracy);

                lat_text_view.setText("Latitude: " + location.getLatitude());
                long_text_view.setText("Longitude: " + location.getLongitude());
                accuracy_text_view.setText("Precision: " + location.getAccuracy());
                speed_text_view.setText("Speed: " + location.getSpeed());

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
