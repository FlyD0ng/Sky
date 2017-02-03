package com.example.myapplication.mvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.ImageBean;
import com.example.myapplication.utils.HttpUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MJ on 2017/2/2.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {


    private OnItemClickListener mOnItemClickListener;
    private ArrayList<ImageBean> mData;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private final Context mContext;

    public ImageAdapter(Context context) {

        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageBean imageBean = mData.get(position);
        if (imageBean == null) {
            return;
        }
        holder.mTvTitle.setText(imageBean.title);
        HttpUtil.getInstance().loadImage(imageBean.thumburl, holder.mIvImage, true);
    }


    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.size();
    }

    public void setmDate(ArrayList<ImageBean> data) {

        mData = data;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;
        @BindView(R.id.ivImage)
        ImageView mIvImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //定义这个接口回调把这个点击的view和这个getAdapterPosition传递出去.
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }
}
