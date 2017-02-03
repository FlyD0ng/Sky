package com.example.myapplication.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.blankj.utilcode.utils.LogUtils;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.fragment.NewsListFragment;
import com.example.myapplication.utils.FragmentManagerUtil;

/**
 * Created by MJ on 2017/2/2.
 */
public class NewsAdapter extends FragmentStatePagerAdapter {
    public static final String TYPE = "type";
    private String[] mTitles;

    public NewsAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    //这个是viewpager的数据返回的一个fragment
    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = FragmentManagerUtil.createFragment(NewsListFragment.class,false);
        Bundle bundle = new Bundle();//创建fragment的时候,携带数据
        //通过当前的条目的position返回这个tab的title
        bundle.putString(TYPE,mTitles[position]);
        fragment.setArguments(bundle);

        LogUtils.d("创建fragment多少个");
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
