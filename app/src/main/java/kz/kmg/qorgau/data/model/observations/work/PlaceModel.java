package kz.kmg.qorgau.data.model.observations.work;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.SearchableId;

public class PlaceModel implements SearchableId {

	@SerializedName("UpdateDate")
	private String updateDate;

	@SerializedName("NameEn")
	private String nameEn;

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("NameKz")
	private String nameKz;

	@SerializedName("Latitude")
	private int latitude;

	@SerializedName("Id")
	private int id;

	@SerializedName("OrganizationId")
	private int organizationId;

	@SerializedName("NameRu")
	private String nameRu;

	@SerializedName("Longtitude")
	private int longtitude;

	public String getUpdateDate(){
		return updateDate;
	}


	public String getNameEn(){
		return nameEn;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public String getNameKz(){
		return nameKz;
	}

	public int getLatitude(){
		return latitude;
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

	public int getLongtitude(){
		return longtitude;
	}

	@Override
	public String getTitle() {
		return nameRu;
	}
}