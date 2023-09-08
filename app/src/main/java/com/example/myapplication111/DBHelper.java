package com.example.myapplication111;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NeedCalendar.db";

    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터베이스가 생성 될 때 호출
        //epdlxjqpdltm -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS TodoList (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT NOT NULL, content TEXT NOT NULL,  writeDate TEXT not NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    //select 문 (할일 목록들을 조회)
    public ArrayList<TodoItem> getTodoList() {
        ArrayList<TodoItem> todoItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TodoList ORDER BY writeDate DESC", null);

        if (cursor.getCount() != 0) {
            //조회온 데이터가 있을때 내부 수행
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int contentIndex = cursor.getColumnIndex("content");
            int writeDateIndex = cursor.getColumnIndex("writeDate");

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String content = cursor.getString(contentIndex);
                String writeDate = cursor.getString(writeDateIndex);

//                    int id = cursor.getInt(cursor.getColumnIndex("id"));
//                    String title = cursor.getString(cursor.getColumnIndex("title"));
//                    String content = cursor.getString(cursor.getColumnIndex("content"));
//                    String writeDate = cursor.getString(cursor.getColumnIndex("writeDate"));

                TodoItem todoItem = new TodoItem();
                todoItem.setId(id);
                todoItem.setTitle(title);
                todoItem.setTitle(content);
                todoItem.setTitle(writeDate);

            }
        }
        cursor.close();
        return todoItems;
    }

    //insert문
    public void InsertTodo(String _title, String _content, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO TodoList (title, content, writeDate) VALUES('" + _title +  "','" + _content + "' , '" + _writeDate + "');");

    }

    //UPDATE문
    public void UpdateTodo(String _title, String _content, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE TodoList SET title ='" + _title + "',content ='" + _content + "' , writeDate ='" + _writeDate + "' WHERE writeDate='" + _beforeDate + "'");
    }

    //DELETE문(할일 목록을 제거 한다.)
    public void deleteTodo(String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM TodoList WHERE writeDate = '" + _beforeDate + "'");
    }



}
