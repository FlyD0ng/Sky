package com.example.myapplication.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 */
public abstract class BaseFragment extends Fragment {


    private View mContentView;
    protected Context mContext;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceId(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, mContentView);
        initView();
        initData();
        initListener();
        return mContentView;
    }
    protected abstract void initListener();
    protected abstract void initData();
    protected abstract void initView();

    protected abstract int setLayoutResourceId();
    protected View getContentView(){
        return mContentView;
    }

    protected  ProgressDialog getProgressDialog(){
        return mProgressDialog;
    }
    protected void startProgressDialog(){

        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    protected  void  stopProgressDialog(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

}
