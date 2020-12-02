package kz.kmg.qorgau.data.model.questionnaires;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuestionnaireFormModel{

	@SerializedName("OrganizationName")
	private String organizationName;

	@SerializedName("Type")
	private Integer type;

	@SerializedName("Description")
	private String description;

	@SerializedName("Username")
	private String username;

	@SerializedName("Questions")
	private List<QuestionsItem> questions;

	@SerializedName("Id")
	private Integer id;

	@SerializedName("Name")
	private String name;

	public String getOrganizationName(){
		return organizationName;
	}

	public Integer getType(){
		return type;
	}

	public String getDescription(){
		return description;
	}

	public String getUsername(){
		return username;
	}

	public List<QuestionsItem> getQuestions(){
		return questions;
	}

	public Integer getId(){
		return id;
	}

	public String getName(){
		return name;
	}
}