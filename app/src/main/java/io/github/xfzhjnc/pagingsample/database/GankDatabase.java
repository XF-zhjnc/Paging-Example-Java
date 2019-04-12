package io.github.xfzhjnc.pagingsample.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ArticleEntity.class}, version = 1)
public abstract class GankDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "gank.db";
    private static final Object LOCK = new Object();
    private static volatile GankDatabase sInstance;

    public abstract ArticleDao articleDao();

    public static GankDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            GankDatabase.class, GankDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
