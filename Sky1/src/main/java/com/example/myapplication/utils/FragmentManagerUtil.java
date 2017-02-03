package com.example.myapplication.utils;


import com.example.myapplication.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MJ on 2017/1/27.
 * map集合存储的是键值对.我们通过这个存储的键获取这个值.
 */
public class FragmentManagerUtil {
    private static Map<String,BaseFragment> fragmentlist = new HashMap<>();
    private BaseFragment baseFragment;


    public static  BaseFragment createFragment(Class<?> claszz, boolean  isAddList){
        BaseFragment baseFragment = null;
        String className = claszz.getName();

        if (fragmentlist.containsKey(className)){

            baseFragment = fragmentlist.get(className);
        }else {

            try {//通过Class对象的forName.newInstance返回这个类的实例对象.
                baseFragment = (BaseFragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (isAddList){
            fragmentlist.put(className,baseFragment);
        }

        return baseFragment;
    }

    public  static  BaseFragment createFragment(Class<?> clazz){

        return createFragment(clazz,true);
    }

    public  static  void  clearFragmentList(){
        fragmentlist.clear();
    }
}
