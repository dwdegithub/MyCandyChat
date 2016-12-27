package com.candychat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.candychat.R;

/**
 * Created by ZN_mager on 2016/5/27.
 */
public class WebViewActivity extends BaseActivity {

    public static final String PARAM_KEY_TITLE = "title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initViews();
    }

    private void initViews() {
        String title = getIntent().getStringExtra(PARAM_KEY_TITLE);
        String url = getIntent().getDataString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.selector_home_as_upindicator));
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(title);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
