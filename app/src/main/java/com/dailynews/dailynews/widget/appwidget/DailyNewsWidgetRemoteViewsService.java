package com.dailynews.dailynews.widget.appwidget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dailynews.dailynews.R;
import com.dailynews.dailynews.data.provider.NewsContentProvider;
import com.dailynews.dailynews.widget.DetailActivity;
import com.dailynews.dailynews.widget.HomePageActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Created by 123 on 2017/5/17.
 */

public class DailyNewsWidgetRemoteViewsService extends RemoteViewsService {

    private Cursor data = null;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory();
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            // Load data
            if (data != null) {
                data.close();
            }

            // This method is called by the app hosting the widget (e.g., the launcher)
            // However, our ContentProvider is not exported so it doesn't have access to the
            // data. Therefore we need to clear (and finally restore) the calling identity so
            // that calls use our process and permission
            final long identityToken = Binder.clearCallingIdentity();
            Uri uri = NewsContentProvider.NEWS_URI.buildUpon().appendPath(HomePageActivity.sTabTitles[0]).build();
            data = getContentResolver().query(uri,
                    null,
                    "TOPIC = ?", new String[]{HomePageActivity.sTabTitles[0]}, "UPDATE_DATE DESC");
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            // Bind data with view
            if (position == AdapterView.INVALID_POSITION ||
                    data == null || !data.moveToPosition(position)) {
                return null;
            }

            RemoteViews views = new RemoteViews(getPackageName(), R.layout.item_news);
            String title = data.getString(data.getColumnIndex("TITLE"));
            String imageUrl = data.getString(data.getColumnIndex("IMAGE_URL"));
            final String contentUrl = data.getString(data.getColumnIndex("COTENT_URL"));

            views.setTextViewText(R.id.title, title);
            Bitmap weatherArtImage = null;
            if (imageUrl != null) {
                try {
                    weatherArtImage = Picasso.with(getApplicationContext())
                            .load(imageUrl)
                            .placeholder(R.mipmap.image)
                            .error(android.R.drawable.ic_menu_camera)
                            .get();
                } catch (IOException e) {
                    Log.e(TAG, "Error retrieving large icon from " + imageUrl, e);
                }
            }

            if (imageUrl != null) {
                views.setViewVisibility(R.id.imageView, View.VISIBLE);
                views.setImageViewBitmap(R.id.imageView, weatherArtImage);
            } else {
                views.setViewVisibility(R.id.imageView, View.GONE);
            }

            final Intent fillInIntent = new Intent(getApplicationContext(), DetailActivity.class);
            fillInIntent.putExtra("URL", contentUrl);
            views.setOnClickFillInIntent(R.id.view, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return new RemoteViews(getPackageName(), R.layout.app_widget_loading);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
