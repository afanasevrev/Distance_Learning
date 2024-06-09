package com.example.client_correct.material;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ListOfMaterialTemp {
    private String id;
    private String name;
    public ListOfMaterialTemp() {}
    public ListOfMaterialTemp(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
