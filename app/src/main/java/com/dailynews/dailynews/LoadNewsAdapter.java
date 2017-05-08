package com.dailynews.dailynews;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dailynews.dailynews.R.id.imageView;

//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;

/**
 * Created by 123 on 2017/5/5.
 */

public class LoadNewsAdapter<T> extends RecyclerView.Adapter {

    private Context mContext;

    public List<T> getDataList() {
        return dataList;
    }

    // the main data list to save loaded data
    protected List<T> dataList;

    public void setServerListSize(int serverListSize) {
        this.serverListSize = serverListSize;
    }

    // the serverListSize is the total number of items on the server side,
    // which should be returned from the web request results
    protected int serverListSize = -1;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    private QueryNewsData mQueryNewsData;

    public static class NewsData {
        String title;
        String detailContentUrl;
        String imageUrl;

        public NewsData(String title, String detailContentUrl, String imageUrl) {
            this.title = title;
            this.detailContentUrl = detailContentUrl;
            this.imageUrl = imageUrl;
        }
    }

    public interface QueryNewsData<T> {
        NewsData onDataSet(T data);
    }

    public LoadNewsAdapter(Context context) {
        mContext = context;
    }

    public void setNewsData(List<T> dataList, QueryNewsData<T> queryNewsData) {
        this.dataList = dataList;
        mQueryNewsData = queryNewsData;

        notifyDataSetChanged();
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
            T data = dataList.get(position);

            final NewsData newsData = mQueryNewsData.onDataSet(data);
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.mTitle.setText(newsData.title);
            newsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailActivity.actionStart(mContext, newsData.detailContentUrl);
                }
            });

            if (newsData.imageUrl != null) {

                Picasso.with(mContext)
                        .setLoggingEnabled(true);

                Picasso.with(mContext)
                        .load(newsData.imageUrl)
                        .placeholder(R.mipmap.image)
                        .error(android.R.drawable.ic_menu_camera)
                        .fit()
                        .centerCrop()
                        .into(newsViewHolder.mImageView);

            } else {
                newsViewHolder.mImageView.setVisibility(View.GONE);
            }
        } else if (holder instanceof LastItemViewHolder) {
            LastItemViewHolder lastItemViewHolder = (LastItemViewHolder) holder;
//            if (position >= serverListSize) {
                lastItemViewHolder.mProgressBar.setVisibility(View.GONE);
                lastItemViewHolder.mTextView.setVisibility(View.VISIBLE);
//            } else {
//                lastItemViewHolder.mProgressBar.setVisibility(View.VISIBLE);
//                lastItemViewHolder.mTextView.setVisibility(View.GONE);
//            }
        }

    }

    @Override
    public int getItemCount() {
        if (dataList == null || dataList.isEmpty()) {
            return 0;
        }
        return dataList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= dataList.size()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ACTIVITY;
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view)
        View mView;

        @BindView(R.id.title)
        TextView mTitle;

        @BindView(imageView)
        ImageView mImageView;

        NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class LastItemViewHolder extends RecyclerView.ViewHolder {
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
