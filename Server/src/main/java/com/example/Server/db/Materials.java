package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для взаимодействия, для материалов
 */
@Setter
@Getter
@Entity
@Table(name = "materials")
public class Materials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "pdf_file")
    private byte[] pdf_file;
    public Materials(){}
    public Materials(String name, byte[] pdf_file) {
        this.name = name;
        this.pdf_file = pdf_file;
    }
}
