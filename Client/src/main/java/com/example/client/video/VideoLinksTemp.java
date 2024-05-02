package com.example.client.video;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс для заполнения таблицы FX
 */
@Setter
@Getter
public class VideoLinksTemp {
    public String id;
    public String name;
    public String link;
    public VideoLinksTemp() {}
    public VideoLinksTemp(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }
}
