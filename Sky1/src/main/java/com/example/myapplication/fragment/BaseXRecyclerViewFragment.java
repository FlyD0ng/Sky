package com.example.myapplication.fragment;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.adapter.RVXBaseAdapter;
import com.example.myapplication.utils.FileUtils;
import com.example.myapplication.utils.HttpUtil;
import com.example.myapplication.utils.Tools;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.File;
import java.util.List;

import butterknife.BindView;


/**
 * 当这个类里面使用泛型的时候这个类名后面一定需要使用泛型.
 */
public abstract class BaseXRecyclerViewFragment<T> extends BaseFragment {

    public static final int ACTION_REFRESH = 1;
    public static final int ACTION_LOAD_MORE = 2;
    private int mCurrentState = ACTION_REFRESH;

    @BindView(R.id.xRecyclerView)
    XRecyclerView mXRecyclerView;
    @BindView(R.id.btn_reload)
    Button mBtnReload;
    @BindView(R.id.ll_reload_wrap)
    LinearLayout mLlReloadWrap;
    private RVXBaseAdapter<T> mAdapter;
    private int mCurrentPageIndex = 1;

    protected abstract RVXBaseAdapter<T> setAdapter();

    protected abstract RecyclerView.LayoutManager setLayoutManager();

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_base_xrecycler_view;
    }

    @Override
    protected void initView() {

        mXRecyclerView.setLayoutManager(setLayoutManager());
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mAdapter = setAdapter();
    }

    @Override
    protected void initData() {


        mXRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                swiActionloadData(ACTION_REFRESH);
            }

            @Override
            public void onLoadMore() {
                swiActionloadData(ACTION_LOAD_MORE);
            }
        });
        mXRecyclerView.setRefreshing(true);
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlReloadWrap.setVisibility(View.GONE);
                mXRecyclerView.setRefreshing(true);
            }
        });

    }

    private void swiActionloadData(int actionRefresh) {

        mCurrentState = actionRefresh;
        switch (mCurrentState) {
            case ACTION_REFRESH:
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }

        loadData();
    }

    private void loadData() {

        String url = getUrl(mCurrentPageIndex);
        if (!NetworkUtils.isConnected()) {
            LogUtils.d("没有网络.....+++++++++++++++++++++++");
            String result = readCacheData(getUrl(1));
            parseData(result);
        }else {
            LogUtils.d("有没有网络.....+++++++++++++++++++++++");
            HttpUtil.getInstance().loadString(url, new HttpUtil.HttpCallBack() {
                @Override
                public void onLoading() {

                }

                @Override
                public void onSuccess(String result) {

                    LogUtils.d("onSuccesss+*****************************");
                    if (mCurrentState == ACTION_REFRESH){
                        storeCacheData(getUrl(1),result);
                    }

                    parseData(result);
                }

                @Override
                public void onError(Exception e) {

                    loadError();
                }
            });
        }
    }

    private void storeCacheData(String url, String result) {
        try {
            FileUtils.writeFile(getCacheDir(url),result,"UTF-8",true);
        } catch (Exception e) {


        }
    }

    private void parseData(String result) {
        if (!TextUtils.isEmpty(result)){
            List<T> list = parseJsonData(result);
            mAdapter.addAll(list);
            loadComplete();
            mLlReloadWrap.setVisibility(View.GONE);
        }else {
            loadError();
        }
    }

    private void loadError() {
        mLlReloadWrap.setVisibility(View.VISIBLE);
        loadComplete();
    }

    private void loadComplete() {
        if (mCurrentState == ACTION_REFRESH){
            mXRecyclerView.refreshComplete();
        }else if (mCurrentState == ACTION_LOAD_MORE){
            mXRecyclerView.loadMoreComplete();
        }
    }

    protected abstract List<T> parseJsonData(String result);


    private String readCacheData(String url) {

        String result = null;
        try {
            result = FileUtils.readFile(getCacheDir(url), "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getCacheDir(String url) {

        return FileUtils.getCacheDir(mContext)+ File.separator+"offline_gan_huo_cache1"+
                File.separator+ Tools.md5(url);
    }

    protected abstract String getUrl(int currentPageIndex);


}
