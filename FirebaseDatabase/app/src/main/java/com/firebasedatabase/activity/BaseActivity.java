package com.firebasedatabase.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebasedatabase.R;

public class BaseActivity extends AppCompatActivity {

    String TAG = "BaseActivity";

    protected LinearLayout llLeftMenu;
    protected LinearLayout llMainContainer;
    protected RelativeLayout rlBaseToolbar;
    protected TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base);

        initializeBase();
        clickEventBase();


    }

    protected void initializeBase(){
        try{

            tvTitle = (TextView) findViewById(R.id.tvTitle);
            rlBaseToolbar = (RelativeLayout) findViewById(R.id.rlBaseToolbar);
            llMainContainer = (LinearLayout) findViewById(R.id.llMainContainer);
            llLeftMenu = (LinearLayout) findViewById(R.id.llLeftMenu);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void clickEventBase(){
        try{

            llLeftMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
