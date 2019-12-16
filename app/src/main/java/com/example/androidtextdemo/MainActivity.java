package com.example.androidtextdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    //准备题目资源
    static String[] ques_array = {
            "android虚拟设备的缩写是AVD",
            "Service中不能执行耗时操作",
            "android 中Intent的作用的是实现应用程序间的数据共享",
            "在android中使用Menu时可能需要重写的方法是：onCreateOptionsMenu()，onOptionsItemSelected()",
            "退出Activity错误的方法是：System.exit()",
            "刘伟荣老师真帅！",
            "Toast没有焦点",
            "Toast只能持续一段时间",
            "query方法，是ContentProvider对象的方法",
            "对于一个已经存在的SharedPreferences对象setting,想向其中存入一个字符串\"person\",setting应该先调用edit()",
    };
    //初始化倒计时控件
    TextView txv = null;
    int witch = -1;
    //准备答案数组
    int[] result_array = {-1, -2, -2, -1, -2, -1, -1, -1, -2, -1};
    //题目数量

    //使用集合接收ListView数据
    List<Map<String, ?>> lists;
    ListView mListView;
    //判断播放器是否在播放
    int Flag_isplaying;
    TextView play;
    //总成绩数值统计
    int score = 0;
    //剩余时间初始化
    int lefttime = 10;
    Dialogshowclass mDialogshowclass;
    //当前题目号






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.bt_player);
        mListView = findViewById(R.id.list_main);
        //设置标题
        this.setTitle("一起学习吧");

        lists = new ArrayList<>();
        //        遍历数组添加进map 并将map添加进lists
        for (int i = 0; i < ques_array.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i + 1));
            map.put("strque", ques_array[i]);
            lists.add(map);
        }
        String[] from = {"id", "strque"};
        int[] to = {R.id.item_num, R.id.item_text};
        //使用SimpleAdapter填充ListView
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, lists, R.layout.mainlist_item, from, to);
        mListView.setAdapter(simpleAdapter);
        mDialogshowclass = new Dialogshowclass(this,ques_array,result_array,txv);

    }

    public void start(View view) {

        mDialogshowclass.Dialogshow();
    }

    public void score(View view) {
        Intent it_score = new Intent(this, HistoryActivity.class);
        startActivity(it_score);
    }

    public void music(View view) {
        if (Flag_isplaying == 0) {
            Intent intent = new Intent(this, MusicService.class);
            startService(intent);
            Flag_isplaying = 1;
            play.setText("停止");

        } else {
            Intent intent = new Intent(this, MusicService.class);
            stopService(intent);
            Flag_isplaying = 0;
            play.setText("播放");

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_author:
                Intent it_author = new Intent(this, AuthorActivity.class);
                startActivity(it_author);
                break;
            case R.id.menu_exit:
                finish();
                break;
            case R.id.menu_add:

                break;
        }

        return true;
    }



}
