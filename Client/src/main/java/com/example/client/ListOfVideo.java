package com.example.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Класс для видеоматериалов,
 * Тесно связан с таблицей FX
 */
public class ListOfVideo {
    private StringProperty id;
    private StringProperty videoName;
    private StringProperty linkInVideo;
    public ListOfVideo(String id, String videoName, String linkInVideo) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.videoName = new SimpleStringProperty(this, "videoName", videoName);
        this.linkInVideo = new SimpleStringProperty(this, "linkInVideo", linkInVideo);
    }
    public String getId() {
        return id.get();
    }
    public StringProperty idProperty() {
        return id;
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public String getVideoName() {
        return videoName.get();
    }
    public StringProperty videoNameProperty() {
        return videoName;
    }
    public void setVideoName(String videoName) {
        this.videoName.set(videoName);
    }
    public String getLinkInVideo() {
        return linkInVideo.get();
    }
    public StringProperty linkInVideoProperty() {
        return linkInVideo;
    }
    public void setLinkInVideo(String linkInVideo) {
        this.linkInVideo.set(linkInVideo);
    }
}
