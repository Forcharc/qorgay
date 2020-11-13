package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

public class ObservationTypeModel {

	@SerializedName("LastChangeDate")
	private Object lastChangeDate;

	@SerializedName("Id")
	private int id;

	@SerializedName("Code")
	private Object code;

	@SerializedName("CreateDate")
	private String createDate;

	@SerializedName("Name")
	private String name;

	public Object getLastChangeDate(){
		return lastChangeDate;
	}

	public int getId(){
		return id;
	}

	public Object getCode(){
		return code;
	}

	public String getCreateDate(){
		return createDate;
	}

	public String getName(){
		return name;
	}
}