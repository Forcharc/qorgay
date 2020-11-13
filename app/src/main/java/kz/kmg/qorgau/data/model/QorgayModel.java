package kz.kmg.qorgau.data.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class QorgayModel implements Serializable {
    Integer dictKorgauObservationTypeId;
    String fullName;
    String phone;
    String incidentDateTime;
    Integer organizationId;
    Boolean isContractor;
    String contractor;
    Integer organizationDepartmentId;
    Integer supervisedOrganizationId;
    String supervisedObject;
    Integer[] dictKorgauObservationCategories;
    String suggestion;
    File[] files;
    String possibleConsequence;
    String measure;
    String actionToEncourage;
    Boolean isDiscussed;
    Boolean isInformed;
    String informTo;
    Boolean isEliminated;


    public QorgayModel(
            Integer dictKorgauObservationTypeId,
            String fullName,
            String phone,
            String incidentDateTime,
            Integer organizationId,
            Boolean isContractor,
            String contractor,
            Integer organizationDepartmentId,
            Integer supervisedOrganizationId,
            String supervisedObject,
            Integer[] dictKorgauObservationCategories,
            String suggestion, 
            File[] files,
            String possibleConsequence, 
            String measure, 
            String actionToEncourage, 
            Boolean isDiscussed, 
            Boolean isInformed, 
            String informTo, 
            Boolean isEliminated
    ) {
        this.dictKorgauObservationTypeId = dictKorgauObservationTypeId;
        this.fullName = fullName;
        this.phone = phone;
        this.incidentDateTime = incidentDateTime;
        this.organizationId = organizationId;
        this.isContractor = isContractor;
        this.contractor = contractor;
        this.organizationDepartmentId = organizationDepartmentId;
        this.supervisedOrganizationId = supervisedOrganizationId;
        this.supervisedObject = supervisedObject;
        this.dictKorgauObservationCategories = dictKorgauObservationCategories;
        this.suggestion = suggestion;
        this.files = files;
        this.possibleConsequence = possibleConsequence;
        this.measure = measure;
        this.actionToEncourage = actionToEncourage;
        this.isDiscussed = isDiscussed;
        this.isInformed = isInformed;
        this.informTo = informTo;
        this.isEliminated = isEliminated;
    }

    public Integer getDictKorgauObservationTypeId() {
        return dictKorgauObservationTypeId;
    }

    public void setDictKorgauObservationTypeId(Integer dictKorgauObservationTypeId) {
        this.dictKorgauObservationTypeId = dictKorgauObservationTypeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIncidentDateTime() {
        return incidentDateTime;
    }

    public void setIncidentDateTime(String incidentDateTime) {
        this.incidentDateTime = incidentDateTime;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getOrganizationDepartmentId() {
        return organizationDepartmentId;
    }

    public void setOrganizationDepartmentId(Integer organizationDepartmentId) {
        this.organizationDepartmentId = organizationDepartmentId;
    }

    public Integer getSupervisedOrganizationId() {
        return supervisedOrganizationId;
    }

    public void setSupervisedOrganizationId(Integer supervisedOrganizationId) {
        this.supervisedOrganizationId = supervisedOrganizationId;
    }

    public Integer[] getDictKorgauObservationCategories() {
        return dictKorgauObservationCategories;
    }

    public void setDictKorgauObservationCategories(ArrayList<Integer> dictKorgauObservationCategories) {
        this.dictKorgauObservationCategories = dictKorgauObservationCategories.toArray(new Integer[0]);
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String getPossibleConsequence() {
        return possibleConsequence;
    }

    public void setPossibleConsequence(String possibleConsequence) {
        this.possibleConsequence = possibleConsequence;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getActionToEncourage() {
        return actionToEncourage;
    }

    public void setActionToEncourage(String actionToEncourage) {
        this.actionToEncourage = actionToEncourage;
    }

    public Boolean isDiscussed() {
        return isDiscussed;
    }

    public void setDiscussed(Boolean discussed) {
        isDiscussed = discussed;
    }

    public Boolean isInformed() {
        return isInformed;
    }

    public void setInformed(Boolean informed) {
        isInformed = informed;
    }

    public String getInformTo() {
        return informTo;
    }

    public void setInformTo(String informTo) {
        this.informTo = informTo;
    }

    public Boolean isEliminated() {
        return isEliminated;
    }

    public Boolean getContractor() {
        return isContractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getSupervisedObject() {
        return supervisedObject;
    }

    public void setSupervisedObject(String supervisedObject) {
        this.supervisedObject = supervisedObject;
    }

    public void setDictKorgauObservationCategories(Integer[] dictKorgauObservationCategories) {
        this.dictKorgauObservationCategories = dictKorgauObservationCategories;
    }

    public Boolean getDiscussed() {
        return isDiscussed;
    }

    public Boolean getInformed() {
        return isInformed;
    }

    public Boolean getEliminated() {
        return isEliminated;
    }

    public void setContractor(Boolean contractor) {
        isContractor = contractor;
    }

    public void setEliminated(Boolean eliminated) {
        isEliminated = eliminated;
    }
}
