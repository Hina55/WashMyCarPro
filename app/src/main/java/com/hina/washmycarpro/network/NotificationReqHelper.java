package com.hina.washmycarpro.network;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NotificationReqHelper {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("collapse_key")
    @Expose
    private String collapseKey;
    @SerializedName("notification")
    @Expose
    private Notification notification;

    public NotificationReqHelper(String token, Notification notification) {
        this.to = token;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public static class Notification {

        @SerializedName("body")
        @Expose
        private String body;
        @SerializedName("title")
        @Expose
        private String title;

        public Notification(String title, String text) {
            this.title = title;
            this.body=text;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }


}