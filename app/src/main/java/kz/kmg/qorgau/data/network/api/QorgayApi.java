package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import kz.kmg.qorgau.data.model.home.questionnaire.ObservationType;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QorgayApi {

    @NonNull
    @POST("korgau/createmobile")
    Flowable<Response<Object>> addQorgay(@Body RequestBody createQorgayBody);

    @NonNull
    @GET("DictKorgauObservationType/GetAll")
    Flowable<Response<List<ObservationType>>> getObservationTypes();
    
}
