package kz.kmg.qorgau.data.model.observations.work;

import com.google.gson.annotations.SerializedName;

import kz.kmg.qorgau.data.model.observations.BaseObservationModel;

public class WorkObservationModel implements BaseObservationModel {

    @SerializedName("OrganizationName")
    private String organizationName;

    @SerializedName("OrganizationDepartmentName")
    private String organizationDepartmentName;

    @SerializedName("AuthorFullname")
    private String authorFullname;

    @SerializedName("Task")
    private String task;

    @SerializedName("DateCard")
    private String dateCard;

    @SerializedName("Id")
    private int id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Long getDate() {
        if (dateCard != null) {
            return Long.parseLong(dateCard.substring(6, dateCard.length() - 2));
        } else {
            return null;
        }
    }

    @Override
    public String getOrgName() {
        return organizationName;
    }
}