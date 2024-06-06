package com.example.Server.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "materials_for_4_category")
public class MaterialsFor4Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "pdf_file")
    private byte[] pdf_file;
    public MaterialsFor4Category(){}
    public MaterialsFor4Category(String name, byte[] pdf_file) {
        this.name = name;
        this.pdf_file = pdf_file;
    }
}
