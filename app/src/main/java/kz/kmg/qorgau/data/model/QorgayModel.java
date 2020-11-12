package kz.kmg.qorgau.data.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class QorgayModel implements Serializable {
        int dictKorgauObservationTypeId;
        String fullName;
        String incidentDateTime;
        int organizationId;
        int organizationDepartmentId;
        int supervisedOrganizationId;
        ArrayList<Integer> dictKorgauObservationCategories;
        String suggestion;
        ArrayList<File> files;
        String possibleConsequence;
        String measure;
        String actionToEncourage;
        boolean isDiscussed;
        boolean isInformed;
        String informTo;
        boolean isEliminated;



        public QorgayModel(int dictKorgauObservationTypeId, String fullName, String incidentDateTime, int organizationId, int organizationDepartmentId, int supervisedOrganizationId, ArrayList<Integer> dictKorgauObservationCategories, String suggestion, ArrayList<File> files, String possibleConsequence, String measure, String actionToEncourage, boolean isDiscussed, boolean isInformed, String informTo, boolean isEliminated) {
                this.dictKorgauObservationTypeId = dictKorgauObservationTypeId;
                this.fullName = fullName;
                this.incidentDateTime = incidentDateTime;
                this.organizationId = organizationId;
                this.organizationDepartmentId = organizationDepartmentId;
                this.supervisedOrganizationId = supervisedOrganizationId;
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

        public int getDictKorgauObservationTypeId() {
                return dictKorgauObservationTypeId;
        }

        public void setDictKorgauObservationTypeId(int dictKorgauObservationTypeId) {
                this.dictKorgauObservationTypeId = dictKorgauObservationTypeId;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getIncidentDateTime() {
                return incidentDateTime;
        }

        public void setIncidentDateTime(String incidentDateTime) {
                this.incidentDateTime = incidentDateTime;
        }

        public int getOrganizationId() {
                return organizationId;
        }

        public void setOrganizationId(int organizationId) {
                this.organizationId = organizationId;
        }

        public int getOrganizationDepartmentId() {
                return organizationDepartmentId;
        }

        public void setOrganizationDepartmentId(int organizationDepartmentId) {
                this.organizationDepartmentId = organizationDepartmentId;
        }

        public int getSupervisedOrganizationId() {
                return supervisedOrganizationId;
        }

        public void setSupervisedOrganizationId(int supervisedOrganizationId) {
                this.supervisedOrganizationId = supervisedOrganizationId;
        }

        public ArrayList<Integer> getDictKorgauObservationCategories() {
                return dictKorgauObservationCategories;
        }

        public void setDictKorgauObservationCategories(ArrayList<Integer> dictKorgauObservationCategories) {
                this.dictKorgauObservationCategories = dictKorgauObservationCategories;
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

        public void setFiles(ArrayList<File> files) {
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

        public boolean isDiscussed() {
                return isDiscussed;
        }

        public void setDiscussed(boolean discussed) {
                isDiscussed = discussed;
        }

        public boolean isInformed() {
                return isInformed;
        }

        public void setInformed(boolean informed) {
                isInformed = informed;
        }

        public String getInformTo() {
                return informTo;
        }

        public void setInformTo(String informTo) {
                this.informTo = informTo;
        }

        public boolean isEliminated() {
                return isEliminated;
        }

        public void setEliminated(boolean eliminated) {
                isEliminated = eliminated;
        }
}
