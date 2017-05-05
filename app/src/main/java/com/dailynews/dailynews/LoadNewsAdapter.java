package com.dailynews.dailynews;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 123 on 2017/5/5.
 */

public class LoadNewsAdapter extends RecyclerView.Adapter {

    // the main data list to save loaded data
//    protected List<T> dataList;

    public void setServerListSize(int serverListSize) {
        this.serverListSize = serverListSize;
    }

    // the serverListSize is the total number of items on the server side,
    // which should be returned from the web request results
    protected int serverListSize = 1000;

    private int mItemCount = 15;
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    public void setItemCount(int add) {
        mItemCount += add;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            // display the last row
            Activity activity = (Activity) parent.getContext();
            View view = activity.getLayoutInflater().inflate(
                    R.layout.progress, parent, false);

            return new LastItemViewHolder(view);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.mTitle.setText("习近平改革方法论：用“督查”“督”出改革实效");
            newsViewHolder.mSubTitle.setText("作风优良，能打胜仗");
            newsViewHolder.mImageView.setImageResource(R.mipmap.image);
        } else if (holder instanceof LastItemViewHolder) {
            LastItemViewHolder lastItemViewHolder = (LastItemViewHolder) holder;
            if (position > serverListSize) {
                lastItemViewHolder.mProgressBar.setVisibility(View.GONE);
                lastItemViewHolder.mTextView.setVisibility(View.VISIBLE);
            } else {
                lastItemViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                lastItemViewHolder.mTextView.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mItemCount + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= mItemCount) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView mTitle;

        @BindView(R.id.subtitle) TextView mSubTitle;

        @BindView(R.id.imageView)
        ImageView mImageView;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LastItemViewHolder extends  RecyclerView.ViewHolder {
        @BindView(R.id.progressbar)
        ProgressBar mProgressBar;

        @BindView(R.id.textview)
        TextView mTextView;

        LastItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
