package com.example.step17example;

import static com.example.step17example.AppConstant.BASE_URL;
import static com.example.step17example.AppConstant.REQUEST_TODO_ADDDATA;
import static com.example.step17example.AppConstant.REQUEST_TODO_DELETE;
import static com.example.step17example.AppConstant.REQUEST_TODO_INSERT;
import static com.example.step17example.AppConstant.REQUEST_TODO_LIST;
import static com.example.step17example.AppConstant.REQUEST_TODO_UPDATE;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.step17example.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/*
    view bindiing 사용하는 방법

    1. build.gradle 파일에 아래의 설정 추가

        buildFeatures{
            viewBinding = true
        }
    2. 우상단에 sync now 링크를 눌러서 설정이 적용되도록 한다.
    3. layout.xml 문서의 이름대로 클래스가 만들어진다.
       예를 들어 activity_main.xml 문서면 ActivityMainBinding클래스
                activity_sub.xml 문서면 ActivitySubBinding 클래스
 */

public class MainActivity extends AppCompatActivity implements Util.RequestListener, AdapterView.OnItemLongClickListener, AbsListView.OnScrollListener {

    ActivityMainBinding binding;
    ArrayList<Todo> list;
    TodoAdapter adapter;
    Date date;
    String msg;
    String num;
    Boolean isNext = true;
    private Boolean lastList = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // R.Layout.activity_main.xml 문서를 전개해서 View를 만들기
       binding = ActivityMainBinding.inflate(getLayoutInflater());
        // 전개된 Layout에서 root를 얻어내서 화면 구성을 한다.( 여기서는 LinearLayout이다. )
        setContentView(binding.getRoot());

        list = new ArrayList<>();


        binding.addBtn.setOnClickListener((view)->{
            date = new Date();
            msg = binding.inputText.getText().toString();
            Map<String,String> map1 = new HashMap<>();
            map1.put("content", msg);
            Util.sendPostRequest(REQUEST_TODO_INSERT, BASE_URL+"todo/insert", map1, this);
            binding.inputText.setText("");
            Util.hideKeyboard(this);
        });

        // ListView를 오랬동안 클릭했을 때
        binding.listView.setOnItemLongClickListener(this);
        binding.listView.setOnScrollListener(this);
        binding.progressbar.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Map<String,String> map = new HashMap<>();
        num = "1";
        map.put("num", num);

        Util.sendPostRequest(REQUEST_TODO_LIST, BASE_URL+"todo/list", map, this);

    }

    @Override
    public void onSuccess(int requestId, Map<String, Object> result)  {
        if(requestId == REQUEST_TODO_LIST){
            String data = (String)result.get("data");
            try {
                JSONObject array = new JSONObject(data);
                JSONArray array2 = array.getJSONArray("result");
                isNext = array.getBoolean("isNext");

                for(int i=0; i<array2.length(); i++){
                    Todo todo = new Todo();
                    todo.setNum(array2.getJSONObject(i).getInt("num"));
                    todo.setContent(array2.getJSONObject(i).getString("content"));
                    todo.setRegdate(array2.getJSONObject(i).getString("regdate"));
                    list.add(todo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new TodoAdapter(this, R.layout.listview_cell, list);
            binding.listView.setAdapter(adapter);
        }else if(requestId == REQUEST_TODO_INSERT){
            Util.sendPostRequest(REQUEST_TODO_ADDDATA, BASE_URL+"todo/getAddData", null, this);
        }else if(requestId == REQUEST_TODO_ADDDATA){
            String data = (String)result.get("data");
            try {
                JSONObject json = new JSONObject(data);
                Todo todo = new Todo();
                todo.setNum(json.getInt("num"));
                todo.setContent(json.getString("content"));
                todo.setRegdate(json.getString("regdate"));
                list.add(0, todo);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(requestId == REQUEST_TODO_UPDATE){
            binding.progressbar.setVisibility(View.GONE);

            String data = (String)result.get("data");
            JSONObject array;
            try {
                array = new JSONObject(data);
                JSONArray array2 = array.getJSONArray("result");
                isNext = array.getBoolean("isNext");

                for(int i=0; i<array2.length(); i++){
                    Todo todo = new Todo();
                    todo.setNum(array2.getJSONObject(i).getInt("num"));
                    todo.setContent(array2.getJSONObject(i).getString("content"));
                    todo.setRegdate(array2.getJSONObject(i).getString("regdate"));
                    list.add(todo);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        Log.d("#### json 문자열 ####", "fail");

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         /*
                            view는 클릭한 cell의 View
                            position은 클릭한 cell의 인덱스
                            id는 클릭한 cell 모델의 primary key 값
                         */
                        // 삭제할 할일의 primary key 값을 Map에 담고
                       HashMap<String, String> map = new HashMap<>();
                        map.put("num", Integer.toString(list.get(position).getNum()));
                        Util.sendPostRequest(REQUEST_TODO_DELETE, BASE_URL+"todo/delete", map, MainActivity.this);

                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("아니오", null)
                .create()
                .show();

        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(isNext == true && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastList ){
            binding.progressbar.setVisibility(View.VISIBLE);

            Map<String,String> map = new HashMap<>();
            int next = Integer.parseInt(num)+1;
            num = Integer.toString(next);

            map.put("num", num);

            Util.sendPostRequest(REQUEST_TODO_UPDATE, BASE_URL+"todo/list", map, this);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastList = (totalItemCount > 0 ) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }
}