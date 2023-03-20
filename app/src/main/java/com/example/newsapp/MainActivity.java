package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorclickInterface{
    private RecyclerView newsRv,categoryRV;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRv=findViewById(R.id.idRVNews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadingPB=findViewById(R.id.PBLoading);
        swipeRefreshLayout=findViewById(R.id.swipe);
        articlesArrayList=new ArrayList<>();
        categoryRVModalArrayList=new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRv.setLayoutManager(new LinearLayoutManager(this));
        newsRv.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // Your code goes here
                        // In this code, we are just
                        // changing the text in the textbox

                        Toast.makeText(MainActivity.this,"Data UPDATED",Toast.LENGTH_LONG).show();

                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );


        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();

    }


    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://images.unsplash.com/reserve/Af0sF2OS5S5gatqrKzVP_Silhoutte.jpg?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fEFsbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1554475900-0a0350e3fc7b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1579952363873-27f3bade9f55?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c3BvcnRzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1504805572947-34fad45aed93?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDh8fGlubm92YXRpb258ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njd8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://plus.unsplash.com/premium_photo-1664475930841-305c641fa9f0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZW50ZXJ0YWlubWVudCUyMGV2ZW50fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fEhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));

        categoryRVAdapter.notifyDataSetChanged();

    }

    private void getNews(String category){
        swipeRefreshLayout.setRefreshing(false);

        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apikey=35be82bff9cf49fd80d261a3a56de2ff";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=35be82bff9cf49fd80d261a3a56de2ff";
        String BASE_URL="https://newsapi.org/";
        Retrofit retroFit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroFitAPI retroFitAPI= retroFit.create(RetroFitAPI.class);
        Call<NewsModal> call;
        if (category.equals("All")){
            call=retroFitAPI.getAllNews(url);
        }
        else {
            call=retroFitAPI.getNewsByCategory(categoryUrl);
        }
        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
              NewsModal newsModal=response.body();
              loadingPB.setVisibility(View.GONE);
              ArrayList<Articles> articles=newsModal.getArticles();
              for(int i=0;i<articles.size();i++){
                  articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                          articles.get(i).getUrl(),articles.get(i).getContent()));
              }
                newsRVAdapter=new NewsRVAdapter(articlesArrayList,MainActivity.this);
                categoryRVAdapter=new CategoryRVAdapter(categoryRVModalArrayList,MainActivity.this,MainActivity.this::onCategoryClick);
                newsRv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                newsRv.setAdapter(newsRVAdapter);
                categoryRV.setAdapter(categoryRVAdapter);

              newsRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed to get news",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onCategoryClick(int position) {
        String category=categoryRVModalArrayList.get(position).getCategory();
getNews(category);

    }

}