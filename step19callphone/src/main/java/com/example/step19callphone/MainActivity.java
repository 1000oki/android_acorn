package com.example.step19callphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.step19callphone.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 클릭 리스너 등록
        binding.dialBtn.setOnClickListener(v->{
            String phoneNumber = binding.inputPhone.getText().toString();
            // 전화를 걸겠다는 Intent(의도) 객체 생성하기
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL); // 전화를 걸고 싶다는 의도를 Intent 객체에 담고
            // 전화번호를 Uri 객체에 포장을 한다.
            Uri uri = Uri.parse("tel:"+phoneNumber);
            // Intent에 데이터를 담는다.
            intent.setData(uri);
            // 해당 Intent를 받아주는 액티비티를 실행해주세요라고 운영체제에 요청을 한다.
            startActivity(intent);
        });

        binding.callBtn.setOnClickListener(v->{
            // 전화를 걸기 전에 전화 걸기 허용을 했는지 확안
            // 전화걸기 권한이 체크되었는지 상수값 얻어오기
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);
            // 만일 권한이 허용되지 않았다면
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                // 권한을 허용하도록 유도한다.
                // 권한이 필요한 목록을 배열에 담는다.
                String[] permissions = {Manifest.permission.CALL_PHONE};
                // 배열을 전달해서 해당 권한을 부여하도록 요청한다.
                ActivityCompat.requestPermissions(MainActivity.this,
                        permissions,
                        0); //요청의 아이디
                return; //메소드는 여기서 종료
            }
            // 전화걸기
            call();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            // 만일 권한을 부여 받았다면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // 전화걸기
                call();
            }else{// 부여 받지 않았다면
                Toast.makeText(this, "전화를 거는 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void call(){
        String phoneNumber = binding.inputPhone.getText().toString();
        // 전화를 걸겠다는 Intent(의도) 객체 생성하기
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL); // 전화를 걸고 싶다는 의도를 Intent 객체에 담고
        // 전화번호를 Uri 객체에 포장을 한다.
        Uri uri = Uri.parse("tel:"+phoneNumber);
        // Intent에 데이터를 담는다.
        intent.setData(uri);
        // 해당 Intent를 받아주는 액티비티를 실행해주세요라고 운영체제에 요청을 한다.
        startActivity(intent);
    }
}