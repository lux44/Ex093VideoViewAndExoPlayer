package com.lux.ex093videoviewandexoplayer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.lux.ex093videoviewandexoplayer.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    //Video View 의 성능을 개선한 라이브러리 : ExoPlayer

    ActivitySecondBinding binding;

    //video uri
    Uri videoUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");

    //실제 비디오를 플레이하는 객체의 참조변수
    ExoPlayer exoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_second);
        binding=ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //실제 비디오를 실행하는 객체 생성
        exoPlayer=new ExoPlayer.Builder(this).build();
        //플레이어 뷰에게 플레이어 설정
        binding.pv.setPlayer(exoPlayer);

        //비디오 1개 설정하기 *******
        MediaItem mediaItem=MediaItem.fromUri(videoUri);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.play();   //자동으로 로딩이 끝났을때 시작함.

        //비디오 여러개 설정하기
//        Uri firstUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4");
//        Uri secondUri=Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
//
//        MediaItem mediaItem1=MediaItem.fromUri(firstUri);
//        MediaItem mediaItem2=MediaItem.fromUri(secondUri);
//        exoPlayer.addMediaItem(mediaItem1);
//        exoPlayer.addMediaItem(mediaItem2);
//
//        exoPlayer.prepare();
//        exoPlayer.play();
//        exoPlayer.setRepeatMode(ExoPlayer.REPEAT_MODE_ALL);

        binding.btn.setOnClickListener(view -> {
            //ExoPlayer 는 fullScreen  기능이 없기에 하고 싶다면
            //별도의 fullScrenn 화면을 만들어서 실행해야 함.
            Intent intent=new Intent(this,FullScreenActivity.class);
            intent.setData(videoUri);

            //전체화면 모드에서 현재까지 재생된 위치를 받아서 이어서 플레이 하도록
            long currentPos=exoPlayer.getCurrentPosition();
            intent.putExtra("currentPos",currentPos);

            //결과를 받기 위해 액팉비티를 실행
            resultLauncher.launch(intent);
        });
    }
    ActivityResultLauncher<Intent> resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK) {
                Intent intent=result.getData();
                long currentPos=intent.getLongExtra("currentPos",0);
                exoPlayer.seekTo(currentPos);
                exoPlayer.play();
            }
        }
    });

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.pause();
    }
}