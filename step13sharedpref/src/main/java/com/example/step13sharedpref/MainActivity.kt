package com.example.step13sharedpref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.ste13sharedpref.R

/*
    App에서 문자열을 영구 저장하는 방법 ( 영구 저장이란 앱을 종료하고 다시 시작해도 불러올 수 있는 문자열)

    1. 파일 입출력을 이용해서 저장
    2. android 내장 data base를 이용해서 저장 => SQLite DataBase
    3. SharedPreference를 이용해서 저장(느리지만 간단히 저장하고 불러올 수 있다)
       내부적으로 xml 문서를 만들어서 문자열을 저장하고 불러온다.
       저장된 문자열을 boolean, int, double, String type으로 변환해서 불러올 수 있다.

 */

class MainActivity : AppCompatActivity(), View.OnClickListener {
    /*
         java에서는 field를 선언만 하면 자동으로 null로 초기화 됨.
         kotlin에서는 null이 가능한 field를 만들어서 명시적으로 넣어줘야한다.
     */
    var editText:EditText? = null

    // onCreate()메소드 재정의
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main)

        // EditText 객체의 참조값 얻어오기
        editText = findViewById<EditText>(R.id.editText)
        // Button 객체의 참조값 얻어오기
        // val saveBtn = findViewById<Button>(R.id.saveBtn)
        val saveBtn: Button = findViewById(R.id.saveBtn)
        saveBtn.setOnClickListener(this)
    }

    // 저장 버튼을 누르면 호출되는 메소드
    override fun onClick(p0: View?) {
        //EditText에 입력한 문자열 읽어오기
        val msg = editText?.text.toString() // null이 가능한 변수나 필드의 값을 참조할 때는 ?가 필요하다.
        // SharedPreferences의 참조값 얻어오기
        val pref:SharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
        // 에디터 객체의 참조값 얻어오기
        val editor:SharedPreferences.Editor = pref.edit()
        // 에디터 객체를 이용해서 문자열을 key:value 형태로 영구 저장할 수 있다.
        editor.putString("msg", msg)
        editor.commit()

        AlertDialog.Builder(this)
            .setMessage("저장했습니다.")
            .setNeutralButton("확인",null)
            .create()
            .show()
    }
}