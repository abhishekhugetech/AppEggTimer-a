package com.abhishek.appeggtimer_a;

import android.icu.text.UnicodeSetSpanner;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Creating an Image View to Replace when countdown is finished
    ImageView mImageView;
    // Create a new Boolean called isCounterActive to help us reset the Timer and set it default to false; (13)
    Boolean isCounterActive = false;
    // Declaring the Seekbar Timer so that we can use it in our Controller Method (5)
    SeekBar timerSeekBar;
    // Declaring the TextView here so that we can use it in our updateTimer Method (8)
    TextView timerTextView;
    // Creating the countdown (14)
    CountDownTimer mCountDownTimer;
    // Selecting the Button (14)
    Button timerButton;
    // Creating the finish countdown method (14)
    public void finishCountdown(){
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        mCountDownTimer.cancel();
        timerButton.setText("Go !");
        timerSeekBar.setEnabled(true);
        isCounterActive = false;
    }
    //Creting the updateTimer method (7)
    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;
        String minuteString = Integer.toString(minutes);
        if(minutes <10 ){
            minuteString = "0"+minutes;
        }
        String secondsString = Integer.toString(seconds);
        if(seconds < 10){
            secondsString = "0"+seconds;
        }
        timerTextView.setText(minuteString+":"+secondsString);
        timerSeekBar.setProgress(secondsLeft);
    }
    // Created the method for the Go button (4)
    public void controller(View view){
        mImageView.setImageResource(R.drawable.egg);
        // In the button click method first we will check that if counter is active is false then inside that we will set that (13)
        // boolean to true, Set the SeekBar to disabled,Set the button text to stop. (13)
        if(!isCounterActive) {
            //  Setting the isCounterActive to true (13)
            isCounterActive = true;
            // Setting SeekBar to disabled
            timerSeekBar.setEnabled(false);
            // Setting button text
            timerButton.setText("Stop !");
            // Fixing the Lag of Time cause due to the onTick method which stats after some 100 milliseconds after the countdown
            // timer has stated so that's why i added 100 milliseconds to the millisInFuture parameter (11)
            mCountDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisecondsLeft) {
                    // Now inside the ontick method we have to update the Timer so for that we are going to create a method so that
                    // we don't have to repeat the same code (6)
                    // Adding updateTimer method so that every Seconds the TextView Updates(9)
                    updateTimer((int) millisecondsLeft / 1000);
                }

                @Override
                public void onFinish() {
                    // Setting the Timer to 00:00 on finish and Toasting out the finish Couuntdown text (10)
                    timerTextView.setText("00:00");
                    Toast.makeText(getApplicationContext(), "Countdown Finished", Toast.LENGTH_SHORT).show();
                    // Creating a mediaplayer to play the sound of Egg crack (12 Playing the egg sound;)
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.egg);
                    mediaPlayer.start();
                    finishCountdown();
                    mImageView.setImageResource(R.drawable.eggcrack);
                }
            }.start();
        }else{
            //Inside the else  we will set the timerTextview to 30 sec,set progress of seekbar to 30 sec,cancel the countdown, (14)
            // set controller button text to go! , Set the seekbar enabled and then set counterisActive to false an then we (14)
            // will put all this code inside a method called resetTimer and then we will add this method to finish Countdown and (14)
            // inside the else condition . (14)
            finishCountdown();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Selected the Seekbar and the Text View (1)
        timerSeekBar = findViewById(R.id.timeSeekBaar);
        timerTextView = findViewById(R.id.timerTextView);
        // Selected the max and the Progress for timer Seekbar(2)
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        // Setted an On Seekbar change listener to the Seekbar(3)
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        timerButton = findViewById(R.id.controllerButton);
        mImageView = findViewById(R.id.imageView);
    }
}