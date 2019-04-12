package io.github.xfzhjnc.pagingsample;

import io.github.xfzhjnc.pagingsample.database.ArticleEntity;
import io.github.xfzhjnc.pagingsample.network.ArticleResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public final class HttpClient {
    private static final String HOST = "http://gank.io/api/";

    public static GankService getGankService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GankService.class);
    }

    public interface GankService {
        @GET("data/Android/{count}/{page}")
        Call<ArticleResponse<ArticleEntity[]>> getAndroidArticleList(@Path("count") int count, @Path("page") int page);
    }
}
