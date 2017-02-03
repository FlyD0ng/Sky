package com.example.myapplication.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.myapplication.R;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    public static final String URL = "webViewUrl";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.webView)
    WebView mWebView;
    private String mUrl;

    @Override
    protected void initListener() {

    }

    @Override
    protected void iniData() {

    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_web_view;
    }



    protected void initToolbar() {
        mToolbar = IfindViewById(R.id.toolbar);
        if (mToolbar != null) {
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            //我们可以自己制定这个toolbar的颜色.
//            mToolbar.setTitleTextAppearance(this, R.style.ToolBarTitleTextApperance);
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    protected void initView() {
        initWebViewSetting();
        mUrl = getIntent().getStringExtra(URL);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mProgressbar.setMax(100);
        mWebView.loadUrl(mUrl);

        mToolbar.setTitle("加载中....");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (canGoBanck()) goBack();
        else finish();
    }

    private void goBack() {
        if (mWebView != null){
            mWebView.goBack();
        }
    }

    private boolean canGoBanck() {
        return mWebView != null  && mWebView.canGoBack();
    }

    private void initWebViewSetting() {
        WebSettings mWebViewSettings = mWebView.getSettings();

        mWebView.requestFocusFromTouch();//支持获取手势焦点,输入用户名,密码或者其他
        mWebViewSettings.setJavaScriptEnabled(true);
        mWebViewSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebViewSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小.

        mWebViewSettings.setSupportZoom(true);//支持缩放.默认为true.是下面那个的前提
        mWebViewSettings.setBuiltInZoomControls(true);  //设置内置的缩放空寂爱你.
        //若上面是false,则该webView不可以缩放,这个不管设置什么都不能缩放.

        mWebViewSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件

        mWebViewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        mWebViewSettings.supportMultipleWindows();//多窗口
        mWebViewSettings.setAllowFileAccess(true);//设置可以访问文件.
        mWebViewSettings.setNeedInitialFocus(true);//当webview调用requestFoucus时为webview设置节点.
        mWebViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新窗口
        mWebViewSettings.setLoadsImagesAutomatically(true);
        mWebViewSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.setBackgroundColor(getResources().getColor(R.color.primary));
    }


    //WebViewClient就是帮助WebView处理各种通知、请求事件的。
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }
    }
    //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }
        //获取Web页中的title用来设置自己界面中的title
        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
        //因此建议当触发onReceiveError时，不要使用获取到的title
        @Override
        public void onReceivedTitle(WebView view, String title) {

            mToolbar.setTitle(title);
        }
    }
}
