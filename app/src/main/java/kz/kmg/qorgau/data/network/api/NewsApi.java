package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.data.model.list.QorgayModel;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @NonNull
    @GET("admin/news/list")
    Flowable<Response<List<NewsModel>>> getNews();

    @NonNull
    @GET("admin/news/top")
    Flowable<Response<List<NewsTopModel>>> getNewsTop();
}
