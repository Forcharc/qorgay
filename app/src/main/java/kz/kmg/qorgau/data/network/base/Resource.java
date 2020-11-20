package kz.kmg.qorgau.data.network.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import retrofit2.http.Headers;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final ApiError apiError;

    @Nullable
    public final String cookie;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable ApiError apiError, @Nullable String cookie) {
        this.status = status;
        this.data = data;
        this.apiError = apiError;
        this.cookie = cookie;
    }

    public static <T> Resource<T> success (@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data,null, null);
    }

    public static <T> Resource<T> successWithCookie (@Nullable T data, String cookie) {
        return new Resource<>(Status.SUCCESS, data,null, cookie);
    }

    public static <T> Resource<T> error(@Nullable ApiError apiError) {
        return new Resource<>(Status.ERROR, null, apiError, null);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}
