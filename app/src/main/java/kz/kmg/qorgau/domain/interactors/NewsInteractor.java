package kz.kmg.qorgau.domain.interactors;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.data.model.user.ProfileModel;
import kz.kmg.qorgau.data.network.api.NewsApi;
import kz.kmg.qorgau.data.network.api.ProfileApi;
import kz.kmg.qorgau.data.network.base.ApiError;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;
import okhttp3.MultipartBody;

public class NewsInteractor {

    public static @NotNull
    Flowable<Resource<List<NewsModel>>> getNews(NewsApi api) {
        return api.getNews()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<NewsModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    public static @NotNull
    Flowable<Resource<List<NewsTopModel>>> getNewsTop(NewsApi api) {
        return api.getNewsTop()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<NewsTopModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

}
