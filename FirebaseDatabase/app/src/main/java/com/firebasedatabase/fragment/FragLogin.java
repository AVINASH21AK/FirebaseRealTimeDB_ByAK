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
import com.firebasedatabase.activity.ActHome;
import com.firebasedatabase.activity.ActOption;
import com.firebasedatabase.model.User;
import com.firebasedatabase.utils.App;
import com.firebasedatabase.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragLogin extends Fragment {

    String TAG = "FragLogin";
    @BindView(R.id.edtName) EditText edtName;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.tvSumbit) TextView tvSumbit;


    int tempTotal = 0;
    int totalUsers = 0;
    String strUserID = "";
    boolean shouldUserLogin = false;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    User globalModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_login, container, false);

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
                    shouldUserLogin = false;

                    App.hideSoftKeyboardMy(getActivity(), tvSumbit);
                    final String strName = edtName.getText().toString().trim();
                    final String strEmail = edtEmail.getText().toString().trim();


                    mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            totalUsers = (int) dataSnapshot.getChildrenCount();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    mFirebaseInstance.getReference(Constants.DATABASE.login).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(totalUsers > 0) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                    tempTotal = tempTotal + 1;
                                    App.showLog(TAG, "tempTotal: " + tempTotal + " -- strUserID: " + strUserID);

                                    User post = postSnapshot.getValue(User.class);
                                    App.showLog(TAG, "User: id: " + post.id + " name: " + post.name + " email: " + post.email);

                                    if (strEmail.equalsIgnoreCase(post.email)) {
                                        strUserID = post.id;
                                        shouldUserLogin = true;

                                        globalModel = new User();
                                        globalModel.id = post.id;
                                        globalModel.name = post.name;
                                        globalModel.email = post.email;
                                    }
                                }
                            }
                            else {
                                App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.UserNotRegister);
                            }



                            if(tempTotal == totalUsers) {
                                if (shouldUserLogin) {
                                    final User user = new User(strUserID, strName, strEmail);
                                    mFirebaseDatabase.child(strUserID).setValue(user);
                                    App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.SuccessLogin);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Hawk.put(Constants.SHAREDPREFERENCE.Login, "1");
                                            Hawk.put(Constants.SHAREDPREFERENCE.UserID, globalModel.id);
                                            Hawk.put(Constants.SHAREDPREFERENCE.UserName, globalModel.name);
                                            Hawk.put(Constants.SHAREDPREFERENCE.UserEmail, globalModel.email);

                                            Intent i1 = new Intent(getActivity(), ActHome.class);
                                            startActivity(i1);

                                        }
                                    }, 1000);




                                } else {
                                    App.showSnackBar(tvSumbit, Constants.MESSAGES.VALIDATIONS.UserNotRegister);
                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                            Log.d(TAG, "Failed to read app title value.", error.toException());
                        }
                    });


                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
