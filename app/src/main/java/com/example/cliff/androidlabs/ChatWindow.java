package com.example.cliff.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private Button button_send;
    private EditText edit_message;
    private ListView list_chat;
    private ArrayList<String> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);


        button_send = findViewById(R.id.button_send);
        edit_message = findViewById(R.id.edit_message);
        list_chat = findViewById(R.id.list_chat);
        messages = new ArrayList<>();

        class ChatAdapter extends ArrayAdapter<String> {

            public ChatAdapter(Context context) {
                super(context, 0);
            }

            @Override
            public int getCount() {

                int count = messages.size();
                return count;
            }

            @Override
            public	String getItem(int position) {
                return messages.get(position);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
                View result;
                if (position % 2 == 0)
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                else
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message = result.findViewById(R.id.message_text);
                message.setText( getItem(position) );


                return result;
            }
        }


        final ChatAdapter messageAdapter = new ChatAdapter(this);
        list_chat.setAdapter(messageAdapter);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messages.add(edit_message.getText().toString());
                messageAdapter.notifyDataSetChanged();
                edit_message.setText("");
            }
        });
    }
}
