package com.example.myapplication.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.example.myapplication.R;
import com.example.myapplication.activity.WebViewActivity;
import com.example.myapplication.adapter.NewsAdapter;
import com.example.myapplication.adapter.RVXBaseAdapter;
import com.example.myapplication.bean.GanHuoBean;
import com.example.myapplication.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.utils.Apis.GanHuo;

/**
 * Created by MJ on 2017/2/2.
 */
public class NewsListFragment extends BaseXRecyclerViewFragment {

    private RVXBaseAdapter<GanHuoBean> mRvxBaseAdapter;
    private String mType;

    @Override
    protected RVXBaseAdapter setAdapter() {

        mRvxBaseAdapter = new RVXBaseAdapter<GanHuoBean>(new ArrayList<GanHuoBean>(),mContext) {
            @Override
            protected int setLayoutResourceId(int viewType) {
                return R.layout.item_ganhuo;
            }

            @Override
            protected void onBindData(ViewHolder holder, GanHuoBean bean) {

                holder.getView(R.id.tv_desc).setVisibility(View.GONE);
                holder.getView(R.id.iv_img).setVisibility(View.GONE);
                holder.getView(R.id.fl_head_date_wrap).setVisibility(View.GONE);

                if (bean.url.endsWith(".jpg")){
                    holder.getView(R.id.iv_img).setVisibility(View.VISIBLE);
                    ImageView imageView = holder.getView(R.id.iv_img);
                    HttpUtil.getInstance().loadImage(bean.url,imageView,true);
                }else {
                    holder.getView(R.id.tv_desc).setVisibility(View.VISIBLE);
                    holder.setText(R.id.tv_desc,bean.desc);
                }

                holder.setText(R.id.tv_source,bean.source);
                holder.setText(R.id.tv_people,bean.who);
                holder.setText(R.id.tv_time,bean.publishedAt.substring(0,10));
                holder.setText(R.id.tv_tag,bean.type);

            }

            @Override
            protected void onItemClick(int position) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL,mBeans.get(position -1).url);
                mContext.startActivity(intent);
            }
        };


        return mRvxBaseAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager setLayoutManager() {

        return new LinearLayoutManager(mContext);
    }

    @Override
    protected List parseJsonData(String result) {

        JSONObject jsonObject = null;
        List<GanHuoBean> list = null;
        try {
            jsonObject = new JSONObject(result);

            list = new Gson().fromJson(jsonObject.getString("results"), new TypeToken<List<GanHuoBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    protected String getUrl(int currentPageIndex) {

        mType = getArguments().getString(NewsAdapter.TYPE);
        String url = GanHuo+"/"+mType+"/10/"+currentPageIndex;
       LogUtils.d("访问的路径:   "+url);

        return url;
    }
}
