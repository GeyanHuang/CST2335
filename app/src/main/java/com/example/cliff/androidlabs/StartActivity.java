package com.example.cliff.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {

    private static final String TAG = StartActivity.class.getSimpleName();
    private TextView welcome;
    private Button button, button_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(TAG, "In onCreate()");


        welcome = findViewById(R.id.welcome);
        button = findViewById(R.id.button);
        button_chat = findViewById(R.id.button_chat);
        SharedPreferences sharedPref = getSharedPreferences("User info", Context.MODE_PRIVATE);

        String email = sharedPref.getString("email", "");
        welcome.setText(email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

        button_chat.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                StartActivity.this.deleteDatabase(ChatDatabaseHelper.DATABASE_NAME);
                Toast.makeText(StartActivity.this, R.string.delete_history, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "In onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                Log.i(TAG, "Returned to StartActivity.onActivityResult");
                break;
        }
        switch (resultCode) {
            case Activity.RESULT_OK:
                String messagePassed = data.getStringExtra("Response");
                Toast.makeText(this, messagePassed, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void weather_forecast(View view) {
        Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
        startActivity(intent);
    }
}
