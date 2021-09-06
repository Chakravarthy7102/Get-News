package com.example.getnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    String title, image, desc, content, url;
    private TextView newsTitleTV, newsSubDescTV, newsContentTV;
    private ImageView newsIV;
    private Button newsDetailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        title = getIntent().getStringExtra("title");
        image = getIntent().getStringExtra("image");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        if(url.isEmpty() || url.equals("null")){
            newsDetailBtn.setVisibility(View.GONE);
        }
        newsTitleTV = findViewById(R.id.idTVNewsTitle);
        newsSubDescTV = findViewById(R.id.idTVSubDesc);
        newsContentTV = findViewById(R.id.idTVContent);
        newsIV = findViewById(R.id.idIVNews);
        newsDetailBtn = findViewById(R.id.idBtnReadFullNews);
        newsTitleTV.setText(title);
        newsSubDescTV.setText(desc);
        newsContentTV.setText(content);
        //Picasso.with(this).load(image).into(newsIV);
        Glide.with(this).load(image).into(newsIV);
        //Picasso.with(getApplicationContext()).load(image).into(newsIV);
        newsDetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}