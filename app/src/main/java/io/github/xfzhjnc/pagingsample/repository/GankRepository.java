package io.github.xfzhjnc.pagingsample.repository;

import androidx.lifecycle.LiveData;
import io.github.xfzhjnc.pagingsample.AppExecutors;
import io.github.xfzhjnc.pagingsample.database.ArticleDao;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.database.GankDatabase;
import io.github.xfzhjnc.pagingsample.network.NetworkDataSource;

public class GankRepository {
    private static final Object LOCK = new Object();
    private static volatile GankRepository sInstance;

    private final AppExecutors mExecutors;
    private final NetworkDataSource mNetworkDataSource;
    private final ArticleDao mArticleDao;

    private final LiveData<ArticleEntity[]> mArticleEntities;

    private GankRepository(AppExecutors executors, NetworkDataSource networkDataSource, GankDatabase database) {
        mExecutors = executors;
        mNetworkDataSource = networkDataSource;
        mArticleDao = database.articleDao();
        mArticleEntities = mNetworkDataSource.getDownloadedArticleEntities();
        mArticleEntities.observeForever(articleEntities -> mExecutors.diskIO().execute(()-> mArticleDao.bulkInsert(articleEntities)));
    }

    public synchronized static GankRepository getInstance(AppExecutors executors, NetworkDataSource networkDataSource, GankDatabase database) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new GankRepository(executors, networkDataSource, database);
                }
            }
        }
        return sInstance;
    }

    public void fetchArticleEntities(final int page, final int count) {
        mNetworkDataSource.fetchArticleEntities(page, count);
    }

    public LiveData<ArticleEntity[]> getArticleEntities() {
        return mArticleDao.getArticles();
    }
}
