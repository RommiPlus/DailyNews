package com.dailynews.dailynews.widget.fragment;


import android.content.ContentValues;
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

import com.blankj.utilcode.util.LogUtils;
import com.dailynews.dailynews.EndlessRecyclerViewScrollListener;
import com.dailynews.dailynews.LoadNewsAdapter;
import com.dailynews.dailynews.R;
import com.dailynews.dailynews.data.provider.NewsContentProvider;
import com.dailynews.dailynews.http.bean.MostPopular;
import com.dailynews.dailynews.http.bean.TopStories;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    private static int NEWS_LOADER = 0;

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
                requestNews();
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

        requestNews();
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

    public void requestNews() {
        if (mTopic.contains("Top")) {
            // request top stories
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
                    .build();

            TopStoriesService topStories = retrofit.create(TopStoriesService.class);
            Call<TopStories> call = topStories.GetTopStories("world", "8ad7d39a8c7f49469614462a619b36c6");
            call.enqueue(new Callback<TopStories>() {
                @Override
                public void onResponse(Call<TopStories> call, Response<TopStories> response) {
                    TopStories topStories = response.body();
                    mLoadNewsAdapter.setServerListSize(topStories.getNum_results());

                    List<TopStories.ResultsBean> list = topStories.getResults();
                    List<ContentValues> values = new ArrayList<>();
                    for (TopStories.ResultsBean bean : list) {
                        String title = bean.getTitle();
                        String detailContenUrl = bean.getUrl();

                        String imageUrl = null;
                        List<TopStories.ResultsBean.MultimediaBean> MuliList = bean.getMultimedia();
                        if (MuliList != null && !MuliList.isEmpty()) {
                            TopStories.ResultsBean.MultimediaBean multimediaBean = MuliList.get(3);
                            imageUrl = multimediaBean.getUrl();
                        }

                        try {
                            ContentValues value = new ContentValues();
                            value.put("TOPIC", mTopic);
                            value.put("TITLE", title);
                            value.put("IMAGE_URL", imageUrl);
                            value.put("COTENT_URL", detailContenUrl);
                            value.put("UPDATE_DATE", getUpdateDate(bean.getUpdated_date()).getTime());
                            values.add(value);
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.e(TAG, e.getMessage());
                        }
                    }

                    Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(mTopic).build();

                    getActivity().getContentResolver().delete(uri, "TOPIC = ?", new String[]{mTopic});
                    getActivity().getContentResolver().bulkInsert(
                            uri,
                            values.toArray(new ContentValues[values.size()]));

                }

                @Override
                public void onFailure(Call<TopStories> call, Throwable t) {
                    Log.v("Throwable", t.getMessage());
                }
            });
            return;
        }

        // request Most popular
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
                .build();

        MostPopularService mostPopularService = retrofit.create(MostPopularService.class);
        Call<MostPopular<Object>> call = mostPopularService.GetMostPopular(mTopic, 1, "8ad7d39a8c7f49469614462a619b36c6");
        call.enqueue(new Callback<MostPopular<Object>>() {
            @Override
            public void onResponse(Call<MostPopular<Object>> call, Response<MostPopular<Object>> response) {
                MostPopular mostPopular = response.body();
                List<MostPopular.ResultsBean> list = mostPopular.getResults();
                List<ContentValues> values = new ArrayList<>();
                for (MostPopular.ResultsBean bean : list) {
                    String title = bean.getTitle();
                    String detailContenUrl = bean.getUrl();

                    String imageUrl = null;
                    Object meida = bean.getMedia();
                    if (meida != null && meida instanceof ArrayList) {
                        ArrayList<LinkedTreeMap<String, Object>> beanList = ((ArrayList) meida);
                        LinkedTreeMap<String, Object> map = beanList.get(0);
                        ArrayList<LinkedTreeMap<String, Object>> data = (ArrayList<LinkedTreeMap<String, Object>>) map.get("media-metadata");
                        if (data != null && !data.isEmpty()) {
                            LinkedTreeMap<String, Object> metaBean = data.get(1);
                            imageUrl = (String) metaBean.get("url");
                        }
                    }

                    try {
                        ContentValues value = new ContentValues();
                        value.put("TOPIC", mTopic);
                        value.put("TITLE", title);
                        value.put("IMAGE_URL", imageUrl);
                        value.put("COTENT_URL", detailContenUrl);
                        value.put("UPDATE_DATE", getPublishDate(bean.getPublished_date()).getTime());
                        values.add(value);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        LogUtils.e(TAG, e.getMessage());
                    }
                }

                Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(mTopic).build();
                getActivity().getContentResolver().delete(uri, "TOPIC = ?", new String[]{mTopic});
                getActivity().getContentResolver().bulkInsert(
                        uri,
                        values.toArray(new ContentValues[values.size()]));

                mLoadNewsAdapter.setServerListSize(mostPopular.getNum_results());
            }

            @Override
            public void onFailure(Call<MostPopular<Object>> call, Throwable t) {
                Log.v("Throwable", t.getMessage());
            }
        });
    }

    private Date getPublishDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
        return format.parse(date);
    }

    private Date getUpdateDate(String date) throws ParseException {
        date = date.replace("T", " ").substring(0, date.lastIndexOf("-"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
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

    public interface TopStoriesService {
        @GET("{section}.json")
        Call<TopStories> GetTopStories(@Path("section") String section, @Query("api-key") String apiKey);
    }

    public interface MostPopularService {
        @GET("mostviewed/{section}/{time-period}.json")
        Call<MostPopular<Object>> GetMostPopular(@Path("section") String section, @Path("time-period") int timeSection, @Query("api-key") String apiKey);
    }
}