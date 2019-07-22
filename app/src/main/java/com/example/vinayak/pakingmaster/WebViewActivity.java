package com.example.vinayak.pakingmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String data = "http://packing.vishwanet.in/web/displaySlipsByUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getSupportActionBar().setTitle("Packing Web Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assignView();
        webView.loadUrl(data);
    }

    private void assignView(){
        webView = (WebView)findViewById(R.id.webView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
