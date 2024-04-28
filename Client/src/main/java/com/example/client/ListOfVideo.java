package com.example.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListOfVideo {
    private StringProperty id;
    private StringProperty videoName;
    private StringProperty linkInVideo;
    public ListOfVideo(String id, String videoName, String linkInVideo) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.videoName = new SimpleStringProperty(this, "videoName", videoName);
        this.linkInVideo = new SimpleStringProperty(this, "linkInVideo", linkInVideo);
    }
    
}
