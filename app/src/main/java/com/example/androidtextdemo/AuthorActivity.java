package com.example.androidtextdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.third,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //关于作者界面返回到主界面
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        return true;
    }
}
