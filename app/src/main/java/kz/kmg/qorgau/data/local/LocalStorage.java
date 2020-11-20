package kz.kmg.qorgau.data.local;


public interface LocalStorage {

    void setNotificationToken(String notificationToken);
    String getNotificationToken();

    void setCookie(String cookie);
    String getCookie();
}
