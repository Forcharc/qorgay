package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

public class IsSuccessResponse {

	@SerializedName("success")
	private Boolean isSuccess;

	@SerializedName("message")
	private String message;


	public Boolean getIsSuccess(){
		return isSuccess;
	}

}