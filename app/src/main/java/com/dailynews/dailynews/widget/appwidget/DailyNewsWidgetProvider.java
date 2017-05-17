package com.dailynews.dailynews.widget.appwidget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.dailynews.dailynews.R;
import com.dailynews.dailynews.sync.DailyNewsSyncAdapter;
import com.dailynews.dailynews.widget.DetailActivity;
import com.dailynews.dailynews.widget.HomePageActivity;

/**
 * Created by 123 on 2017/5/17.
 */

public class DailyNewsWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, HomePageActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Set up the collection
            setRemoteAdapter(context, views);


            Intent clickIntentTemplate = new Intent(context, DetailActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.list_view, clickPendingIntentTemplate);
            views.setEmptyView(R.id.list_view, R.id.widget_empty);

            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.list_view,
                new Intent(context, DailyNewsWidgetRemoteViewsService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (DailyNewsSyncAdapter.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        }
    }

}
