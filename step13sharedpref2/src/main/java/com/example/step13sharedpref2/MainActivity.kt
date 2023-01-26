package com.example.step13sharedpref2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceManager

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
    var editText: EditText? = null

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

        val readBtn = findViewById<Button>(R.id.readBtn)
        /*
        readBtn.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
            }
        })
         */
        // 위의 코드를 간략히 표현하면 아래와 같다.
        readBtn.setOnClickListener{
            val pref:SharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
            // "msg"라는 키값으로 저장된 문자열 읽어오기, 없다면 default값이 읽어와 진다.
            val msg = pref.getString("msg", "")
            /*
                여기서 this는 MainActivity객체를 가리킨다.
                java에서는 익명클래스 안에서 바깥클래스 객체의 참조값을 가리키려면
                MainActivity.this와 같이 클래스명을 명시했어야한다.

                kotlin에서도 익명 클래스 안에서 바깥 클래스 객체의 참조값을 가리키려면
                this@MainClass와 같이 클래스명을 명시하면된다.
             */
            Toast.makeText(this, msg,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        // 액티비티가 활성화되는 시점에 설정에 저장된 값을 읽어오고 싶다면 여기서 작업하면 된다.
        val signature = pref.getString("signature","")
        val reply = pref.getString("reply", "")
        val sync = pref.getBoolean("sync", false)

        Toast.makeText(this, "signature:${signature}, reply:${reply}, sync:${sync}", Toast.LENGTH_LONG).show()
    }

    // 저장 버튼을 누르면 호출되는 메소드
    override fun onClick(p0: View?) {
        //EditText에 입력한 문자열 읽어오기
        val msg = editText?.text.toString() // null이 가능한 변수나 필드의 값을 참조할 때는 ?가 필요하다.
        // SharedPreferences의 참조값 얻어오기
        val pref: SharedPreferences = getSharedPreferences("info", Context.MODE_PRIVATE)
        // 에디터 객체의 참조값 얻어오기
        val editor: SharedPreferences.Editor = pref.edit()
        // 에디터 객체를 이용해서 문자열을 key:value 형태로 영구 저장할 수 있다.
        editor.putString("msg", msg)
        editor.commit()

        AlertDialog.Builder(this)
            .setMessage("저장했습니다.")
            .setNeutralButton("확인",null)
            .create()
            .show()
    }
    // 옵션 메뉴를 구성하는 메소드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menu/menu_main.xml 문서를 전개해서 메뉴 구성하기
        // in java => getMenuInflater().inflate()
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // 옵션 아이템을 선택했을 때 호출되는 메소드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 선택한 메뉴의 아이디 읽어오기
        val id = item.itemId
        // 만일 설정 메뉴를 선택했다면
        if(id == R.id.setting){
            // kotlin에서 특정 클래스 type은 클래스명::class.java라고 해야한다.
            val intent = Intent(this, SettingsActivity::class.java)
            // SettingsActivity를 시작하겠다는 의도를 가지고 있는 Intent 객체를 이용해서
            // 액티비티 시작 시키기
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}