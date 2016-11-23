package com.example.fady.musicplayer20;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {

    public static MediaPlayer mp;
    ArrayList<File> mySongs;
    int postion;
    Thread updateSeekBar;

    SeekBar sb;
    Button btPlay , btFF, btNxt, btFB, btPv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sb = (SeekBar) findViewById(R.id.seekBar);
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                //super.run();
                int totalDuration =mp.getDuration();
                int currentPostion=0;

                while (currentPostion<totalDuration){
                    try {
                        sleep(500);
                        currentPostion = mp.getCurrentPosition();
                        sb.setProgress(currentPostion);
                        sb.setMax(totalDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        if(mp !=null){
            mp.stop();
            mp.release();
        }


        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySongs = (ArrayList) b.getParcelableArrayList("songlist");
        postion = b.getInt("pos");

        Uri u = Uri.parse(mySongs.get(postion).toString());
        mp = MediaPlayer.create(getApplicationContext(),u);
        //sb.setMax(mp.getDuration());
        mp.start();
        updateSeekBar.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });



        btPlay = (Button) findViewById(R.id.btPlay);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mp.isPlaying()){
                    mp.pause();
                    btPlay.setText(">");
                }else{btPlay.setText("II");
                    mp.start();}
            }
        });


        btFF = (Button) findViewById(R.id.btFF);
        btFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()+5000);
            }
        });


        btNxt = (Button) findViewById(R.id.btNxt);
        btNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();
                postion = (postion+1)%mySongs.size();
                Uri Nu = Uri.parse(mySongs.get(postion).toString());
                mp = MediaPlayer.create(getApplicationContext(),Nu);
                //sb.setMax(mp.getDuration());
                mp.start();
            }
        });


        btFB = (Button) findViewById(R.id.btFB);
        btFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()-5000);
            }
        });


        btPv = (Button) findViewById(R.id.btPv);
        btPv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();
                postion = (postion-1 <0)? mySongs.size()-1 : postion-1;
                Uri Pu = Uri.parse(mySongs.get(postion).toString());
                mp = MediaPlayer.create(getApplicationContext(),Pu);
                //sb.setMax(mp.getDuration());
                mp.start();

            }
        });

    }
    }

