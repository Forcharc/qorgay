package kz.kmg.qorgau.data.model.list;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QorgayModel implements Serializable {

	@SerializedName("OrganizationName")
	private String organizationName;

	@SerializedName("IsInformed")
	private boolean isInformed;

	@SerializedName("OrganizationDepartmentId")
	private int organizationDepartmentId;

	@SerializedName("OrganizationId")
	private int organizationId;

	@SerializedName("Attachments")
	private List<AttachmentsItemModel> attachments;

	@SerializedName("PossibleConsequence")
	private String possibleConsequence;

	@SerializedName("Suggestion")
	private String suggestion;

	@SerializedName("Contractor")
	private String contractor;

	@SerializedName("DictKorgauObservationCategories")
	private Object dictKorgauObservationCategories;

	@SerializedName("IsDiscussed")
	private boolean isDiscussed;

	@SerializedName("Phone")
	private String phone;

	@SerializedName("Measure")
	private String measure;

	@SerializedName("IncidentDatetime")
	private String incidentDatetime;

	@SerializedName("Files")
	private Object files;

	@SerializedName("ActionToEncourage")
	private String actionToEncourage;

	@SerializedName("SupervisedOrganizationId")
	private int supervisedOrganizationId;

	@SerializedName("Status")
	private int status;

	@SerializedName("DictKorgauObservationTypeId")
	private int dictKorgauObservationTypeId;

	@SerializedName("InformTo")
	private String informTo;

	@SerializedName("PhoneUID")
	private String phoneUID;

	@SerializedName("IsContractor")
	private boolean isContractor;

	@SerializedName("IsEliminated")
	private boolean isEliminated;

	@SerializedName("FullName")
	private String fullName;

	@SerializedName("SupervisedObject")
	private String supervisedObject;

	@SerializedName("Id")
	private int id;

	public String getOrganizationName(){
		return organizationName;
	}

	public boolean isIsInformed(){
		return isInformed;
	}

	public int getOrganizationDepartmentId(){
		return organizationDepartmentId;
	}

	public int getOrganizationId(){
		return organizationId;
	}

	public List<AttachmentsItemModel> getAttachments(){
		return attachments;
	}

	public String getPossibleConsequence(){
		return possibleConsequence;
	}

	public String getSuggestion(){
		return suggestion;
	}

	public String getContractor(){
		return contractor;
	}

	public Object getDictKorgauObservationCategories(){
		return dictKorgauObservationCategories;
	}

	public boolean isIsDiscussed(){
		return isDiscussed;
	}

	public String getPhone(){
		return phone;
	}

	public String getMeasure(){
		return measure;
	}

	public String getIncidentDatetime(){
		return incidentDatetime;
	}

	public long getIncidentDateTimeLong() {
		if (incidentDatetime != null) {
			return Long.parseLong(incidentDatetime.substring(6, incidentDatetime.length() - 2));
		} else {
			return 0L;
		}
	}

	public Object getFiles(){
		return files;
	}

	public String getActionToEncourage(){
		return actionToEncourage;
	}

	public int getSupervisedOrganizationId(){
		return supervisedOrganizationId;
	}

	public int getStatus(){
		return status;
	}

	public int getDictKorgauObservationTypeId(){
		return dictKorgauObservationTypeId;
	}

	public String getInformTo(){
		return informTo;
	}

	public String getPhoneUID(){
		return phoneUID;
	}

	public boolean isIsContractor(){
		return isContractor;
	}

	public boolean isIsEliminated(){
		return isEliminated;
	}

	public String getFullName(){
		return fullName;
	}

	public String getSupervisedObject(){
		return supervisedObject;
	}

	public int getId(){
		return id;
	}
}