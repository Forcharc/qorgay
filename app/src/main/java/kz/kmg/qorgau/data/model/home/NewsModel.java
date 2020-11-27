package kz.kmg.qorgau.data.model.home;

import com.google.gson.annotations.SerializedName;

public class NewsModel{

	@SerializedName("PublishDate")
	private String publishDate;

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("Text")
	private String text;

	@SerializedName("Id")
	private int id;

	@SerializedName("Url")
	private String url;

	@SerializedName("Name")
	private String name;

	public String getPublishDate(){
		return publishDate;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public String getText(){
		return text;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}

	public String getName(){
		return name;
	}
}