package com.dailynews.dailynews;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
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
public class PageFragment extends Fragment {
    public static final String ARG_TITLE = "ARG_TITLE";

    private String mTitle;

    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private LoadNewsAdapter mLoadNewsAdapter;
    public static PageFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, view);

        // request data from server according to title

        // Load data to display


        // When pull to refresh, update all data


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
        mLoadNewsAdapter = new LoadNewsAdapter(getContext());
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
        if (mTitle.contains("Top")) {
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
                    List<TopStories.ResultsBean> list = topStories.getResults();
                    mLoadNewsAdapter.setNewsData(list, new LoadNewsAdapter.QueryNewsData<TopStories.ResultsBean>() {
                        @Override
                        public LoadNewsAdapter.NewsData onDataSet(TopStories.ResultsBean data) {
                            String title = data.getTitle();
                            String detailContenUrl = data.getUrl();

                            String imageUrl = null;
                            List<TopStories.ResultsBean.MultimediaBean> MuliList = data.getMultimedia();
                            if (MuliList != null && !MuliList.isEmpty()) {
                                TopStories.ResultsBean.MultimediaBean bean = data.getMultimedia().get(3);
                                imageUrl = bean.getUrl();
                            }

                            return new LoadNewsAdapter.NewsData(title, detailContenUrl, imageUrl);
                        }
                    });

                    mLoadNewsAdapter.setServerListSize(topStories.getNum_results());
                    mLoadNewsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TopStories> call, Throwable t) {
                    Log.v("Throwable", t.getMessage());
                }
            });
            return;
        }

        // request Most popular
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/")
//                .client(client)
                .build();

        MostPopularService mostPopularService = retrofit.create(MostPopularService.class);
        Call<MostPopular<Object>> call = mostPopularService.GetMostPopular(mTitle, 1, "8ad7d39a8c7f49469614462a619b36c6");
        call.enqueue(new Callback<MostPopular<Object>>() {
            @Override
            public void onResponse(Call<MostPopular<Object>> call, Response<MostPopular<Object>> response) {
                MostPopular mostPopular = response.body();
                List<MostPopular.ResultsBean> list = mostPopular.getResults();
                mLoadNewsAdapter.setNewsData(list, new LoadNewsAdapter.QueryNewsData<MostPopular.ResultsBean>() {
                    @Override
                    public LoadNewsAdapter.NewsData onDataSet(MostPopular.ResultsBean data) {
                        String title = data.getTitle();
                        String detailContenUrl = data.getUrl();

                        String imageUrl = null;
                        Object meida = data.getMedia();
                        if (meida != null && meida instanceof ArrayList) {
                            ArrayList< LinkedTreeMap<String, Object>> beanList = ((ArrayList) meida);
                           LinkedTreeMap<String, Object> map = beanList.get(0);
                           ArrayList<LinkedTreeMap<String, Object>> list = (ArrayList<LinkedTreeMap<String, Object>>) map.get("media-metadata");
                            if (list != null && !list.isEmpty()) {
                                LinkedTreeMap<String, Object> metaBean = list.get(1);
                                imageUrl = (String) metaBean.get("url");
                            }
                        }

                        return new LoadNewsAdapter.NewsData(title, detailContenUrl, imageUrl);
                    }
                });

                mLoadNewsAdapter.setServerListSize(mostPopular.getNum_results());
                mLoadNewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MostPopular<Object>> call, Throwable t) {
                Log.v("Throwable", t.getMessage());
            }
        });
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