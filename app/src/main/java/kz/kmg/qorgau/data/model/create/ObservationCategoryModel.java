package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

public class ObservationCategoryModel{

	@SerializedName("IsSelected")
	private boolean isSelected;

	@SerializedName("Id")
	private int id;

	@SerializedName("Name")
	private String name;

	public boolean isIsSelected(){
		return isSelected;
	}

	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}
}