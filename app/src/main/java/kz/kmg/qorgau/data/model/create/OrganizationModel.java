package kz.kmg.qorgau.data.model.create;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.SearchableId;

public class OrganizationModel implements SearchableId {

	@SerializedName("ParentId")
	private Object parentId;

	@SerializedName("IsDeleted")
	private boolean isDeleted;

	@SerializedName("Email")
	private Object email;

	@SerializedName("Description")
	private Object description;

	@SerializedName("BIN")
	private Object bIN;

	@SerializedName("InsertDate")
	private String insertDate;

	@SerializedName("PostalCode")
	private Object postalCode;

	@SerializedName("ShortName")
	private Object shortName;

	@SerializedName("OrganizationType")
	private int organizationType;

	@SerializedName("Name")
	private String name;

	@SerializedName("DictBranchId")
	private Object dictBranchId;

	@SerializedName("DictCityId")
	private Object dictCityId;

	@SerializedName("UpdateDate")
	private String updateDate;

	@SerializedName("Phone")
	private Object phone;

	@SerializedName("JurAddress")
	private Object jurAddress;

	@SerializedName("Id")
	private int id;

	public Object getParentId(){
		return parentId;
	}

	public boolean isIsDeleted(){
		return isDeleted;
	}

	public Object getEmail(){
		return email;
	}

	public Object getDescription(){
		return description;
	}

	public Object getBIN(){
		return bIN;
	}

	public String getInsertDate(){
		return insertDate;
	}

	public Object getPostalCode(){
		return postalCode;
	}

	public Object getShortName(){
		return shortName;
	}

	public int getOrganizationType(){
		return organizationType;
	}

	public String getName(){
		return name;
	}

	public Object getDictBranchId(){
		return dictBranchId;
	}

	public Object getDictCityId(){
		return dictCityId;
	}

	public String getUpdateDate(){
		return updateDate;
	}

	public Object getPhone(){
		return phone;
	}

	public Object getJurAddress(){
		return jurAddress;
	}

	public int getId(){
		return id;
	}

	@Override
	public String getTitle() {
		return name;
	}
}