package com.blackdog.studentmanager;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.blackdog.studentmanager.module.me.MeFragment;
import com.blackdog.studentmanager.module.named.NameFragment;
import com.blackdog.studentmanager.module.student.StudentFragment;
import com.blackdog.studentmanager.util.ToastUtils;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private StudentFragment mUserManagerFragment;
    private NameFragment mNamedFragment;
    private MeFragment mMeFragment;
    private Fragment mCurrentFragment;
    private long mLastBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化变量
        initValues();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //切换第一个Fragment
        if(mUserManagerFragment == null){
            mUserManagerFragment = new StudentFragment();
        }
        mCurrentFragment = new Fragment();
        switchContent(mCurrentFragment,mUserManagerFragment);
        navigationView.setCheckedItem(R.id.nav_usermanager);
    }

    /**
     * 初始化变量
     */
    private void initValues() {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis() - mLastBackTime < 2000){
                super.onBackPressed();
            }else{
                mLastBackTime = System.currentTimeMillis();
                ToastUtils.showToast("再点击一次退出程序");
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();
        switch (id){
            case R.id.nav_usermanager:
                if(mUserManagerFragment == null){
                    mUserManagerFragment = new StudentFragment();
                }
                switchContent(mCurrentFragment,mUserManagerFragment);
                break;
            case R.id.nav_named:
                if(mNamedFragment == null){
                    mNamedFragment = new NameFragment();
                }
                switchContent(mCurrentFragment,mNamedFragment);

                break;
            case R.id.nav_me:
                if(mMeFragment == null){
                    mMeFragment = new MeFragment();
                }
                switchContent(mCurrentFragment,mMeFragment);

                break;
        }


        return true;
    }

    /**
     * 切换fragment
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            if (!to.isAdded()) {
                getSupportFragmentManager().beginTransaction().hide(from)
                        .add(R.id.content, to).show(to).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(from).show(to).commit();
            }
        }
    }
}
