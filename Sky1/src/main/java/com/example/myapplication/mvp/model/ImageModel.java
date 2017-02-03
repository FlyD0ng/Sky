package com.example.myapplication.mvp.model;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by MJ on 2017/1/28.
 */

public interface ImageModel {
    void  loadImageList(ImageModelImpl.OnLoadImageListListener listener, SwipeRefreshLayout swipeRefreshLayout);
}
