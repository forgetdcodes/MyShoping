package com.example.myshop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.myshop.Http.UrlContent;
import com.example.myshop.Utils.cartProvider;
import com.example.myshop.bean.Wares;
import com.example.myshop.weidget.MyToolBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.Serializable;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

/**
 * Created by 刘博良 on 2017/5/16.
 */

public class WareDetailActivity extends BaseActivity implements MyToolBar.OnClickListener {

    @ViewInject(R.id.myToolBar_detail_activity)
    private MyToolBar myToolBar;

    @ViewInject(R.id.webView)
    private WebView webView;

    private Wares wares;
    private cartProvider provider;
    private WebInterface webInterface;
    private SpotsDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waredetial);
        ViewUtils.inject(this);

        Serializable serializable =getIntent().getSerializableExtra("Wares");
        if (serializable ==null)
            this.finish();
        wares = (Wares) serializable;
        provider =new cartProvider(this);
        dialog =new SpotsDialog(this,"小黑正在给你拼命加载~~");
        dialog.show();

        initMyToolBar();
        initWebView();


    }

    private void initMyToolBar() {

        myToolBar.setNavigationOnClickListener(this);
        myToolBar.getmButton().setVisibility(View.VISIBLE);
        myToolBar.getmButton().setText("分享");
        myToolBar.getmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showShare();
            }
        });
    }

    private void showShare() {
        ShareSDK.initSDK(this);

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("小黑的商城");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(wares.getImgUrl());
//        oks.setImageUrl(wares.getImgUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(wares.getName());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(wares.getImgUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(wares.getName());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShareSDK.stopSDK(this);
    }

    private void initWebView() {
        WebSettings webSet =webView.getSettings();
        webSet.setJavaScriptEnabled(true);
        webSet.setBlockNetworkImage(false);
        webSet.setAppCacheEnabled(true);

        webView.loadUrl(UrlContent.WARES_DETAIL);

        webInterface =new WebInterface(this);
        webView.addJavascriptInterface(webInterface,"appInterface");
        webView.setWebViewClient(new WebClient());
    }

    class WebClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (dialog !=null &&dialog.isShowing())
                dialog.dismiss();
            webInterface.showDetail();
        }
    }

    class WebInterface{
        private Context context;
        public WebInterface(Context context){
            this.context =context;
        }

        @JavascriptInterface
        public void showDetail(){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showDetail("+wares.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id){

            provider.put(wares);
            Toast.makeText(context,"以添加到购物车",Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void addFavorites(){

        }
    }

    @Override
    public void onClick(View v) {

        this.finish();
    }
}
