package com.example.cliff.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cliff on 2017-10-09.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = ChatDatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "Messages.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Messages";
    public static final String COLUMN_ID = "MessageID";
    public static final String COLUMN_CONTENT = "Message";

    private SQLiteDatabase database;

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CONTENT + " TEXT" +
            ")";

    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(LOGTAG, "Calling onCreate");
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(LOGTAG, "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void openDatabase() {
        database = getWritableDatabase();
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public long insert(String content) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTENT, content);

        return database.insert(TABLE_NAME, null, values);

    }

    public void deleteItem(String id) {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id);
    }

    public Cursor getAllRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
