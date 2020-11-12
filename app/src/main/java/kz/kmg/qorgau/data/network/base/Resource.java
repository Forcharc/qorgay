package kz.kmg.qorgau.data.network.base;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final ApiError apiError;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable ApiError apiError) {
        this.status = status;
        this.data = data;
        this.apiError = apiError;
    }

    public static <T> Resource<T> success (@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data,null);
    }

    public static <T> Resource<T> error(@Nullable ApiError apiError) {
        return new Resource<>(Status.ERROR, null, apiError);
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING}
}
