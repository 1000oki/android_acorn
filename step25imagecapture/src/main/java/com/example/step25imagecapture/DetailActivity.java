package com.example.step25imagecapture;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.step25imagecapture.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // DetailActivity가 활성화 될 때 전달 받은 Intent 객체의 참조값 얻어오기
        // GalleryListActivity에서 생성한 Intent 객체이기 때문에
        // "dto"라는 키값으로 GalleryDto 객체가 들어 있다.
        Intent intent = getIntent();
        GalleryDto dto = (GalleryDto)intent.getSerializableExtra("dto");

        Glide.with(this)
                .load(dto.getImagePath())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageView);

        binding.writer.setText("작성자 : "+dto.getWriter());
        binding.caption.setText(dto.getCaption());
        binding.regdate.setText(dto.getRegdate());

    }
}