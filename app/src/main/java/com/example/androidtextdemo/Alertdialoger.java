package com.example.androidtextdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * @author glsite.com
 * @version $
 * @des
 * @updateAuthor $
 * @updateDes
 */
public class Alertdialoger {
    public Alertdialoger(AlertDialog.Builder builder) {
        builder
                .setTitle("这是标题")
                .setMessage("有多个按钮")
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })

                .create();
        builder.show();

    }


}
