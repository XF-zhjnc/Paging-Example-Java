package io.github.xfzhjnc.pagingsample.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import io.github.xfzhjnc.pagingsample.AppExecutors;
import io.github.xfzhjnc.pagingsample.database.ArticleDao;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.database.GankDatabase;
import io.github.xfzhjnc.pagingsample.network.NetworkDataSource;
import io.github.xfzhjnc.pagingsample.paging.ArticleListBoundaryCallback;

public class GankRepository {
    private static final Object LOCK = new Object();
    private static volatile GankRepository sInstance;

    private final AppExecutors mExecutors;
    private final NetworkDataSource mNetworkDataSource;
    private final ArticleDao mArticleDao;

    private final LiveData<ArticleEntity[]> mArticleEntities;

    private final int DATABASE_PAGE_SIZE = 20;

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

    public LiveData<PagedList<ArticleEntity>> getArticleEntities() {
        DataSource.Factory<Integer, ArticleEntity> factory  = mArticleDao.getArticles();
        ArticleListBoundaryCallback callback = new ArticleListBoundaryCallback(mNetworkDataSource);
        return new LivePagedListBuilder(factory, DATABASE_PAGE_SIZE).setBoundaryCallback(callback).build();
    }
}
