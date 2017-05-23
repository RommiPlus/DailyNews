package com.dailynews.dailynews;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dailynews.dailynews.data.db.DailyNewsDao;
import com.dailynews.dailynews.widget.DetailActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dailynews.dailynews.R.id.imageView;


/**
 * Created by 123 on 2017/5/5.
 */

public class LoadNewsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private Cursor mCursor;
    private ProgressBar mEmptyView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = LoadNewsAdapter.class.getSimpleName();

    public LoadNewsAdapter(Context context, ProgressBar progressBar, Cursor cursor) {
        mContext = context;
        mEmptyView = progressBar;
        mCursor = cursor;
    }

    public void setServerListSize(int serverListSize) {
        this.serverListSize = serverListSize;
    }

    public void setFirebaseAnalytics(FirebaseAnalytics analytics) {
        this.mFirebaseAnalytics = analytics;
    }

    // the serverListSize is the total number of items on the server side,
    // which should be returned from the web request results
    protected int serverListSize = -1;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    public void swapCursor(Cursor data) {
        mCursor = data;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            // display the last row
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.progress, parent, false);

            return new LastItemViewHolder(view);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            mCursor.moveToPosition(position);
            String title = mCursor.getString(mCursor.getColumnIndex(DailyNewsDao.Properties.Title.columnName));
            String imageUrl = mCursor.getString(mCursor.getColumnIndex(DailyNewsDao.Properties.ImageUrl.columnName));
            final String contentUrl = mCursor.getString(mCursor.getColumnIndex(DailyNewsDao.Properties.CotentUrl.columnName));

            final NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            newsViewHolder.mTitle.setText(title);
            newsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Click Event", "News: " + newsViewHolder.mTitle + " is clicked");
                    mFirebaseAnalytics.logEvent(TAG, bundle);

                    DetailActivity.actionStart(mContext, contentUrl);
                }
            });

            if (imageUrl != null) {

                Picasso.with(mContext)
                        .setLoggingEnabled(true);

                Picasso.with(mContext)
                        .load(imageUrl)
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
        if (mCursor == null || mCursor.getCount() == 0) {
            return 0;
        }
        return mCursor.getCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= mCursor.getCount()) ? VIEW_TYPE_LOADING
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
