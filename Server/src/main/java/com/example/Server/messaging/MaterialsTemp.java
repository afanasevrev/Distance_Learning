package com.example.Server.messaging;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class MaterialsTemp {
    private int id;
    private String name;
    public MaterialsTemp(){}
    public MaterialsTemp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
