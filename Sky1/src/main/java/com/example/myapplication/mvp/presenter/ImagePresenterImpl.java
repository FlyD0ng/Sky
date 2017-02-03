package com.example.myapplication.mvp.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.myapplication.bean.ImageBean;
import com.example.myapplication.mvp.model.ImageModelImpl;
import com.example.myapplication.mvp.view.ImgView;

import java.util.List;

/**
 * Created by MJ on 2017/2/2.
 */

public class ImagePresenterImpl implements ImagePresenter,ImageModelImpl.OnLoadImageListListener {

    private ImgView mImgView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final ImageModelImpl mImageModel;

    public ImagePresenterImpl(ImgView imgView, SwipeRefreshLayout swipeRefreshLayout){
        mImgView = imgView;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mImageModel = new ImageModelImpl();
    }

    @Override
    public void loadImageList() {
      mImgView.showProgress();
        mImageModel.loadImageList(this,mSwipeRefreshLayout);

    }


    @Override
    public void onSuccess(List<ImageBean> list) {
        mImgView.addImages(list);
        mImgView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {

        mImgView.hideProgress();
        mImgView.loadFailMsg();
    }
}
