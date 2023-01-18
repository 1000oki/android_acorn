package com.example.step09gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameView extends View {
    // 필드
    Bitmap backImg; //배경 이미지
    int width, height; // 화면의 폭과 높이 (GameView가 차지하고 있는 화면의 폭과 높이)

    // 드래곤의 이미지를 저장할 배열
    Bitmap[] dragonImgs = new Bitmap[4];
    // 드래곤 이미지 인덱스
    int dragonIndex = 0;
    // 유닛(드래곤, 적기)의 크기를 저장할 필드
    int unitSize;
    // 드래곤의 좌표를 저장할 필드(가운데 기준)
    int dragonX, dragonY;
    // 배경 이미지의 y 좌표
    int back1Y, back2Y;

    int index = 0;
    int i2 = -1;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    // 초기화 메소드
    public void init(){
        // 원본 배경 이미지 읽어 들이기
        Bitmap backImg = BitmapFactory.decodeResource(getResources(), R.drawable.backbg);
        // 배경 이미지를 view의 크기에 맞게 조절해서 필드에 저장
        this.backImg = Bitmap.createScaledBitmap(backImg, width, height, false);

        //드레곤 이미지를 로딩해서 사이즈를 조절하고 배열에 저장한다.
        Bitmap dragonImg1=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit1);
        Bitmap dragonImg2=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit2);
        Bitmap dragonImg3=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit3);
        dragonImg1=Bitmap
                .createScaledBitmap(dragonImg1, unitSize, unitSize, false);
        dragonImg2=Bitmap
                .createScaledBitmap(dragonImg2, unitSize, unitSize, false);
        dragonImg3=Bitmap
                .createScaledBitmap(dragonImg3, unitSize, unitSize, false);
        dragonImgs[0]=dragonImg1;
        dragonImgs[1]=dragonImg2;
        dragonImgs[2]=dragonImg3;
        dragonImgs[3]=dragonImg2;
    }
    // View가 활성화 될 때 최초 한번 호출되고 View의 사이즈가 바뀌면 다시 호출된다.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // view가 차지하고 있는 폭과 높이가 px단위로 w,h에 전달된다.
        width=w;
        height=h;
        // unitSize는 화면 폭의 1/5로 설정
        unitSize = w/5;
        // 드래곤의 초기 좌표 부여
        dragonX = w/2;
        dragonY = height-unitSize/2;

        // Handler객체에 메세지를 보내서 화면이 주기적으로 갱신되는 구조로 바꾼다.
        handler.sendEmptyMessageDelayed(0,20);

        // 배경 이미지의 초기 좌표
        back1Y = 0;
        back2Y = -height;

        // 초기화 메소드 호출
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // (0, 0)좌표에 배경 이미지 그리기
        canvas.drawBitmap(backImg, 0, back1Y, null);
        canvas.drawBitmap(backImg, 0, back2Y, null);

        // 드래곤 그리기
        canvas.drawBitmap(dragonImgs[index], dragonX-unitSize/2, dragonY-unitSize/2, null);

        back1Y += 3;
        back2Y += 3;

        // 만일 배경1의 좌표가 아래로 벗어나면
        if(back1Y >= height){
            // 배경1를 상단으로 다시 보낸다.
            back1Y = -height;
            // 배경2와 오차가 생기지 않게 하기위해 복원하기
            back2Y=0;
        }
        // 만일 배경2의 좌표가 아래로 벗어나면
        if(back2Y >= height){
            // 배경2를 상단으로 다시 보낸다
            back2Y = -height;
            // 배경1와 오차가 생기지 않게 하기위해 복원하기
            back1Y=0;
        }
    }

    // View에 터치 이벤트가 발생하면 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 터치한 곳의 좌표를 드래곤의 x좌표에 반영하기
        dragonX = (int)event.getX();
        return true;
    }

    // View가 활성화 될 때 최초 한번 호출되고 View의 사이즈가 바뀌면 다시 호출된다.
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            // 화면 갱시하기
            invalidate();
            // handler 객체에 빈 메세지를 10/1000 초 이후에 다시 보내기
            handler.sendEmptyMessageDelayed(0,10);
        }
    };
}
