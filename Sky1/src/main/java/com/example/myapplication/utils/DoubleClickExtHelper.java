package com.example.myapplication.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by MJ on 2017/1/27.
 */
public class DoubleClickExtHelper {
    private Activity mActivity;
    private final Handler mHandler;
    private boolean isOnKeyBacking;
    private Toast backToast;

    public DoubleClickExtHelper(Activity activity) {

        mActivity = activity;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public  boolean  onKeyDwon(int  keyCode, KeyEvent event){
        if (keyCode != KeyEvent.KEYCODE_BACK){//按键码.
            return false;
        }
        if (isOnKeyBacking){
            mHandler.removeCallbacks(onBackTimeRunnable);//删除所有的消息.
            if (backToast != null){
                backToast.cancel();
            }

            ActivityCollector.finishAll();
            return true;
        }else {
            isOnKeyBacking = true;
            if (backToast == null){
                backToast = Toast.makeText(mActivity,"再按一次退出",Toast.LENGTH_SHORT);
            }

            backToast.show();
            mHandler.postDelayed(onBackTimeRunnable,2000);
            return true;

        }
    }

    private  Runnable onBackTimeRunnable = new Runnable() {
        @Override
        public void run() {
            isOnKeyBacking = false;
            if (backToast != null){
                backToast.cancel();
            }
        }
    };
}
