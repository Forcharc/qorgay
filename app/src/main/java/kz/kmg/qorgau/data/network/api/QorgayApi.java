package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.list.QorgayModel;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QorgayApi {

    @NonNull
    @POST("korgau/createmobile")
    Flowable<Response<IsSuccessResponse>> addQorgay(@Body RequestBody createQorgayBody);

    @NonNull
    @GET("korgau/GetKorgausByPhoneId")
    Flowable<Response<List<QorgayModel>>> getQorgayList(@Query("phoneUid") String phoneUid);

    @NonNull
    @GET("DictKorgauObservationType/GetAll")
    Flowable<Response<List<ObservationTypeModel>>> getObservationTypes();

    @NonNull
    @GET("Reference/GetOrganizations")
    Flowable<Response<List<OrganizationModel>>> getOrganizations();

    @NonNull
    @GET("Reference/GetOrgDepartments/{org_id}")
    Flowable<Response<List<DepartmentModel>>> getDepartments(@Path("org_id") int organizationId);

    @NonNull
    @GET("korgau/GetObservationCategories")
    Flowable<Response<List<ObservationCategoryModel>>> getObservationCategories();


}
