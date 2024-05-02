package com.example.Server.messaging;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class ListOfVideoTemp {
    private int id;
    private String nameVideo;
    private String linkInVideo;
    public ListOfVideoTemp(){}
    public ListOfVideoTemp(int id, String nameVideo, String linkInVideo) {
        this.id = id;
        this.nameVideo = nameVideo;
        this.linkInVideo = linkInVideo;
    }
    public ListOfVideoTemp(String nameVideo, String linkInVideo) {
        this.nameVideo = nameVideo;
        this.linkInVideo = linkInVideo;
    }
}
