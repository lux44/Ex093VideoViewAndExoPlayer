package com.lux.ex093videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.lux.ex093videoviewandexoplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    //video 파일은 용량이 크기 때문에 대부분의 경우 res 폴더에 넣고 사용하지 않음.
    //res 폴더 안에 raw 하위 폴더에 비디오, 오디오 파일이 위치함. -- 하지만 비디오는 거의 권장되지 않음. 용량이 너무 크기 때문
    //보통은 서버에 비디오 파일을 저장하고 이를 불러와서 플레이함.
    //sample video uri 를 검색하여 실습
    Uri videoUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //비디오뷰에 '컨트롤바'를 붙이기
        binding.vv.setMediaController(new MediaController(this));

        //비디오 뷰에 동영상의 uri 설정하기
        binding.vv.setVideoURI(videoUri);
        //동영상을 읽어오는데 시간이 소요되기 때문에 바로 start 하지 않고 가급적
        //비디오의 플레이 준비가 완료되었을때 start()를 하도록 권장
        binding.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //비디오 시작
                binding.vv.start();
            }
        });

        binding.btn.setOnClickListener(view -> {
            startActivity(new Intent(this,SecondActivity.class));
            finish();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        //비디오 일시정지
        if (binding.vv.isPlaying()) binding.vv.pause();
    }
}