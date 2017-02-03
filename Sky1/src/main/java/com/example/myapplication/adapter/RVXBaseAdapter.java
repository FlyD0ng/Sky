package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.example.myapplication.utils.HttpUtil;

import java.util.List;

/**
 * Created by MJ on 2017/2/2.
 */

public abstract class RVXBaseAdapter<T> extends RecyclerView.Adapter<RVXBaseAdapter.ViewHolder> {
    protected int mLastAnimatedPosition = -1;
    protected boolean mAnimateItems;
    protected List<T> mBeans;
    protected Context mContext;

    public RVXBaseAdapter(List<T> beans, Context context){

        mBeans = beans;
        mContext = context;
    }
    @Override
    public RVXBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(setLayoutResourceId(viewType), parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    protected abstract int setLayoutResourceId(int  viewType);

    @Override
    public void onBindViewHolder(final RVXBaseAdapter.ViewHolder holder, final int position) {
        runEnterAnimation(holder.mItemView,position);
        onBindData(holder,mBeans.get(position));


        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(holder.getAdapterPosition()  );

            }
        });
    }

    protected abstract void onBindData(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mBeans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SparseArray<View> mMViews;
        private View mItemView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mMViews = new SparseArray<>();

        }
        public <T extends View> T getView(int viewId){
            View view = mMViews.get(viewId);
            if (view == null){
                view = mItemView.findViewById(viewId);
                mMViews.put(viewId,view);
            }

            return (T) view;
        }

        public  void  setText(int  viewId,String text){
            TextView tv = getView(viewId);
            tv.setText(text);
        }
        public  void  setImage(int  viewId,int resId){
            ImageView tv = getView(viewId);
            tv.setBackgroundResource(resId);
        }

        public  void  setImageFromInternet(int  viewId,String url){
            ImageView tv = getView(viewId);
            HttpUtil.getInstance().loadImage(url,tv);
        }

    }

    protected void onItemClick(int adapterPosition) {


    }

    public  void  add(T bean){
        mBeans.add(bean);
    }


    public void addAll(List<T> list) {//这个是解析的json数据
        mBeans.addAll(list);


        notifyDataSetChanged();
    }

    public void  clear(){
        mBeans.clear();
        notifyDataSetChanged();
    }
    private void runEnterAnimation(View itemView, int position) {
        if(!mAnimateItems){
            return;
        }
        if(position > mLastAnimatedPosition){
            mLastAnimatedPosition = position;
            itemView.setTranslationY(ScreenUtils.getScreenHeight());
            itemView.animate()
                    .alpha(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.0f))
                    .setDuration(300).start();
        }
    }
}
