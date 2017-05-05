package com.dailynews.dailynews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 123 on 2017/5/5.
 */

public class LoadNewsAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
        newsViewHolder.mTitle.setText("习近平改革方法论：用“督查”“督”出改革实效");
        newsViewHolder.mSubTitle.setText("作风优良，能打胜仗");
        newsViewHolder.mImageView.setImageResource(R.mipmap.image);
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.subtitle) TextView mSubTitle;

        @BindView(R.id.imageView)
        ImageView mImageView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
