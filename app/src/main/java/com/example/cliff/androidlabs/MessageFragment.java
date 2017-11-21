package com.example.cliff.androidlabs;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private View view;
    private TextView textView_message, textView_id;
    private Button button_delete, button_cancel;

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
        button_delete = view.findViewById(R.id.button_delete);
        button_cancel = view.findViewById(R.id.button_delete_cancel);

        String message = getArguments().getString("Message");
        final String id = getArguments().getString("ItemId");
        textView_message.setText(message);
        textView_id.setText(id);

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getLocalClassName().equals("MessageDetails")){
                    final Intent resultIntent = new Intent();
                    resultIntent.putExtra("ItemId", id);
                    getActivity().setResult(1,resultIntent);
                    getActivity().finish();
                }else{
                    ((ChatWindow) getActivity()).deleteMessage(id);
                }
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getLocalClassName().equals("MessageDetails")){
                    getActivity().finish();
                }else{
                    ((ChatWindow) getActivity()).closeSideBar();
                }
            }
        });
    }
}
