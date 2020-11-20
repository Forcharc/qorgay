package kz.kmg.qorgau.data.model.list;

import com.google.gson.annotations.SerializedName;

public class AttachmentsItemModel {

	@SerializedName("FileHumanName")
	private String fileHumanName;

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("Id")
	private int id;

	@SerializedName("MimeType")
	private Object mimeType;

	@SerializedName("FileSize")
	private int fileSize;

	public String getFileHumanName(){
		return fileHumanName;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public int getId(){
		return id;
	}

	public Object getMimeType(){
		return mimeType;
	}

	public int getFileSize(){
		return fileSize;
	}
}