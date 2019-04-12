package io.github.xfzhjnc.pagingsample.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.repository.GankRepository;

public class ArticleViewModel extends ViewModel {
    private final GankRepository mRepository;
    private LiveData<PagedList<ArticleEntity>> mArticleEntities;

    ArticleViewModel(GankRepository repository) {
        mRepository = repository;
        mArticleEntities = mRepository.getArticleEntities();
    }

    public LiveData<PagedList<ArticleEntity>> getArticleEntities() {
        return mArticleEntities;
    }
}
