package com.dailynews.dailynews.widget.fragment;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.dailynews.dailynews.EndlessRecyclerViewScrollListener;
import com.dailynews.dailynews.LoadNewsAdapter;
import com.dailynews.dailynews.R;
import com.dailynews.dailynews.data.provider.NewsContentProvider;
import com.dailynews.dailynews.sync.DailyNewsSyncAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_POSITION = "ARG_POSITION";

    private String mTopic;
    private int mPosition;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.progressbar)
    public ProgressBar mProgressBar;

    private LoadNewsAdapter mLoadNewsAdapter;

    private static final String TAG = PageFragment.class.getSimpleName();

    public static PageFragment newInstance(int position, String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_POSITION, position);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopic = getArguments().getString(ARG_TITLE);
        mPosition = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.blue, R.color.green, R.color.purple);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DailyNewsSyncAdapter.syncImmediately(
                        getActivity(), DailyNewsSyncAdapter.syncBundle(new String[]{mTopic}));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 5000);

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mLoadNewsAdapter = new LoadNewsAdapter(getContext(), mProgressBar, null);
        mRecyclerView.setAdapter(mLoadNewsAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayout.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DailyNewsSyncAdapter.syncImmediately(
                getActivity(), DailyNewsSyncAdapter.syncBundle(new String[]{mTopic}));
        getActivity().getSupportLoaderManager().initLoader(mPosition, null, this);
    }



    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                mLoadNewsAdapter.setItemCount(10);
//                mLoadNewsAdapter.notifyDataSetChanged();
            }
        }, 4000);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "id: " + id);
        Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(mTopic).build();
        return new CursorLoader(getActivity(), uri, null,
                "TOPIC = ?", new String[]{mTopic}, "UPDATE_DATE DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "mTopic: " + mTopic);
        mLoadNewsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mLoadNewsAdapter.swapCursor(null);
    }


}