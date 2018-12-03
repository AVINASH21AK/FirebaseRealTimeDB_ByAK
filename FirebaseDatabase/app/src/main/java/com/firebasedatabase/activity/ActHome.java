package com.firebasedatabase.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebasedatabase.R;
import com.firebasedatabase.model.User;
import com.firebasedatabase.utils.App;
import com.firebasedatabase.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActHome extends BaseActivity {

    String TAG = "ActHome";
    @BindView(R.id.edtID) EditText edtID;
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.tvSumbit) TextView tvSumbit;
    @BindView(R.id.tvDelete) TextView tvDelete;


    String strUserID="", strEmail="", strName="";
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup.inflate(this, R.layout.act_home, llMainContainer);
        ButterKnife.bind(this);


        initiaize();
        clickEvent();
        insertRealTimeDatabase();  //--https://firebase.google.com/docs/database/android/start/?authuser=0


    }

    public void initiaize(){
        try{

            tvTitle.setText("Home");


            strUserID = Hawk.get(Constants.SHAREDPREFERENCE.UserID).toString();
            strEmail = Hawk.get(Constants.SHAREDPREFERENCE.UserEmail).toString();
            strName = Hawk.get(Constants.SHAREDPREFERENCE.UserName).toString();

            edtID.setEnabled(false);
            edtEmail.setEnabled(false);

            edtID.setText(strUserID);
            edtEmail.setText(strEmail);
            edtName.setText(strName);

            mFirebaseInstance = FirebaseDatabase.getInstance();
            mFirebaseDatabase = mFirebaseInstance.getReference(Constants.DATABASE.login);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clickEvent(){
        try{

            tvSumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    App.hideSoftKeyboardMy(ActHome.this, tvTitle);
                    String name = edtName.getText().toString().trim();
                    final User user = new User(strUserID, name, strEmail);
                    mFirebaseDatabase.child(strUserID).setValue(user);

                    App.showSnackBar(tvTitle, Constants.MESSAGES.VALIDATIONS.SuccessUpdated);
                }
            });

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    App.hideSoftKeyboardMy(ActHome.this, tvTitle);
                    showDeleteAlert();

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void insertRealTimeDatabase(){
        try{


            mFirebaseInstance.getReference(Constants.DATABASE.title).setValue(tvTitle.getText().toString().trim());
            mFirebaseInstance.getReference(Constants.DATABASE.title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String appTitle = dataSnapshot.getValue(String.class);
                    tvTitle.setText(appTitle);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    App.showLog(TAG, "Failed to read app title value: "+error.toException());
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void showDeleteAlert(){
        try{

            //-- Show dialog
            final AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(ActHome.this, android.R.style.Theme_Material_Light_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(ActHome.this);
            }

            String strMessage = "Your account will be deleted.";
            builder
                    .setMessage(Html.fromHtml("<font color='#000000'>" + strMessage + "</font>"))
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            mFirebaseDatabase.child(strUserID).removeValue();
                            dialog.dismiss();
                            App.showSnackBar(tvTitle, Constants.MESSAGES.VALIDATIONS.SuccessDeleted);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            final AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.colorPrimary));

            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setTextColor(getResources().getColor(R.color.colorPrimary));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
