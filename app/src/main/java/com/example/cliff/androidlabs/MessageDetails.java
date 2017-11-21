package com.example.cliff.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putString("Message", "abc");

        Fragment fragment = new MessageFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.phone_frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
