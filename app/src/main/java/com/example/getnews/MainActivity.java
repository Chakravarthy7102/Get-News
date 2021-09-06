package com.example.getnews;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private RecyclerView newsRV, categoriesRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articles;
    private ArrayList<CategoryRVModel> categoryRVModals;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAadapter newsRVAapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoriesRV = findViewById(R.id.idRVCategory);
        loadingPB = findViewById(R.id.idPBLoading);
        loadingPB.setVisibility(View.VISIBLE);
        articles = new ArrayList<>();
        categoryRVModals = new ArrayList<>();
        newsRVAapter = new NewsRVAadapter(articles, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModals, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAapter);
        categoriesRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNewsByRetrofit("All");
        newsRVAapter.notifyDataSetChanged();

    }

    private void getCategories() {
        categoryRVModals.add(new CategoryRVModel("All","https://i1.wp.com/www.justwalkedby.com/wp-content/uploads/2019/09/20090402_185758.jpg"));
        categoryRVModals.add(new CategoryRVModel("Technology", "https://wallpapercave.com/wp/bfATswl.jpg"));
        categoryRVModals.add(new CategoryRVModel("Science",  "https://torange.biz/photofxnew/177/HD/medical-science-educational-pharm-illustration-background-177387.jpg"));
        categoryRVModals.add(new CategoryRVModel("Sports", "https://wallpapercave.com/wp/bfATswl.jpg(technology)"));
        categoryRVModals.add(new CategoryRVModel("General", "https://live.staticflickr.com/65535/47774841802_cb3713988e_b.jpg"));
        categoryRVModals.add(new CategoryRVModel("Business", "https://api.time.com/wp-content/uploads/2020/03/stock-market-coronavirus-2.jpg"));
        categoryRVModals.add(new CategoryRVModel("Entertainment", "https://2.bp.blogspot.com/--ggQ4-UWDfg/Ummg3X22iwI/AAAAAAAAAeg/oGwAaGHDV1w/s1600/screenland+interior2.jpg"));
        categoryRVModals.add(new CategoryRVModel("Health", "https://c2.staticflickr.com/4/3164/2947616212_f0f58cfa00_b.jpg"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNewsByRetrofit(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articles.clear();
        String categoryURL = "http://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=9f4dace8dd01449db36a0f0933321675";
        String url = "http://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=9f4dace8dd01449db36a0f0933321675";
        String BASE_URL = "http://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if (category.equals("All")) {
            call = retrofitAPI.getALlNews(url);
        } else {
            call = retrofitAPI.getALlNews(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>()  {
            @Override
            public void onResponse(Call<NewsModel> call, retrofit2.Response<NewsModel> response) {

                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articlesArrayList = newsModel.getArticles();
                for (int i = 0; i < articlesArrayList.size(); i++) {
                    articles.add(new Articles(articlesArrayList.get(i).getTitle(), articlesArrayList.get(i).getDescription(), articlesArrayList.get(i).getUrlToImage(), articlesArrayList.get(i).getUrl(), articlesArrayList.get(i).getContent()));
                }
                newsRVAapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModals.get(position).getCategory();
        getNewsByRetrofit(category);
    }
}