package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

public class CreateQorgayModel {

	@SerializedName("success")
	private Boolean isSuccess;


	public Boolean getIsSuccess(){
		return isSuccess;
	}

}