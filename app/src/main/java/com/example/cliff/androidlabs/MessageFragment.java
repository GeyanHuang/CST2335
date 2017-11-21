package com.example.cliff.androidlabs;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private View view;
    private TextView textView_message, textView_id;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        textView_message = view.findViewById(R.id.textview_selected_message);
        textView_id = view.findViewById(R.id.textview_message_id);

        String message = getArguments().getString("Message");
        textView_message.setText(message);
    }
}
