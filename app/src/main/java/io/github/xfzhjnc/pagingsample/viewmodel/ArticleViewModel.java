package io.github.xfzhjnc.pagingsample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.repository.GankRepository;

public class ArticleViewModel extends ViewModel {
    private final GankRepository mRepository;
    private LiveData<ArticleEntity[]> mArticleEntities;

    ArticleViewModel(GankRepository repository) {
        mRepository = repository;
        mRepository.fetchArticleEntities(1, 10);
        mArticleEntities = mRepository.getArticleEntities();
    }

    public LiveData<ArticleEntity[]> getArticleEntities() {
        return mArticleEntities;
    }
}
