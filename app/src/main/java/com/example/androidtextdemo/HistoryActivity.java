package com.example.androidtextdemo;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemLongClickListener {
    EditText edserach;
    ImageView imgserach;
    ListView listhistory;
    DBHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        edserach=findViewById(R.id.ed_serach);
        imgserach=findViewById(R.id.img_serach);
        listhistory=findViewById(R.id.list_history);
        imgserach.setOnClickListener(this);
        listhistory.setOnItemLongClickListener(this);
        mDBHelper=new DBHelper(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //关于作者界面返回到主界面
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        return true;
    }

    @Override
    public void onClick(View v) {
        System.out.println("结果集为：");

        //通过EditText获取需要搜索的姓名
        String name = edserach.getText().toString();

        //返回通过name查询到的Cursor结果集
        Cursor query = mDBHelper.query(name,"TABname",this);
            String[] from={"id","name","socre","time"};
            int[] to ={R.id.tv_id,R.id.tv_name,R.id.tv_score,R.id.tv_time};
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.historylist_item, query, from, to,1);
            listhistory.setAdapter(simpleCursorAdapter);

       

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        mDBHelper.delete((int) id,"TABname");
        return false;
    }
}
