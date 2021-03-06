package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.SearchableId;
import kz.kmg.qorgau.data.model.SearchableIdModel;

public class DepartmentModel implements SearchableId {
	@SerializedName("OrganizationName")
	private Object organizationName;
	private String updateDate;
	@SerializedName("NameEn")
	private Object nameEn;
	@SerializedName("InsertDate")
	private String insertDate;
	@SerializedName("NameKz")
	private Object nameKz;
	@SerializedName("Id")
	private int id;
	@SerializedName("OrganizationId")
	private int organizationId;
	@SerializedName("NameRu")
	private String nameRu;


	/*
	public DepartmentModel(int id, String title) {
		super(id, title);
	}
*/

	public Object getOrganizationName(){
		return organizationName;
	}

	public String getUpdateDate(){
		return updateDate;
	}

	public Object getNameEn(){
		return nameEn;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public Object getNameKz(){
		return nameKz;
	}

	public int getId(){
		return id;
	}

	public int getOrganizationId(){
		return organizationId;
	}

	public String getNameRu(){
		return nameRu;
	}

	@Override
	public String getTitle() {
		return getNameRu();
	}
}
