package com.example.vinayak.pakingmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String data = "<html><body><h1>Hello, Google!</h1></body></html>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        assignView();
        webView.loadData(data, "text/html", "UTF-8");
    }

    private void assignView(){
        webView = (WebView)findViewById(R.id.webView);
    }
}
