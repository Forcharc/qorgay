package kz.kmg.qorgau.data.model.observations.work;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.SearchableId;

public class PlaceItemModel implements SearchableId {
	@SerializedName("PlaceId")
	private int placeId;
	private String nameEn;
	private String nameKz;
	private int latitude;
	@SerializedName("Id")
	private int id;
	@SerializedName("NameRu")
	private String nameRu;
	private int longtitude;
	private String place;

	public int getPlaceId(){
		return placeId;
	}

	public String getNameEn(){
		return nameEn;
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

	public String getNameRu(){
		return nameRu;
	}

	public int getLongtitude(){
		return longtitude;
	}

	public String getPlace(){
		return place;
	}

	@Override
	public String getTitle() {
		return nameRu;
	}
}
