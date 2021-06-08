package com.bpal.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String APPID = "17a737f9050113b36749fbd48a140635";

    TextView condition, city, temperature, humidity, pressure, day1, day2, day3;
    ImageView icon;

    LocationManager locationManager;
    LocationListener locationListener;

    double latitude = 0, longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        condition = findViewById(R.id.status);
        city = findViewById(R.id.location);
        temperature = findViewById(R.id.temperature);
        icon = findViewById(R.id.image);
        humidity = findViewById(R.id.humidity);
        pressure = findViewById(R.id.pressure);

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);

        day1.setText(getDay("EEEE", +1));
        day2.setText(getDay("EEEE", +2));
        day3.setText(getDay("EEEE", +3));

        getCurrentLocation();

    }

    private void getCurrentLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPS = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPS) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    RequestParams params = new RequestParams();
                    params.put("lat", latitude);
                    params.put("lon", longitude);
                    params.put("appid", APPID);
                    getWeather(params);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

        }
         if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 10, locationListener);
    }

    private void getWeather(RequestParams params) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                WeatherData weatherData = WeatherData.fromJson(response);

                temperature.setText(weatherData.getTemperature());
                humidity.setText("H "+weatherData.getHumidity());
                pressure.setText("P "+weatherData.getPressure());
                city.setText(weatherData.getCity());
                int id = getResources().getIdentifier(weatherData.getIcon(), "drawable", getPackageName());
                icon.setImageResource(id);
                condition.setText(weatherData.getCondition());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        } );

    }

    private static String getDay(String format, int days) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return dateFormat.format(new Date(calendar.getTimeInMillis()));
    }

}