package com.zlping.demo.webproxy;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.zlping.demo.R;

public class WebViewActivity2 extends Activity {
    private String url = "http://10.59.8.48:8080/examples/login1.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        WebView web = (WebView)this.findViewById(R.id.webView);
        web.setEnabled(true);
        web.loadUrl(url);
    }
}
