package com.gosigitgo.getlocationdesember;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import mumayank.com.airlocationlibrary.AirLocation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//1
        Button btnGetLocation = findViewById(R.id.btn_get_location);
        final TextView tvResult = findViewById(R.id.rv_result);
//2
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AirLocation(MainActivity.this, true, true, new AirLocation.Callbacks() {
                    @Override
                    public void onSuccess(Location location) {
                        //3
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        //4
                        Geocoder geocoder = new Geocoder(MainActivity.this);
                        try {
                            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                            String kecamatan = addresses.get(0).getLocality();
                            String provinsi = addresses.get(0).getAdminArea();
                            String subAdmin = addresses.get(0).getSubAdminArea();

                            tvResult.setText("Latitude :"+ lat + "\n" + "Longitude :" +lon+"\n" + "Kecamatan :"+ kecamatan + "\n"+ "Provinsi :"+ provinsi +"\n"+ "Sub Admin :" + subAdmin);
                            //5
                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                            intent.putExtra("LAT", lat);
                            intent.putExtra("LON", lon);
                            startActivity(intent);

                        }catch (IOException e){
                            tvResult.setText(e.getMessage());
                        }

                    }

                    @Override
                    public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {

                    }
                });

            }
        });
    }
}
