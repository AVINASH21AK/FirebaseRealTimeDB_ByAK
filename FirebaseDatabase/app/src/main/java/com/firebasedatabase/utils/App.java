package com.firebasedatabase.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import java.util.regex.Pattern;




public class App extends Application {

    public static String TAG = "APP";
    static Context context;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        context = getApplicationContext();
        mInstance = this;

        //-- SharedPreference
        Hawk.init(context).build();

    }


    //-- Check Internet
    public static boolean isInternetAvail(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if(isConnected)
                return true;
            else
                return false;
        }
        return false;
    }

    //-- Hide Keyboard
    public static void hideSoftKeyboardMy(Activity activity, View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //-- Log
    public static void showLog(String ActivityName, String strMessage) {
        Log.d("From: ", ActivityName + " -- " + strMessage);
    }


    //-- Snackbar
    public static void showSnackBar(View view, String strMessage) {
        // Toast.makeText(context, ""+strMessage, Toast.LENGTH_SHORT).show();

        try {
            Snackbar snackbar = Snackbar.make(view, strMessage, Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.BLACK);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setMaxLines(3);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-- Valid email
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}");
        // Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    //-- Valid string
    public static boolean isValidString(String str){

        if(str != null && str.trim().length()>0)
            return true;
        else
            return false;

    }


    //-- Px to Dp Converter
    public static int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    //-- Dp to Px Converter
    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


}
