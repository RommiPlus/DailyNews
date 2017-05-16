package com.dailynews.dailynews.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.dailynews.dailynews.R;
import com.dailynews.dailynews.data.provider.NewsContentProvider;
import com.dailynews.dailynews.http.bean.MostPopular;
import com.dailynews.dailynews.http.bean.TopStories;
import com.dailynews.dailynews.widget.HomePageActivity;
import com.google.gson.internal.LinkedTreeMap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.content.ContentValues.TAG;

public class DailyNewsSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = DailyNewsSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in seconds.
    public static final int SYNC_INTERVAL = 60 * 60 * 3;  // 3 hours
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    public static final String ACTION_DATA_UPDATED =
            "com.example.android.sunshine.app.ACTION_DATA_UPDATED";

    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int WEATHER_NOTIFICATION_ID = 3004;

    private static final String SYNC_DATA = "SYNC_DATA";

    public DailyNewsSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public static void initializeSyncAdapter(Context context, Bundle bundle) {
        getSyncAccount(context, bundle);
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context, Bundle bundle) {
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context, bundle),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context, Bundle bundle) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context, bundle);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context, Bundle bundle) {
        /*
         * Since we've created an account
         */
        configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME, bundle);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime, Bundle bundle) {
        Account account = getSyncAccount(context, bundle);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(bundle).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, bundle, syncInterval);
        }
    }

    public static Bundle syncBundle(String[] array) {
        Bundle bundle = new Bundle();
        for (int i = 0; i < array.length; i++) {
            bundle.putString(HomePageActivity.sTabTitles[i], array[i]);
        }
        return bundle;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "onPerformSync Called.");

        for (int i = 0; i < HomePageActivity.sTabTitles.length; i++) {
            String topic = extras.getString(HomePageActivity.sTabTitles[i]);
            if (topic != null) {
                requestNews(topic);
            }
        }
    }

    public void requestNews(final String syncTopic) {
        if (syncTopic.contains("Top")) {
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
                            value.put("TOPIC", syncTopic);
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

                    Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(syncTopic).build();

                    getContext().getContentResolver().delete(uri, "TOPIC = ?", new String[]{syncTopic});
                    getContext().getContentResolver().bulkInsert(
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
        Call<MostPopular<Object>> call = mostPopularService.GetMostPopular(syncTopic, 1, "8ad7d39a8c7f49469614462a619b36c6");
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
                        value.put("TOPIC", syncTopic);
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

                Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(syncTopic).build();
                getContext().getContentResolver().delete(uri, "TOPIC = ?", new String[]{syncTopic});
                getContext().getContentResolver().bulkInsert(
                        uri,
                        values.toArray(new ContentValues[values.size()]));
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

    public interface TopStoriesService {
        @GET("{section}.json")
        Call<TopStories> GetTopStories(@Path("section") String section, @Query("api-key") String apiKey);
    }

    public interface MostPopularService {
        @GET("mostviewed/{section}/{time-period}.json")
        Call<MostPopular<Object>> GetMostPopular(@Path("section") String section, @Path("time-period") int timeSection, @Query("api-key") String apiKey);
    }

    private void updateWidgets() {
        Context context = getContext();
        // Setting the package ensures that only components in our app will receive the broadcast
        Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
                .setPackage(context.getPackageName());
        context.sendBroadcast(dataUpdatedIntent);
    }

}