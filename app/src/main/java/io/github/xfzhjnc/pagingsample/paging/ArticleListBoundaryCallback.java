package io.github.xfzhjnc.pagingsample.paging;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import io.github.xfzhjnc.pagingsample.network.NetworkDataSource;

public class ArticleListBoundaryCallback extends PagedList.BoundaryCallback {

    private final int               NETWORK_PAGE_SIZE = 50;
    private       int               lastRequestedPage = 0;
    private       NetworkDataSource mNetworkDataSource;
    // TODO 避免同一时间发起多个请求
    private       boolean           isRequestInProgress = false;

    public ArticleListBoundaryCallback(NetworkDataSource networkDataSource) {
        mNetworkDataSource = networkDataSource;
    }

    @Override
    public void onZeroItemsLoaded() {
        request();
    }

    @Override
    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
        request();
    }

    private void request() {
        mNetworkDataSource.fetchArticleEntities(++lastRequestedPage, NETWORK_PAGE_SIZE);
    }
}
