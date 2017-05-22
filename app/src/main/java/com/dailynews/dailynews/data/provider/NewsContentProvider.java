package com.dailynews.dailynews.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dailynews.dailynews.data.db.DaoMaster;
import com.dailynews.dailynews.data.db.DaoSession;
import com.google.firebase.crash.FirebaseCrash;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 123 on 2017/5/11.
 */

public class NewsContentProvider extends ContentProvider {

    private DaoSession mDaosession;

    private DaoMaster.DevOpenHelper mHelper;

    public static final Uri NEWS_URI = Uri.parse("content://" + "com.dailynews.dailynews" + "/" + "This is ur");

    private static final String TAG = NewsContentProvider.class.getSimpleName();

    @Override
    public boolean onCreate() {
        mHelper = new DaoMaster.DevOpenHelper(getContext(), "dailyNews");
        Database db = mHelper.getWritableDb();
        mDaosession = new DaoMaster(db).newSession();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor = mHelper.getReadableDatabase().query(
                mDaosession.getDailyNewsDao().getTablename(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long _id = mHelper.getWritableDatabase().insert(
                mDaosession.getDailyNewsDao().getTablename(),
                null,
                values
        );

        Uri returnUri;
        if (_id > 0)
            returnUri = Uri.parse("" + _id);
        else {
            FirebaseCrash.log(TAG + ": Failed to insert row into "+ uri);
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        Database db = mHelper.getWritableDb();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = mHelper.getWritableDatabase().insert(
                        mDaosession.getDailyNewsDao().getTablename(), null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsDeleted = mHelper.getWritableDatabase().delete(
                mDaosession.getDailyNewsDao().getTablename(),
                selection,
                selectionArgs);

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsUpdated = mHelper.getWritableDatabase().update(
                mDaosession.getDailyNewsDao().getTablename(),
                values,
                selection,
                selectionArgs
        );

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
