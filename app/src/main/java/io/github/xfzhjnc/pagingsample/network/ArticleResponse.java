package io.github.xfzhjnc.pagingsample.network;

import com.google.gson.annotations.SerializedName;

public class ArticleResponse<T> {
    @SerializedName("error")
    private boolean mError;
    @SerializedName("results")
    private T mData;

    public ArticleResponse(boolean error, T data) {
        mError = error;
        mData = data;
    }

    boolean isError() {
        return mError;
    }

    T getData() {
        return mData;
    }
}
