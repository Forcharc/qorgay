package kz.kmg.qorgau.domain.interactors;


import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.create.CreateQorgayModel;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.list.QorgayModel;
import kz.kmg.qorgau.data.model.user.ProfileModel;
import kz.kmg.qorgau.data.network.api.ProfileApi;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.ApiError;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;
import kz.kmg.qorgau.utils.files.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileInteractor {

    public static @NotNull
    Flowable<Resource<IsSuccessResponse>> login(ProfileApi profileApi,
                                                String login,
                                                String password
    ) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        addNotNull(builder, "UserName", login);
        addNotNull(builder, "Password", password);

        try {
            return profileApi.login(builder.build())
                    .onErrorReturn(ErrorHelper::getErrorResponse)
                    .map(response -> {
                        if (response.isSuccessful()) {
                            List<String> cookieList = response.headers().values("Set-Cookie");
                            String cookie = null;
                            if (cookieList != null && cookieList.size() > 0) {
                                cookie = (cookieList.get(2).split(";"))[0];
                            }
                            return Resource.successWithCookie(response.body(), cookie);
                        } else {
                            return ErrorHelper.<IsSuccessResponse>getQorgayApiError(response);
                        }
                    })
                    .subscribeOn(Schedulers.io());
        } catch (IllegalStateException e) {
            return Flowable.just(Resource.error(new ApiError("Specify data")));
        }
    }

    public static @NotNull
    Flowable<Resource<ProfileModel>> getProfile(ProfileApi profileApi, String cookie) {
        return profileApi.getProfile(cookie)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<ProfileModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    private static void addNotNull(MultipartBody.Builder builder, String field, Object object) {
        if (object != null && !object.toString().equals("")) {
            builder.addFormDataPart(field, object.toString());
        }
    }


}
