package com.example.swim_suit_rank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    CountDownTimer cdt;

    int videoLengthInSec = 23; // put length of the video here
    int lengthOfSecInMillisecond = 1000; // num of milliseconds
    int maxWaitTimeAfterEndOfVideoInSec = 1; // arbitrary
    int totalTimerLengthInMillisecond = (videoLengthInSec + maxWaitTimeAfterEndOfVideoInSec) * lengthOfSecInMillisecond;


    VideoView mainVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainVideo = findViewById(R.id.mainVideo);
        mainVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.swim_suit_rank_main_video));

        cdt = new CountDownTimer(totalTimerLengthInMillisecond, lengthOfSecInMillisecond)
        {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                goToNextActivity();
            }
        }.start();
    }

    public void goToNextActivity(){
        Intent go = new Intent(MainActivity.this, SwimSuitRankActivity.class);
        startActivity(go);
    }

    public void PlayVideo(View view){
        mainVideo.start();
        cdt.cancel();
        cdt = new CountDownTimer(totalTimerLengthInMillisecond, lengthOfSecInMillisecond)
        {
            @Override
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                goToNextActivity();
            }
        }.start();
    }

    public void SkipVideo(View view){
        cdt.cancel();
        goToNextActivity();
    }
}