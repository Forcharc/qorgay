package kz.kmg.qorgau.data.model.home;

import com.google.gson.annotations.SerializedName;

public class NewsTopModel{

	@SerializedName("Url")
	private String url;

	@SerializedName("Name")
	private String name;

	public String getUrl(){
		return url;
	}

	public String getName(){
		return name;
	}
}