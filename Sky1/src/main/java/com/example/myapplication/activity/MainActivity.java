package com.example.myapplication.activity;

import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.myapplication.R;
import com.example.myapplication.fragment.AboutFragment;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.mvp.view.FunnyFragment;
import com.example.myapplication.fragment.NewsFragment;
import com.example.myapplication.utils.ActivityCollector;
import com.example.myapplication.utils.DoubleClickExtHelper;
import com.example.myapplication.utils.FragmentManagerUtil;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    private FragmentManager mFragmentManager;
    private DoubleClickExtHelper mDoubleClickExtHelper;
    private BaseFragment mDefaultFragment;




    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mFragmentManager = getSupportFragmentManager();
        mDoubleClickExtHelper = new DoubleClickExtHelper(this);

    }
    @Override
    protected void iniData() {
        initBottomNavigationBar();
        initDefalueFragment();
    }
    @Override
    protected void initListener() {
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        switchFragment(NewsFragment.class);
                        break;
                    case 1:
                        switchFragment(FunnyFragment.class);
                        break;
                    case 2:
                        switchFragment(AboutFragment.class);
                        break;
                    case 3:
                        ActivityCollector.finishAll();
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    private void switchFragment(Class<?> clazz) {
        BaseFragment fragment = FragmentManagerUtil.createFragment(clazz);
        if (fragment.isAdded()){
            mFragmentManager.beginTransaction().hide(mDefaultFragment).show(fragment).commitAllowingStateLoss();
        }else {
            mFragmentManager.beginTransaction().hide(mDefaultFragment).add(R.id.frame_layout,fragment).commitAllowingStateLoss();
        }
        mDefaultFragment = fragment;//把这个当前的fragment赋值给默认的

    }

    private void initDefalueFragment() {
        mDefaultFragment = FragmentManagerUtil.createFragment(NewsFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_layout,mDefaultFragment).commit();
    }

    private void initBottomNavigationBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_news_24dp, "新闻").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_photo_24dp, "搞笑").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_video_24dp, "关于").setActiveColorResource(R.color.red))
                .addItem(new BottomNavigationItem(R.mipmap.ic_about_me, "退出").setActiveColorResource(R.color.red));
                mBottomNavigationBar.setFirstSelectedPosition(0).initialise();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){

            return  mDoubleClickExtHelper.onKeyDwon(keyCode,event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
