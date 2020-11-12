package kz.kmg.qorgau.data.network.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kz.kmg.qorgau.data.Constants;


public class ApiError implements Serializable {


    @SerializedName("code")
    @Expose
    private int code;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("subErrors")
    @Expose
    private List<SubError> subErrors;

    public ApiError() {
        this.message = "Unknown error";
        this.code = Constants.UNKNOWN_ERROR;
    }

    public ApiError(String message) {
        this.message = message;
        this.code = Constants.UNKNOWN_ERROR;
    }

    public ApiError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", subErrors=" + subErrors +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }

    public class SubError {
        @SerializedName("object")
        @Expose
        private String object;

        @SerializedName("field")
        @Expose
        private String field;

        @SerializedName("rejectedValue")
        @Expose
        private String rejectedValue;

        @SerializedName("message")
        @Expose
        private String message;


        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(String rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "{" +
                    "object='" + object + '\'' +
                    ", field='" + field + '\'' +
                    ", rejectedValue='" + rejectedValue + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
