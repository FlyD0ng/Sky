package com.example.myapplication.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AutoViewPagerAdapter;
import com.example.myapplication.adapter.NewsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {


    @BindView(R.id.auto_viewpager)
    AutoScrollViewPager mAutoViewpager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    private int[] mImageResIds;
    private ArrayList<ImageView> mImageViewList;
    private String[] mTitles;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        AutoViewPagerAdapter adapter = new AutoViewPagerAdapter(mImageViewList);
        mAutoViewpager.setAdapter(adapter);
        mAutoViewpager.startAutoScroll(1000);
        mIndicator.setViewPager(mAutoViewpager);

        NewsAdapter newsAdapter = new NewsAdapter(getChildFragmentManager(),mTitles);

        mViewpager.setAdapter(newsAdapter);
        mTablayout.setupWithViewPager(mViewpager);
    }

    @Override
    protected void initView() {
        mImageResIds = new int[]{R.drawable.b, R.drawable.e, R.drawable.c, R.drawable.d, R.drawable.a};
        mImageViewList = new ArrayList<>();
        ImageView imageView = null;
        for (int i = 0; i < mImageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(mContext);
            imageView.setBackgroundResource(mImageResIds[i]);
            mImageViewList.add(imageView);
        }

        mTitles = new String[]{"all", "Android", "休息视频", "福利", "iOS", "拓展资源", "前端", "瞎推荐"};
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_news;
    }

}
