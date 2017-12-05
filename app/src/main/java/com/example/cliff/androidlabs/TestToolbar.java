package com.example.cliff.androidlabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                Snackbar.make(this.findViewById(R.id.action_one), R.string.item_1, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Snackbar.make(this.findViewById(R.id.action_two), R.string.item_2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());
                builder.setTitle(R.string.dialog_message);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

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
}
