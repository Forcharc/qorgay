package kz.kmg.qorgau.data.model.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationModel{

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("Title")
	private String title;

	@SerializedName("Id")
	private int id;

	@SerializedName("IsRead")
	private boolean isRead;

	public String getInsertDate(){
		return insertDate;
	}

	public String getTitle(){
		return title;
	}

	public int getId(){
		return id;
	}

	public boolean isIsRead(){
		return isRead;
	}
}