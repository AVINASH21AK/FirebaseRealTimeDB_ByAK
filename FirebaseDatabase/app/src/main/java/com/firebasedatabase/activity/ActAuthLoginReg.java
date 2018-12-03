package com.firebasedatabase.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.firebasedatabase.R;
import com.firebasedatabase.fragment.FragAuthBLogin;
import com.firebasedatabase.fragment.FragAuthSignUp;
import com.firebasedatabase.fragment.FragFirebaseDBLogin;
import com.firebasedatabase.fragment.FragFirebaseDBSignUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActAuthLoginReg extends BaseActivity{

    String TAG = "ActFirebaseDatabseLoginReg";

    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) public ViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ViewGroup.inflate(this, R.layout.act_firebasedatabaseloginreg, llMainContainer);
            ButterKnife.bind(this);


           // startActivity(new Intent(ActFirebaseDatabseLoginReg.this, ActFirebaseDBHome.class));

            initialize();
            clickEvent();
            setupViewPager();


            tabLayout.setupWithViewPager(viewPager);


            //-- Width of Indicator Line margin left & Right & to show wrap content to text
            wrapTabIndicatorToTitle(tabLayout, 0, 60);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }







    public void initialize() {
        try {

            rlBaseToolbar.setVisibility(View.GONE);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickEvent() {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //-- Start Setup ViewPager

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragAuthBLogin(), "Login");
        adapter.addFragment(new FragAuthSignUp(), "Register");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount()); //-used to call all apis for all fragments.
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    //-- Indicator Line margin left & Right & to show wrap content to text
    public void wrapTabIndicatorToTitle(TabLayout tabLayout, int externalMargin, int internalMargin) {
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            int childCount = ((ViewGroup) tabStrip).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View tabView = tabStripGroup.getChildAt(i);
                tabView.setMinimumWidth(0);
                tabView.setPadding(0, tabView.getPaddingTop(), 0, tabView.getPaddingBottom());
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    if (i == 0) {
                        // left
                        setMargin(layoutParams, externalMargin, internalMargin);
                    } else if (i == childCount - 1) {
                        // right
                        setMargin(layoutParams, internalMargin, externalMargin);
                    } else {
                        // internal
                        setMargin(layoutParams, internalMargin, internalMargin);
                    }
                }
            }

            tabLayout.requestLayout();
        }
    }

    private void setMargin(ViewGroup.MarginLayoutParams layoutParams, int start, int end) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            layoutParams.setMarginStart(start);
            layoutParams.setMarginEnd(end);
        } else {
            layoutParams.leftMargin = start;
            layoutParams.rightMargin = end;
        }
    }

    //-- End Setup ViewPager




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
