package com.apps.dcodertech.coursesurfer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apps.dcodertech.coursesurfer.data.courseDB;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.net.MalformedURLException;
import java.net.URL;

public class webView extends AppCompatActivity {
    ProgressBar progressBar;
    WebView browser;
    courseDB courseDB;
    Courses courses;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    FloatingActionButton fab;


    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseDB=new courseDB(getApplicationContext());
        setContentView(R.layout.activity_web_view);
        progressBar=findViewById(R.id.progressBar1);
        browser=findViewById(R.id.webView);
        fab=findViewById(R.id.browButton);
        progressBar=new ProgressBar(getApplicationContext());
        browser.setWebViewClient(new myWebClient());
        mToolbar=findViewById(R.id.toolbar);
        mToolbar.setTitle("Course Surfer");
        Bundle bundle=getIntent().getExtras();
        courses= (Courses) bundle.get("method");
        final String url=bundle.getString("webLink");
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = browser.getSettings();
        webSettings.setSupportMultipleWindows(true);
        fab.setImageResource(R.drawable.brow);
        browser.getProgress();
        browser.loadUrl(url);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        try{
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        }catch (Exception e){ }

    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    // To handle "Back" key press event for WebView to go back to previous screen.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
          case R.id.bookmarkWeb:
            bookActivity();
           return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void bookActivity(){
        courseDB.insert(courses);
        StyleableToast.makeText(getApplicationContext(),"Bookmarked!",R.style.mytoast).show();
        courseDB.close();
    }
}
