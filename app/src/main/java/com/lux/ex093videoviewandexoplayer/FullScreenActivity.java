package com.lux.ex093videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.lux.ex093videoviewandexoplayer.databinding.ActivityFullScreenBinding;

public class FullScreenActivity extends AppCompatActivity {

    ActivityFullScreenBinding binding;
    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_full_screen);
        binding=ActivityFullScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exoPlayer=new ExoPlayer.Builder(this).build();
        binding.pv.setPlayer(exoPlayer);

        //이전 액티비티에서 넘어온 Intent 에게 플레이 할 비디오의 uri 데이터 받기
        Intent intent=getIntent();
        Uri videoUri =intent.getData();
        MediaItem mediaItem=MediaItem.fromUri(videoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();

        //재생 위치 설정
        long currentPos=intent.getLongExtra("currentPos",0);
        exoPlayer.seekTo(currentPos);
        exoPlayer.play();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //플레이어를 메모리에서 완전 삭제하기
        exoPlayer.release();
    }

    @Override
    public void onBackPressed() {
        //현재 위치를 다시 SecondActivity()에 전달하기
        long currentPos=exoPlayer.getCurrentPosition();
        Intent intent=getIntent();
        intent.putExtra("currentPos",currentPos);
        setResult(RESULT_OK,intent);

        finish();
    }
}