package kz.kmg.qorgau.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    private String name;
    private String surname;
    private String avatarUrl;

    public UserModel(int id, String name, String surname, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.avatarUrl = avatarUrl;
    }


}
