package com.dailynews.dailynews.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dailynews.dailynews.db.DaoMaster;
import com.dailynews.dailynews.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by 123 on 2017/5/11.
 */

public class NewsContentProvider extends ContentProvider {

    private DaoSession mDaosession;

    public DaoSession getmDaossesion() {
        return mDaosession;
    }

    private DaoMaster.DevOpenHelper mHelper;

    @Override
    public boolean onCreate() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "dailyNews");
        Database db = helper.getWritableDb();
        mDaosession = new DaoMaster(db).newSession();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return mHelper.getReadableDatabase().query(
                mDaosession.getDailyNewsDao().getTablename(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
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
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
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
