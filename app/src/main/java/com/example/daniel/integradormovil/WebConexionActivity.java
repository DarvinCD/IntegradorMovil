package com.example.daniel.integradormovil;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.common.api.Api;

public class WebConexionActivity extends AppCompatActivity {

    String url = "http://www.google.com";
    private FloatingActionButton fConexionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_conexion);


        getSupportActionBar().hide();

        WebView web = (WebView) findViewById(R.id.miVisor);
        web.setWebViewClient(new MyWebViewClient());

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.loadUrl(url);


        fConexionBtn = (FloatingActionButton) findViewById(R.id.fab_conexion);


        fConexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WebConexionActivity.this,NavegationActivity.class));
            }
        });

    }




    private class MyWebViewClient  extends WebViewClient
    {
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }
}
