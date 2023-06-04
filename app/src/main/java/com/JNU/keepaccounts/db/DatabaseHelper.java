package com.JNU.keepaccounts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.JNU.keepaccounts.utils.globle.GlobalConstant;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context myContext;
    public static DatabaseHelper JNUDatabaseHelper = null;

    public static DatabaseHelper getDatabaseHelper(Context context){
        if(JNUDatabaseHelper == null){
            JNUDatabaseHelper = new DatabaseHelper(context, GlobalConstant.DATABASE_NAME, null, 1);
        }
        return JNUDatabaseHelper;
    }


    private static final String CREAT_TABLE_TAG = "create table t_tag(\n" +
            "    tid integer primary key autoincrement,\n" +
            "    tag_name text not null unique,\n" +
            "    tag_img_name text not null\n" +
            ")";
    private static final String CREATE_TABLE_ACCOUNT_BOOK = "create table t_account_book(\n" +
            "    bid integer primary key autoincrement,\n" +
            "    account_book_name text not null unique\n" +
            ")";
    private static final String CREAT_TABLE_EVENT_ITEM= "create table t_event_item(\n" +
            "    eid integer primary key autoincrement,\n" +
            "    event_title text not null,\n" +
            "    event_content text,\n" +
            "    event_time integer not null,\n" +
            "    bid integer not null\n" +
            ")";
    private static final String CREAT_TABLE_ACCOUNT_ITEM = "create table t_account_item(\n" +
            "    aid integer primary key autoincrement,\n" +
            "    income_or_expenditure text not null,\n" +
            "    tid integer not null,\n" +
            "    sum real not null,\n" +
            "    remark text not null,\n" +
            "    account_time integer not null,\n" +
            "    if_borrow_or_lend text not null,\n" +
            "    bid integer not null,\n" +
            "    eid integer not null\n" +
            ")";
    private static final String CREAT_TABLE_SETTING_INFO = "create table t_setting_info(\n" +
            "    username text not null unique,\n" +
            "    password text,\n" +
            "    sid integer primary key autoincrement,\n" +
            "    if_enable_password_check text not null,\n" +
            "    defult_launch_page text not null,\n" +
            "    defult_add_page text not null,\n" +
            "    current_account_book_bid integer\n" +
            "\n" +
            ")";

    private DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE_TAG);
        db.execSQL(CREATE_TABLE_ACCOUNT_BOOK);
        db.execSQL(CREAT_TABLE_EVENT_ITEM);
        db.execSQL(CREAT_TABLE_ACCOUNT_ITEM);
        db.execSQL(CREAT_TABLE_SETTING_INFO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
