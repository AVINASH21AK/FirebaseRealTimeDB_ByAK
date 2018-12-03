package com.firebasedatabase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebasedatabase.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActSelectOption extends BaseActivity {

    String TAG = "ActFirebaseDBHome";
    @BindView(R.id.tvRealTimeDatabase) TextView tvRealTimeDatabase;
    @BindView(R.id.tvAuth) TextView tvAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.inflate(this, R.layout.act_selectoption, llMainContainer);
        ButterKnife.bind(this);

        initiaize();
        clickEvent();


    }

    public void initiaize(){
        try{

            llLeftMenu.setVisibility(View.GONE);
            tvTitle.setText("Firebase - RealTime Database & Authentication");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickEvent(){
        try{

            tvRealTimeDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1 = new Intent(ActSelectOption.this, ActFirebaseDatabseLoginReg.class);
                    startActivity(i1);
                }
            });

            tvAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1 = new Intent(ActSelectOption.this, ActAuthLoginReg.class);
                    startActivity(i1);

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
