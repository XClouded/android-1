
package com.zlping.demo.share;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zlping.demo.R;

public class OAuthWebViewActivity extends Activity {
    public static String TAG = "OAuthWebViewActivity";

    private WebView webView;

    public static final String SINA_CALLBACK_URL = "http://sina.oauth.com";

    public static final String QQ_CALLBACK_URL = "http://www.tencent.com/zh-cn/index.shtml";

    public static final String RENREN_CALLBACK_URL = "http://wap.58.com/wap.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauth_webview);
        webView = (WebView) this.findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i(TAG, "onPageStarted URL: " + url);
                handleUrl(url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handleUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (null != view.getUrl()) {
                    handler.proceed();// 接受证书
                } else {
                    handler.cancel(); // 默认的处理方式，WebView变成空白页
                }
            }

        });
        Intent it = this.getIntent();
        int type = it.getIntExtra("type", 0);
        String url = null;
        switch (type) {
            case 1:
                url = "https://api.weibo.com/oauth2/authorize?client_id=1570408684&response_type=token&display=mobile&redirect_uri=" + URLEncoder.encode(SINA_CALLBACK_URL);
                ;
                break;
            case 2:
                url = "https://open.t.qq.com/cgi-bin/oauth2/authorize?client_id=801115505&response_type=token&redirect_uri=" + URLEncoder.encode(QQ_CALLBACK_URL);
                break;
            case 3:
                url = "https://graph.renren.com/oauth/authorize?display=touch&scope=publish_feed+create_album+photo_upload+read_user_album+status_update&response_type=token&client_id=6b1016db20c540e78bd1b20be4c707a3&redirect_uri=http%3A%2F%2Fgraph.renren.com%2Foauth%2Flogin_success.html";
                break;
        }
        if (url != null) {
            webView.loadUrl(url);
        } else {
            finish();
        }
    }

    private void handleUrl(String url) {
        Log.i(TAG, "url=" + url);
        if (url.startsWith(SINA_CALLBACK_URL)) {
            handleRedirectUrl(url, 1);
        } else if (url.startsWith(QQ_CALLBACK_URL)) {
            handleRedirectUrl(url, 2);
        } else if (url.startsWith(RENREN_CALLBACK_URL)) {
            handleRedirectUrl(url, 3);
        }
    }

    private void handleRedirectUrl(String url, int type) {
        Bundle values = parseUrl(url);
        String error = values.getString("error");
        String error_code = values.getString("error_code");
        String access_token = values.getString("access_token");
        Log.i(TAG, "tomken = " + access_token);
        if (error == null && error_code == null) {
            SharedPreferences settings = getSharedPreferences("zlpingdemo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("sina_access_token", access_token);
            editor.commit();
            
            Intent it = new Intent();
            it.setClass(getApplicationContext(), ShareInfoActivity.class);
            it.putExtra("mAccessToken", access_token);
            startActivity(it);
        } else if (error.equals("access_denied")) {
            // 用户或授权服务器拒绝授予数据访问权限
        } else {

        }

        finish();
    }

    public static Bundle parseUrl(String url) {
        url = url.replace("weiboconnect", "http");
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                params.putString(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
            }
        }
        return params;
    }
}
