package com.example.cliff.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    protected static final String TAG = WeatherForecast.class.getSimpleName();
    private ProgressBar progressBar;
    private LinearLayout progressLayout;
    private TextView currentTemp, minTemp, maxTemp;
    private ImageView weatherImage;
    private String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressLayout = findViewById(R.id.progressbar_layout);

        progressLayout.setVisibility(View.VISIBLE);

        progressBar = findViewById(R.id.progressbar);
        currentTemp = findViewById(R.id.current_temperature);
        minTemp = findViewById(R.id.min_temperature);
        maxTemp = findViewById(R.id.max_temperature);
        weatherImage = findViewById(R.id.weather_image);
        progressBar.setScaleY(3f);

        ForecastQuery forecast = new ForecastQuery();

        forecast.execute(urlString);

        Log.i(TAG, "In onCreate()");
    }

    protected static Bitmap getImage(URL url) {
        Log.i(TAG, "In getImage");
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            int response = httpURLConnection.getResponseCode();
            if (response == 200) {
                return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    public boolean fileExistance(String fileName) {
        Log.i(TAG, "In fileExistance");
        File file = getBaseContext().getFileStreamPath(fileName);
        Log.i(TAG, file.toString());
        return file.exists();
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        String min;
        String max;
        String current;
        String iconName;
        Bitmap icon;

        @Override
        protected String doInBackground(String... args) {
            Log.i(TAG, "In doInBackground");
            try {
                URL url = new URL(args[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    if (parser.getName().equals("temperature")) {
                        current = parser.getAttributeValue(null, "value");
                        publishProgress(25);
                        android.os.SystemClock.sleep(250);
                        min = parser.getAttributeValue(null, "min");
                        android.os.SystemClock.sleep(250);
                        publishProgress(50);
                        max = parser.getAttributeValue(null, "max");
                        android.os.SystemClock.sleep(250);
                        publishProgress(75);
                        android.os.SystemClock.sleep(250);
                    }
                    if (parser.getName().equals("weather")) {
                        iconName = parser.getAttributeValue(null, "icon");
                        String iconFile = iconName + ".png";
                        if (fileExistance(iconFile)) {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            icon = BitmapFactory.decodeStream(inputStream);
                            Log.i(TAG, "Image already exists");
                        } else {
                            URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                            icon = getImage(iconUrl);
                            FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                            icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Log.i(TAG, "Adding new image");
                        }
                        Log.i(TAG, "File name= " + iconFile);
                        publishProgress(100);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.i(TAG, "In onProgressUpdate");
            progressBar.setProgress(value[0]);
            progressLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            String degree = Character.toString((char) 0x00B0);
            currentTemp.setText(current + " " + degree + "C");
            minTemp.setText(min + " " + degree + "C");
            maxTemp.setText(max + " " + degree + "C");
            weatherImage.setImageBitmap(icon);
            progressLayout.setVisibility(View.INVISIBLE);
        }
    }
}
