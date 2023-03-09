package com.example.step25imagecapture.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
    - Http 요청을 할때 서버가 응답하는 쿠키를 모두 읽어서 저장하고 싶다
    - 다음번 Http 요청을 할때 저장된 쿠키를 모두 보내고 싶다
    - 쿠키 값이 수정되어서 응답되면 저장되어 있는 쿠키를 수정해야 한다.
    - 그러면 쿠키를 SQLiteDataBase 를 활용해서 관리하면 빠르게 처리 할수 있지 않을까?
 */
public class MyHttpUtil {
    //필드
    private Context context;
    private DBHelper dbHelper;
    //생성자
    public MyHttpUtil(Context context){
        this.context=context;
        //DBHelper 객체의 참조값을 얻어내서 필드에 저장해 둔다.
        dbHelper=new DBHelper(context, "CookieDB.sqlite", null, 1);
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        //해당 DBHelper 를 처음 사용할때 호출되는 메소드 (new DBHelper() 를 처음 호춯할때)
        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블을 만들면 된다.
            String sql="CREATE TABLE board_cookie (cookie_name TEXT PRIMARY KEY, cookie TEXT)";
            db.execSQL(sql);
        }
        //DB 를 리셋(업그래이드)할때 호출되는 메소드
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //업그래이드할 내용을 작성하면 된다.
            db.execSQL("DROP TABLE IF EXISTS board_cookie"); //만일 테이블이 존재하면 삭제한다.
            //다시 만들어 질수 있도록 onCreate() 메소드를 호출한다.
            onCreate(db);
        }
    }
}










