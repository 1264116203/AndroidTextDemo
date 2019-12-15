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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    //准备题目资源
    static String[] ques = {
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
    TextView txv=null;
    int witch = 0;
    //准备答案数组
    int[] result = {-1, -2, -2, -1, -2, -1, -1, -1, -2, -1};
    //题目数量
    int counter_num;
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
    //当前题目号
    int counter = 1;
    //用户是否选择了
    boolean isSelected;
    //随机数组
    static int[] random_ques = new int[5];
    private CountDownTimer mTimer;
    //随机数集合
    private static SortedSet<Integer> randomset;
    private AlertDialog firstDialog;
    DBHelper quesDB;
    private static Random ran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.bt_player);
        mListView = findViewById(R.id.list_main);
        quesDB = new DBHelper(this);
        randomset = new TreeSet();
        //设置标题
        this.setTitle("一起学习吧");

        lists = new ArrayList<>();
        //        遍历数组添加进map 并将map添加进lists
        for (int i = 0; i < ques.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(i + 1));
            map.put("strque", ques[i]);
            lists.add(map);
        }
        String[] from = {"id", "strque"};
        int[] to = {R.id.item_num, R.id.item_text};
        //使用SimpleAdapter填充ListView
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, lists, R.layout.mainlist_item, from, to);
        mListView.setAdapter(simpleAdapter);
        ran = new Random();

    }

    public void start(View view) {
        counter_num = 3;
        Random_Num(counter_num, ques.length);
        randomset = new TreeSet();
        Dialogshow(witch, counter_num);
        counter = 0;
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

    /**
     * 弹窗显示
     */
    private void Dialogshow(int which, final int counter_num) {

        //设置最大题目范围
        if (counter <= counter_num) {
            System.out.println("witch=" + witch);
            //如果当前题目在0~5之间 则成绩+20分
            System.out.println("result[random_ques[counter - 1]=" + result[random_ques[counter-1]]);
            if (counter_num != 0 && String.valueOf(which).equals(result[random_ques[counter-1]])) {
                score += 20;
                System.out.println("score=" + score);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //为倒计时控件设置属性
            txv = new TextView(this);
            txv.setGravity(Gravity.CENTER);
            txv.setTextSize(40);

            builder.setTitle("快乐答题 gogogo");
            /**
             *  如果达到最大题目数  统计成绩显示到最后的弹窗并获取当前时间
             */
            //判断是否达到最大题目数
            if (counter == counter_num) {
                //获取日历的实例
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                //获得系统时间
                int month = calendar.get(Calendar.MONTH) + 1;
                //日
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                //小时
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                //分钟
                int minute = calendar.get(Calendar.MINUTE);
                //合并字符串
                String string = year + "年" + month + "月" + day + "日 " + hour + ":" + minute;
                builder.setMessage("你的得分为：" + score + "分");
                //new contentValues对象来存储数据
                ContentValues contentValues = new ContentValues();

                contentValues.put("Score", score);
                contentValues.put("Date", string);
                quesDB.insert(contentValues, "Score_tab");
                //保存得分到Score_ta
            } else {// 未达到最大题目数 继续下一题
                System.out.println("counter=" + counter);
                System.out.println("random_ques的长度：" + random_ques.length);
                System.out.println("random_ques[counter]=" + random_ques[counter]);
                builder.setMessage(ques[random_ques[counter]]);
            }

            if (counter < counter_num) {
                builder.setView(txv);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        witch = -1;
                    }
                });
                builder.setNegativeButton("不是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        witch = -2;

                    }
                });
            }
            isSelected = false;
            firstDialog = builder.create();
            firstDialog.show();
            counter++;
            System.out.println("counter++后=" + counter);
            System.out.println("-------------------");
            countdown( firstDialog);
            firstDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    firstDialog = null;
                    if (mTimer != null) {
                        // 停止countDownTimer
                        mTimer.cancel();
                        mTimer = null;
                    }
                    if (isSelected == true) {
                        int resultcode = 0;
                        Dialogshow(resultcode, counter_num);//计算得分进入下一个问题展示页面
                        isSelected = false;
                    } else {
                        Dialogshow(0, counter_num);
                    }
                }
            });

        }


    }


    /**
     * 倒计时显示
     */
    public void countdown( AlertDialog Dialog) {
        txv.setTextColor(Color.rgb(255, 00, 00));
        final AlertDialog myDialog = Dialog;
        mTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txv.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                if (myDialog != null) {
                    myDialog.dismiss();
                }
            }
        };
        mTimer.start();
    }

    /**
     * 生成随机数
     */


    public static void Random_Num(int num_count, int num_range) {
        for (int i = 0; i < num_count; i++) {
            randomset.add(ran.nextInt(num_range));
        }
        if (randomset.size() < num_count) {
            Random_Num(num_count - randomset.size(), num_range);
        }
    }


}
