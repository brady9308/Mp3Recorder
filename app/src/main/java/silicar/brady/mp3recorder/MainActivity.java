package silicar.brady.mp3recorder;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import silicar.brady.libmp3lame.MP3Recorder;

import static android.os.SystemClock.sleep;

/**
 * 主程序
 */

public class MainActivity extends ActionBarActivity implements FragmentRecord.RecordOnClickListener,FragmentRecording.RecordPauseListener,FragmentRecording.RecordStopListener {

    private LinearLayout tabRecord,tabFiles;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentVolume frVolume;
    private FragmentRecord frRecord;
    private FragmentRecording frRecording;
    private FragmentFiles frFiles;

    private MP3Recorder mp3Recorder;
    private File dir;
    private String file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        init();
        findView();
        setOnClick();
    }

    //初始化Activity
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init()
    {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        frVolume = new FragmentVolume();
        frRecord = new FragmentRecord();

        fragmentTransaction.replace(R.id.main,frVolume);
        fragmentTransaction.replace(R.id.btnmanage, frRecord);
        fragmentTransaction.commit();
    }

    private void findView()
    {
        tabRecord = (LinearLayout)findViewById(R.id.tabRecord);
        tabFiles = (LinearLayout)findViewById(R.id.tabFiles);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setOnClick()
    {
        //切换Fragment
        tabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tabRecord.setBackgroundColor(0xff555555);
                tabFiles.setBackgroundColor(0xff333333);
                fragmentTransaction = fragmentManager.beginTransaction();
                if(frVolume == null)
                    frVolume = new FragmentVolume();
                fragmentTransaction.replace(R.id.main,frVolume);
                fragmentTransaction.commit();
            }
        });

        tabFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tabRecord.setBackgroundColor(0xff333333);
                tabFiles.setBackgroundColor(0xff555555);
                fragmentTransaction = fragmentManager.beginTransaction();
                if(frFiles == null)
                    frFiles = new FragmentFiles();
                fragmentTransaction.replace(R.id.main,frFiles);
                fragmentTransaction.commit();
            }
        });
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

    //开始录音
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void RecordOnClick()
    {
        //设置文件路径
        dir = new File(Environment.getExternalStorageDirectory().toString()+"/AudioRecorder/");
        if(!dir.exists())
            dir.mkdir();
        //设置文件名称
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyMMddHHmmss");
        file = simpleDateFormat.format(date)+".mp3";
        mp3Recorder = new MP3Recorder(new File(dir,file));
        //设置录音质量
        mp3Recorder.setDefaultLameMp3Quality(frRecord.getQuality());
        //Log.e("SD",Environment.getExternalStorageDirectory().toString());
        try {
            mp3Recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        frRecording = new FragmentRecording();
        fragmentTransaction.replace(R.id.btnmanage, frRecording);
        fragmentTransaction.commit();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run()
            {
               while (mp3Recorder.isRecording())
               {
                   sleep(100);
                   handler.post(new Runnable() {
                       @Override
                       public void run()
                       {
                           frVolume.setVolume(mp3Recorder.getVolume()/50);
                       }
                   });
               }
            }
        }).start();
    }

    //暂停录音
    @Override
    public void RecordPauseOnClick()
    {
        mp3Recorder.pause();
    }

    //停止录音
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void RecordStopOnClick()
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        if(frRecord == null)
            frRecord = new FragmentRecord();
        fragmentTransaction.replace(R.id.btnmanage,frRecord);
        fragmentTransaction.commit();
        mp3Recorder.stop();
    }
}