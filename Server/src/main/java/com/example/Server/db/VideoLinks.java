package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для взаимодействия с БД для видеоуроков
 */
@Setter
@Getter
@Entity
@Table(name = "video_links")
public class VideoLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "link")
    private byte[] link;
    public VideoLinks(){}
    public VideoLinks(String name, byte[] link) {
        this.name = name;
        this.link = link;
    }
}
