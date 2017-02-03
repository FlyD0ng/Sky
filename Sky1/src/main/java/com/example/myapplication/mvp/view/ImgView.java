package com.example.myapplication.mvp.view;

import com.example.myapplication.bean.ImageBean;

import java.util.List;

/**
 * Created by MJ on 2017/2/2.
 */

public interface ImgView {
    void  addImages(List<ImageBean>  list);
    void  showProgress();
    void hideProgress();
    void  loadFailMsg();
}
