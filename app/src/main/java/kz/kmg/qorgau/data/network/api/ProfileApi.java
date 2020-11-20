package kz.kmg.qorgau.data.network.api;


import androidx.annotation.NonNull;

import io.reactivex.Flowable;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.user.ProfileModel;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProfileApi {

    @NonNull
    @POST("account/auth")
    Flowable<Response<IsSuccessResponse>> login(@Body RequestBody createQorgayBody);

    @NonNull
    @GET("profile/info")
    Flowable<Response<ProfileModel>> getProfile(@Header("Cookie") String cookie);
}
