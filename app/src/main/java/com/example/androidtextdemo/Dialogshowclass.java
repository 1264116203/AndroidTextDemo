package com.example.androidtextdemo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * 弹窗显示
 */

public class Dialogshowclass {
    int counter;
    int which1 = 0;
    int score;
    int counter_num;
    int resultcode = 0;
    String[] ques_array;
    int[] result_array;
    //随机数组
    static int[] random_ques_array;
    private static Random ran;
    private AlertDialog firstDialog;

    //随机数集合
    private static Set<Integer> randomset;
    //用户是否选择了
    boolean isSelected;
    TextView txv;
    Context context;
    DBHelper quesDB;
    private CountDownTimer mTimer;

    public Dialogshowclass(Context context, String[] ques_array, int[] result_array, TextView txv) {

        this.random_ques_array = random_ques_array;
        this.result_array = result_array;
        this.ques_array = ques_array;
        this.txv = txv;
        this.context = context;
        quesDB = new DBHelper(context);

        random_ques_array = new int[counter_num];
        counter_num = 5;
        Random_Num(counter_num, ques_array.length);
        randomset = new LinkedHashSet();
        ran = new Random();

    }

    public void Dialogshow() {
        counter++;

        //设置最大题目范围
        if (counter < counter_num) {
            System.out.println("witch=" + which1);
            //如果当前题目在0~5之间 则成绩+20分
            System.out.println("random_ques[counter]=" + random_ques_array[counter - 1]);
            System.out.println("result[random_ques[counter - 1]=" + result_array[random_ques_array[counter - 1]]);

            if (counter_num != 0 && String.valueOf(which1).equals(result_array[random_ques_array[counter - 1]])) {
                score += 20;
                System.out.println("score=" + score);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            //为倒计时控件设置属性
            txv = new TextView(context);
            txv.setGravity(Gravity.CENTER);
            txv.setTextSize(40);
            builder.setTitle("快乐答题 gogogo");
            /**
             *  如果达到最大题目数  统计成绩显示到最后的弹窗并获取当前时间
             */
            System.out.println("count:" + counter);
            System.out.println("counter_num:" + (counter_num - 1));
            which1 = 1;
            //判断是否达到最大题目数
            if (counter == counter_num - 1) {
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
                builder.setMessage(ques_array[random_ques_array[counter]]);
            }
            if (counter < counter_num) {
                builder.setView(txv);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("不是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        which1 = -2;
                    }
                });
            }
            isSelected = false;
            firstDialog = builder.create();
            firstDialog.show();

            countdown(firstDialog);
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

                        //计算得分进入下一个问题展示页面
                        Dialogshow();
                        isSelected = false;
                    } else {
                        Dialogshow();
                    }
                }
            });
            System.out.println("-------------------");

        }


    }

    /**
     * 倒计时显示
     */
    public void countdown(AlertDialog Dialog) {
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
        ArrayList<Integer> arrayList = new ArrayList<>(randomset);
        for (int i = 0; i < randomset.size(); i++) {
            random_ques_array[i] = arrayList.get(i);
        }

    }


}
