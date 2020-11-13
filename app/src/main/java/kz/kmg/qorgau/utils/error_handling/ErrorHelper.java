package kz.kmg.qorgau.utils.error_handling;

import com.google.gson.Gson;

import kz.kmg.qorgau.data.Constants;
import kz.kmg.qorgau.data.network.RetrofitModule;
import kz.kmg.qorgau.data.network.base.ApiError;
import kz.kmg.qorgau.data.network.base.Resource;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ErrorHelper {

    public static Response getErrorResponse(Throwable throwable) {
        if (throwable instanceof RetrofitModule.NoConnectivityException) {
            ApiError apiError = new ApiError();
            apiError.setMessage(throwable.getMessage());
            return Response.error(Constants.NO_INTERNET_ERROR, ResponseBody.create(
                    apiError.toString(), MediaType.parse("application/json")));
        } else {
            ApiError apiError = new ApiError();
            apiError.setMessage(throwable.getMessage());
            return Response.error(Constants.UNKNOWN_ERROR, ResponseBody.create(
                    apiError.toString(), MediaType.parse("application/json")));
        }
    }


    public static <T> Resource<T> getQorgayApiError(Response response) {
        Gson gson = new Gson();
        ApiError apiError = new ApiError();
        try {
            apiError = gson.fromJson(response.errorBody().string(), ApiError.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        apiError.setCode(response.code());
        if (response.code()==Constants.NOT_FOUND) {
            //return Resource.<T>error(new ApiError(Constants.CITY_NOT_FOUND_MESSAGE,Constants.NOT_FOUND));
            return Resource.<T>error(apiError);
        } else {
            return Resource.<T>error(apiError);
        }
    }


}
