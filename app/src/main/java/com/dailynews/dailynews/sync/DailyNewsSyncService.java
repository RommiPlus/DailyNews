package com.dailynews.dailynews.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DailyNewsSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static DailyNewsSyncAdapter sDailyNewsSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sDailyNewsSyncAdapter == null) {
                sDailyNewsSyncAdapter = new DailyNewsSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sDailyNewsSyncAdapter.getSyncAdapterBinder();
    }
}