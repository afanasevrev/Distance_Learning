package com.example.client.video;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс видеоматериалов для взаимодействия с сервером
 */
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
}
