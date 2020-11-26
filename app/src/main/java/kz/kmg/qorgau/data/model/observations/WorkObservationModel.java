package kz.kmg.qorgau.data.model.observations;

import com.google.gson.annotations.SerializedName;

public class WorkObservationModel {

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


    public Integer getId() {
        return id;
    }

    public Long getDate() {
        if (dateCard != null) {
            return Long.parseLong(dateCard.substring(6, dateCard.length() - 2));
        } else {
            return null;
        }
    }

    public String getOrgName() {
        return organizationName;
    }
}