package kz.kmg.qorgau.data.model.home;

import kz.kmg.qorgau.data.model.UserModel;

public class NewsModel {

    private UserModel user;
    private long dateTimeInMillis;
    private String content;

    public UserModel getUser() {
        return user;
    }

    public long getDateTimeInMillis() {
        return dateTimeInMillis;
    }

    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    private String imgUrl;

    public NewsModel(UserModel user, long dateTimeInMillis, String content, String imgUrl) {
        this.user = user;
        this.dateTimeInMillis = dateTimeInMillis;
        this.content = content;
        this.imgUrl = imgUrl;
    }
}
