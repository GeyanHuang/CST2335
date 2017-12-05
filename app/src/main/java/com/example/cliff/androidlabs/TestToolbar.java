package com.example.cliff.androidlabs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private Dialog customDialog;
    private EditText editText_message;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = getSharedPreferences("User info", Context.MODE_PRIVATE);
        message = sharedPref.getString("message", "");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.version_info, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(this.findViewById(R.id.action_one),message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Snackbar.make(this.findViewById(R.id.action_two), R.string.item_2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                android.app.AlertDialog.Builder commentBuilder = new android.app.AlertDialog.Builder(this);
                View commentView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                editText_message = commentView.findViewById(R.id.editText_message);
                editText_message.setText(editText_message.getText());

                commentBuilder.setView(commentView);
                customDialog = commentBuilder.create();
                customDialog.setCanceledOnTouchOutside(false);
                customDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                customDialog.show();
                editText_message.requestFocus();
                break;
            case R.id.action_three:
                Snackbar.make(this.findViewById(R.id.action_three), R.string.item_3, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(TestToolbar.this, ListItemsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                Toast.makeText(this,R.string.version_info, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void message_check(View view) {
        message = editText_message.getText().toString();

        SharedPreferences sharedPref = getSharedPreferences("User info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("message", message);
        editor.apply();

        customDialog.dismiss();
    }
}
