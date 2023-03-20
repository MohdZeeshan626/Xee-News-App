package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsDeatileActivity extends AppCompatActivity {
   private TextView titleTv,subDescTv,contentTv;
   private ImageView idIVNews;
   private Button readBtn;
    String title,desc,content,imageURL,url;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_deatile);



        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("imageURL");
        url=getIntent().getStringExtra("url");
        titleTv=findViewById(R.id.idTVTitle);
        subDescTv=findViewById(R.id.idTVSubDescription);
        contentTv=findViewById(R.id.idTVContent);
        idIVNews=findViewById(R.id.idIVNews2);
        readBtn=findViewById(R.id.idReadBtn);

        webView=findViewById(R.id.web);

        titleTv.setText(title);
        subDescTv.setText(desc);
        contentTv.setText(content);
        Picasso.get().load(imageURL).into(idIVNews);

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // loading http://www.google.com url in the WebView.
                webView.loadUrl(url);

                // this will enable the javascript.
                webView.getSettings().setJavaScriptEnabled(true);

                // WebViewClient allows you to handle
                // onPageFinished and override Url loading.
                webView.setWebViewClient(new WebViewClient());
                readBtn.setVisibility( View.GONE );
            }
        });



    }
}