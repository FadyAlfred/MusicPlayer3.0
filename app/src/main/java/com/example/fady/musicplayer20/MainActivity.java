package com.example.fady.musicplayer20;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    String [] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        lv =(ListView) findViewById(R.id.lvPlaylist);
        final ArrayList<File> mySong = findSongs(Environment.getExternalStorageDirectory());
        item = new String[mySong.size()];
        for (int i =0; i<mySong.size() ; i++){
            item[i] = mySong.get(i).getName().toString().replace(".mp3","").replace(".m4a","").replace("wav","");

        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.textView,item);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
                startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos",postion).putExtra("songlist",mySong));
            }
        });
    }

    public ArrayList<File> findSongs(File root) {
        File[] files = root.listFiles();
        ArrayList<File> al = new ArrayList<File>();
        for(File singleFile : files){
            if(singleFile.isDirectory()){
                al.addAll(findSongs(singleFile));
            }else{
                if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".m4a") || singleFile.getName().endsWith(".wav")){
                    al.add(singleFile);
                }
            }
        }

    return  al;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
