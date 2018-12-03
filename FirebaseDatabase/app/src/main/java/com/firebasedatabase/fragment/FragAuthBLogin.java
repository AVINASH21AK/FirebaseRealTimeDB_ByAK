package com.firebasedatabase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.firebasedatabase.activity.ActFirebaseDBHome;
import com.firebasedatabase.model.User;
import com.firebasedatabase.utils.App;
import com.firebasedatabase.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragAuthBLogin extends Fragment {

    String TAG = "FragFirebaseDBLogin";
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtPassword) EditText edtPassword;
    @BindView(R.id.tvSumbit) TextView tvSumbit;

    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_firebasedblogin, container, false);

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

            edtName.setVisibility(View.GONE);
            edtPassword.setVisibility(View.VISIBLE);

            auth = FirebaseAuth.getInstance();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void clickEvent(){
        try{

            tvSumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    App.hideSoftKeyboardMy(getActivity(), tvSumbit);
                    final String strPassword= edtPassword.getText().toString().trim();
                    final String strEmail = edtEmail.getText().toString().trim();


                    if(checkValidation(strEmail, strPassword))
                    {
                        auth.signInWithEmailAndPassword(strEmail, strPassword)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Authenticating Completed..", Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = auth.getCurrentUser();
                                        } else {
                                            Toast.makeText(getActivity(), "Authenticating failed..", Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public boolean checkValidation(String strEmail, String strPassword){

        App.hideSoftKeyboardMy(getActivity(), tvSumbit);



        if(!App.isValidString(strEmail)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Email);
            return false;
        }

        else if(!App.isValidEmail(strEmail)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.EmailInvalid);
            return false;
        }

        if(!App.isValidString(strPassword)){
            App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.Password);
            return false;
        }

        else {
            return true;
        }
    }


}
