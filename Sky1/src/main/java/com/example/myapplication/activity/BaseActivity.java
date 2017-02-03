package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.utils.Utils;
import com.example.myapplication.utils.ActivityCollector;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(setLayoutResourceId());
        ButterKnife.bind(this);//绑定butterKnife
        Utils.init(this);
        initView();
        iniData();
        initListener();
//管理activity用于退出这个页面.在父类的activity做管理逻辑.把activity添加到集合管理.
//        AppManager.getAppManager().addActivity(this);
        ActivityCollector.addActivity(this);
    }

    protected abstract void initListener();

    protected abstract void iniData();


    protected abstract int setLayoutResourceId();
    protected abstract void  initView();

    protected <T extends View> T  IfindViewById(int id){
        return (T) findViewById(id);
    }
    protected  void launchActivity(Class<?> clazz){

        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
    //启动这个activity的时候接受一个bundle对象.这个bundle对象里面可以存储这个数据.
    protected  void launchActivityWithBundle(Class<?> clazz,Bundle bundle){

        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
//销毁的时候把这个集合清空.
    @Override
    protected void onDestroy() {
        super.onDestroy();

      ActivityCollector.removeActivity(this);
    }

    protected  void  closeApp(){
        ActivityCollector.finishAll();
    }
}
