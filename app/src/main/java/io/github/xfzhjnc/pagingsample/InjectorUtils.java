package io.github.xfzhjnc.pagingsample;

import android.content.Context;

import io.github.xfzhjnc.pagingsample.database.GankDatabase;
import io.github.xfzhjnc.pagingsample.network.NetworkDataSource;
import io.github.xfzhjnc.pagingsample.repository.GankRepository;

public class InjectorUtils {

    public static GankRepository provideGankRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        NetworkDataSource networkDataSource = provideNetworkDataSource();
        GankDatabase database = GankDatabase.getInstance(context);
        return GankRepository.getInstance(executors, networkDataSource, database);
    }

    public static NetworkDataSource provideNetworkDataSource() {
        AppExecutors executors = AppExecutors.getInstance();
        return NetworkDataSource.getInstance(executors);
    }

}
