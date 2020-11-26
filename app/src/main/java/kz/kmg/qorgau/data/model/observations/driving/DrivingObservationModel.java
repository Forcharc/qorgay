package kz.kmg.qorgau.data.model.observations.driving;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.observations.BaseObservationModel;

public class DrivingObservationModel implements BaseObservationModel {

	@SerializedName("OrganizationName")
	private String organizationName;

	@SerializedName("StartDate")
	private String startDate;

	@SerializedName("OrganizationDepartmentName")
	private String organizationDepartmentName;

	@SerializedName("AuthorFullname")
	private String authorFullname;

	@SerializedName("Id")
	private int id;

	@SerializedName("EndDate")
	private String endDate;

	@SerializedName("Location")
	private String location;


	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Long getDate() {
		if (startDate != null) {
			return Long.parseLong(startDate.substring(6, startDate.length() - 2));
		} else {
			return null;
		}
	}

	@Override
	public String getOrgName() {
		return organizationName;
	}
}