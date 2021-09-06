package com.example.getnews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAadapter extends RecyclerView.Adapter<NewsRVAadapter.ViewHolder> {
    private ArrayList<Articles> articles;
    private Context context;

    public NewsRVAadapter(ArrayList<Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_items, parent, false);
        return new NewsRVAadapter.ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull NewsRVAadapter.ViewHolder holder, int position) {
        Articles modal = articles.get(position);
        holder.subTitleTV.setText(modal.getDescription());
        holder.titleTV.setText(modal.getTitle());
       // Picasso.get().load(modal.getUrlToImage()).into(holder.newsIV);
        //Picasso.with(context).load(modal.getUrlToImage()).into(holder.newsIV);
        Glide.with(context).load(modal.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,NewsDetailsActivity.class);
                i.putExtra("title",modal.getTitle());
                i.putExtra("desc",modal.getDescription());
                i.putExtra("content",modal.getContent());
                i.putExtra("url",modal.getUrl());
                i.putExtra("image",modal.getUrlToImage());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTV, subTitleTV;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.idTVHeading);
            subTitleTV = itemView.findViewById(R.id.idTVSubTitle);
            newsIV = itemView.findViewById(R.id.idIVNews);

        }
    }
}

