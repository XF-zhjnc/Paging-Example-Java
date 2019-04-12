package io.github.xfzhjnc.pagingsample.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.github.xfzhjnc.pagingsample.repository.GankRepository;

public class ArticleViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final GankRepository mGankRepository;

    public ArticleViewModelFactory(GankRepository repository) {
        mGankRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ArticleViewModel(mGankRepository);
    }
}
