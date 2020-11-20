package kz.kmg.qorgau.data.model.create;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CreateQorgayModel implements Serializable {
    Integer dictKorgauObservationTypeId;
    String fullName;
    String phone;
    Integer organizationId;
    Boolean isContractor;
    String contractor;
    Integer organizationDepartmentId;
    Integer supervisedOrganizationId;
    String supervisedObject;
    Set<Integer> dictKorgauObservationCategories = new HashSet<>();
    String suggestion;
    ArrayList<File> files = new ArrayList<>();
    String possibleConsequence;
    String measure;
    String actionToEncourage;
    Boolean isDiscussed;
    Boolean isInformed;
    String informTo;
    Boolean isEliminated;
    String date;
    String time;

    public CreateQorgayModel(
    ) {

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
        return ((date!= null)?(date + " "):"") + ((time!= null)?time:"");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public Set<Integer> getDictKorgauObservationCategories() {
        return dictKorgauObservationCategories;
    }


    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public ArrayList<File> getFiles() {
        return files;
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

    public Boolean getIsContractor() {
        return isContractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getContractor() {
        return this.contractor;
    }

    public String getSupervisedObject() {
        return supervisedObject;
    }

    public void setSupervisedObject(String supervisedObject) {
        this.supervisedObject = supervisedObject;
    }

    public void setDictKorgauObservationCategories(Set<Integer> dictKorgauObservationCategories) {
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

    public void setIsContractor(Boolean contractor) {
        isContractor = contractor;
    }

    public void setEliminated(Boolean eliminated) {
        isEliminated = eliminated;
    }
}
