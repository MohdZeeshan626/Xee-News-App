package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {
    
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articles, Context mainActivity) {
     this.articlesArrayList=articles;
     context=mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {

        Articles articles=articlesArrayList.get(position);
        holder.subTitleTv.setText(articles.getDescription());
        holder.titleTv.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,NewsDeatileActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("description",articles.getDescription());
                intent.putExtra("urlToImage",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                intent.putExtra("content",articles.getContent());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTv,subTitleTv;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.idRVNewsHeading);
            subTitleTv=itemView.findViewById(R.id.idTVSubTitle);
            newsIV=itemView.findViewById(R.id.idIVNews);

        }
    }
}
