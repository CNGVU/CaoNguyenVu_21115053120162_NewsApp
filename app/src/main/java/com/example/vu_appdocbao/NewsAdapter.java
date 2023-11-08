package com.example.vu_appdocbao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    Context context;
    int resource;
    ArrayList<News> listNews;
    public NewsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<News> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listNews = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_custom,parent,false);

        News news = listNews.get(position);
        ImageView img = convertView.findViewById(R.id.img_item);
        // chuyen url sang image
        Glide.with(convertView).load(news.getImg()).into(img);

        TextView tv_title_news = convertView.findViewById(R.id.tv_title_news);
        tv_title_news.setText(news.getTitle());

        TextView tv_time_news = convertView.findViewById(R.id.tv_time_news);
        tv_time_news.setText(news.getTime());
        return convertView;
    }
}