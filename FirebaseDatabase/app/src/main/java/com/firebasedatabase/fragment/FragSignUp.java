package com.firebasedatabase.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebasedatabase.R;
import com.firebasedatabase.model.User;
import com.firebasedatabase.utils.App;
import com.firebasedatabase.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragSignUp extends Fragment {

    String TAG = "FragSignUp";
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.tvSumbit) TextView tvSumbit;

    int strUserID = 0;
    int tempTotal = 0;
    boolean shouldUserRegister;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_signup, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try{

            initialize();
            clickEvent();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void initialize(){
        try {
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

                    tempTotal = 0;
                    shouldUserRegister = true;

                    App.hideSoftKeyboardMy(getActivity(), tvSumbit);
                    final String strName = edtName.getText().toString().trim();
                    final String strEmail = edtEmail.getText().toString().trim();


                    if(checkValidation(strName, strEmail))
                    {

                        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                strUserID = (int) dataSnapshot.getChildrenCount();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        //-- Check Email-ID Exist
                        mFirebaseInstance.getReference(Constants.DATABASE.login).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                if(strUserID > 0) {
                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                        tempTotal = tempTotal + 1;
                                        App.showLog(TAG, "tempTotal: " + tempTotal + " -- strUserID: " + strUserID);

                                        User post = postSnapshot.getValue(User.class);
                                        App.showLog(TAG, "User:  id: " + post.id + " name: " + post.name + " email: " + post.email);

                                        if (strEmail.equalsIgnoreCase(post.email)) {
                                            shouldUserRegister = false;
                                        }
                                    }
                                }
                                else {
                                    App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.EmailAlreadyUsed);
                                }


                                if(tempTotal == strUserID){

                                    //-- Register User if Not Exists
                                    if(shouldUserRegister)
                                    {
                                        final User user = new User(""+strUserID, strName, strEmail);
                                        mFirebaseDatabase.child(""+strUserID).setValue(user);

                                        App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.SuccessRegistered);
                                    }
                                    else {
                                        App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.EmailAlreadyUsed);
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                                App.showLog(TAG, "Failed to read app title value: "+ error.toException());
                            }
                        });






                    }

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkValidation(String strName, String strEmail){

        App.hideSoftKeyboardMy(getActivity(), tvSumbit);

        if(!App.isValidString(strName)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Name);
            return false;
        }

        if(!App.isValidString(strEmail)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Email);
            return false;
        }

        else if(!App.isValidEmail(strEmail)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.EmailInvalid);
            return false;
        }

        else {
            return true;
        }
    }



}
