package kz.kmg.qorgau.data.model.work_observations;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AnswerCategoriesItem{

	@SerializedName("Children")
	private List<ChildrenItem> children;

	@SerializedName("Code")
	private String code;

	@SerializedName("Name")
	private String name;

	@SerializedName("BackgroundColor")
	private String backgroundColor;

	public List<ChildrenItem> getChildren(){
		return children;
	}

	public String getCode(){
		return code;
	}

	public String getName(){
		return name;
	}

	public String getBackgroundColor(){
		return backgroundColor;
	}
}