package com.example.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Process;

import java.util.Stack;

/**
 * Created by MJ on 2017/1/27.
 */
public class AppManager {

    private static Stack<Activity>  activityStack;
    private static  AppManager instance;
    //私有化构造函数,申明本类的成员.
    private AppManager(){

    }
    /**
     * 单一实例
     */
    public static  AppManager getAppManager(){

        if (instance == null){

            synchronized (AppManager.class){
                if (instance == null){
                    instance = new AppManager();
                }
            }

        }

        return  instance;
    }

    /**
     * 添加到堆栈
     */
    public  void  addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<>();
        }

        activityStack.add(activity);
    }
    /**
     * 获取当前Activity(堆栈中最后一个压入的)
     */
    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return  activity;
    }

    /**
     * 结束当前的Activity(堆栈中最后一个压入的
     */
    public  void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的activity
     * @param activity
     */
    private void finishActivity(Activity activity) {
        if(activity != null && !activity.isFinishing()){
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void  finishActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            finishActivity(activity);
            break;
        }
    }

    /**
     * 结束所有的Activity
     */
    public void  finishAllActivity(){
        for (int i = 0; i < activityStack.size(); i++) {
            if(null != activityStack.get(i)){
                finishActivity(activityStack.get(i));
            }

        }

        activityStack.clear();
    }

    /**
     * 获取指定的Activity
     */
    public  static  Activity getActivity(Class<?> cls){
        if(activityStack != null){
            for (Activity activity : activityStack) {
                if(activity.getClass().equals(cls)){

                    return activity;
                }
            }
        }
        return  null;
    }

    public static Stack<Activity>  getActivitys(){
        return activityStack;
    }

    /**
     * 退出应用程序
     */
    public void  AppExit(Context context){
     finishAllActivity();
        FragmentManagerUtil.clearFragmentList();
        Process.killProcess(Process.myPid());

        System.exit(0);
    }

    /**
     * 返回当前Activity栈中的Activity的数量
     */
    public int  getActivityCount(){
        int count = activityStack.size();

        return count;
    }
    /**
     * 堆栈中移除Activity
     */
    public  void  removeActivity(Activity activity){
        if(activityStack == null){
            return;
        }else if (activityStack.contains(activity)){
            activityStack.remove(activity);
        }

        if(activity != null && !activity.isFinishing()){
            activity.finish();;
            activity = null;
        }

    }

}
