package com.example.cliff.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ChatDatabaseHelper databaseHelper;
    private String TAG = ChatWindow.class.getSimpleName();

    private Button button_send;
    private EditText edit_message;
    private ListView list_chat;
    private ArrayList<String> messages;
    private ChatAdapter messageAdapter;

    class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context context) {
            super(context, 0);
        }

        public String getItem(int position) {
            return messages.get(position);
        }

        @Override
        public int getCount() {

            int count = messages.size();
            return count;
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

            message.setText(getItem(position));

            return result;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        init();

        list_chat.setAdapter(messageAdapter);
        registerForContextMenu(list_chat);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit_message.getText().toString().equals("")) {
                    databaseHelper.insert(edit_message.getText().toString());
                    showHistory();
                    edit_message.setText("");
                }
            }
        });

        button_send.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(messages.size()!=0){
                    Toast.makeText(ChatWindow.this,R.string.delete_last_message,Toast.LENGTH_SHORT).show();
                }

                databaseHelper.deleteAll();
                showHistory();
                return true;
            }
        });
    }

    public void init() {
        //init
        button_send = findViewById(R.id.button_send);
        edit_message = findViewById(R.id.edit_message);
        list_chat = findViewById(R.id.list_chat);
        databaseHelper = new ChatDatabaseHelper(this);
        messages = new ArrayList<>();
        messageAdapter = new ChatAdapter(this);
    }

    public void showHistory() {
        messages.clear();
        Cursor cursor = databaseHelper.getAllRecords();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            messages.add(cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_CONTENT)));
            Log.i(TAG, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.COLUMN_CONTENT) ) );
        }
        messageAdapter.notifyDataSetChanged();
        scrollMyListViewToBottom();
        Log.i(TAG, "Cursor’s  column count = " + cursor.getColumnCount() );
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(TAG, "Cursor’s  column name = " + (i + 1) + ". " + cursor.getColumnName(i) );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseHelper.openDatabase();
        showHistory();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.closeDatabase();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void scrollMyListViewToBottom() {
        list_chat.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                list_chat.setSelection(list_chat.getCount() - 1);
            }
        });
    }
}
