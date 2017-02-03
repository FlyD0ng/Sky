package com.example.myapplication.mvp.model;

import android.support.v4.widget.SwipeRefreshLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.myapplication.bean.ImageBean;
import com.example.myapplication.utils.Apis;
import com.example.myapplication.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MJ on 2017/1/28.
 */

public class ImageModelImpl implements ImageModel {
    @Override
    public void loadImageList(final OnLoadImageListListener listener, final SwipeRefreshLayout swipeRefreshLayout) {
        String url = Apis.IMAGES_URL;

        HttpUtil.getInstance().loadString(url, new HttpUtil.HttpCallBack() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onSuccess(String result) {
                ArrayList<ImageBean> list = new ArrayList<>();

                try {
                    list = new Gson().fromJson(result, new TypeToken<List<ImageBean>>() {
                    }.getType());

                    listener.onSuccess(list);
                } catch (JsonSyntaxException e) {
                }
            }

            @Override
            public void onError(Exception e) {

                //如果失败我们需要这下拉刷新关闭掉。弹出提示。
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showShortToast("网络不可用");
            }
        });



    }

    //定义这个接口回调.
    public interface OnLoadImageListListener {
        void  onSuccess(List<ImageBean> list);

        void  onFailure(String msg, Exception e);
    }
}
