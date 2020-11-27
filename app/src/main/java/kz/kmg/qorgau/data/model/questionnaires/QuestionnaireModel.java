package kz.kmg.qorgau.data.model.questionnaires;

import com.google.gson.annotations.SerializedName;

public class QuestionnaireModel{

	@SerializedName("Type")
	private String type;

	@SerializedName("Description")
	private String description;

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("IsPassed")
	private boolean isPassed;

	@SerializedName("Id")
	private int id;

	@SerializedName("Name")
	private String name;

	public String getType(){
		return type;
	}

	public String getDescription(){
		return description;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public boolean isIsPassed(){
		return isPassed;
	}

	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}
}