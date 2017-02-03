package com.example.myapplication.mvp.view;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.bean.ImageBean;
import com.example.myapplication.fragment.BaseFragment;
import com.example.myapplication.mvp.ImageAdapter;
import com.example.myapplication.mvp.presenter.ImagePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FunnyFragment extends BaseFragment implements ImgView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;
    private ImagePresenterImpl mImagePresenter;
    private LinearLayoutManager mLayoutManager;
    private ImageAdapter mAdapter;
    private ArrayList<ImageBean> mData;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_funny;
    }


    @Override
    protected void initView() {
        //绑定presenter.
        mImagePresenter = new ImagePresenterImpl(this, mSwipeRefreshWidget);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.primary,R.color.primary_dark,R.color.primary_light
                ,R.color.accent);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ImageAdapter(mContext );



    }
    @Override
    protected void initData() {
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();//手动下拉刷新一次数据.

    }
    @Override
    protected void initListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mLastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
              if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                      mLastVisibleItem +1 == mAdapter.getItemCount()){
                  Snackbar.make(mRecyclerView,"加载完毕了...",Snackbar.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            }
        });
    }







    @Override
    public void addImages(List<ImageBean> list) {//list就是我们这个modelimpl下载的数据.

        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.addAll(list);

        mAdapter.setmDate(mData);
    }

    @Override
    public void showProgress() {

        mSwipeRefreshWidget.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshWidget.setRefreshing(false);
    }

    @Override
    public void loadFailMsg() {

        Snackbar.make(mRecyclerView,"加载失败,检查网络",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
//recyclerview下拉刷新数据.....

        if (mData != null){
            mData.clear();//刷新数据的时候先清空这个集合不然会重复
        }
        mImagePresenter.loadImageList();//交给presenter去刷新数据.



    }
}
