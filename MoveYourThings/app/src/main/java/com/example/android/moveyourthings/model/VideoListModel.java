package com.example.android.moveyourthings.model;

public class VideoListModel {
    private String name;
    private String video_path;
    private String video_duration;
    private String video_thumb;
    private String datetime;
    private String user_id;
    private String uplodedStatus;


    public String getName() {
        return name;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(String video_duration) {
        this.video_duration = video_duration;
    }

    public String getVideo_thumb() {
        return video_thumb;
    }

    public void setVideo_thumb(String video_thumb) {
        this.video_thumb = video_thumb;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUplodedStatus() {
        return uplodedStatus;
    }

    public void setUplodedStatus(String uplodedStatus) {
        this.uplodedStatus = uplodedStatus;
    }

    public void setName(String name) {
        this.name = name;

    }


}
