package kz.kmg.qorgau.data.model.work_observations;

import com.google.gson.annotations.SerializedName;

public class ChildrenItem{

	@SerializedName("Children")
	private Object children;

	@SerializedName("Code")
	private String code;

	@SerializedName("Name")
	private String name;

	@SerializedName("BackgroundColor")
	private Object backgroundColor;

	private transient String comment;
	private transient Integer category = 0;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}


	public Object getChildren(){
		return children;
	}

	public String getCode(){
		return code;
	}

	public String getName(){
		return name;
	}

	public Object getBackgroundColor(){
		return backgroundColor;
	}
}