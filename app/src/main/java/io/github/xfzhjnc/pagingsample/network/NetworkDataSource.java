package io.github.xfzhjnc.pagingsample.network;

import androidx.lifecycle.MutableLiveData;
import io.github.xfzhjnc.pagingsample.AppExecutors;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.HttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource {
    private static final Object LOCK = new Object();
    private static volatile NetworkDataSource sInstance;

    private AppExecutors mExecutors;

    private final MutableLiveData<ArticleEntity[]> mDownloadedArticleEntities;

    private NetworkDataSource(AppExecutors executors) {
        mExecutors = executors;
        mDownloadedArticleEntities = new MutableLiveData<>();
    }

    public synchronized static NetworkDataSource getInstance(AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new NetworkDataSource(executors);
                }
            }
        }
        return sInstance;
    }

    public void fetchArticleEntities(final int page, final int count) {
        mExecutors.networkIO().execute(()-> {
            Call<ArticleResponse<ArticleEntity[]>> call = HttpClient.getGankService().getAndroidArticleList(count, page);
            call.enqueue(new Callback<ArticleResponse<ArticleEntity[]>>() {
                @Override
                public void onResponse(Call<ArticleResponse<ArticleEntity[]>> call, Response<ArticleResponse<ArticleEntity[]>> response) {
                    assert response.body() != null;
                    if (!response.body().isError()) {
                        ArticleEntity[] entities = response.body().getData();
                        mDownloadedArticleEntities.postValue(entities);
                    }
                }

                @Override
                public void onFailure(Call<ArticleResponse<ArticleEntity[]>> call, Throwable t) {

                }
            });
        });
    }

    public MutableLiveData<ArticleEntity[]> getDownloadedArticleEntities() {
        return mDownloadedArticleEntities;
    }
}
