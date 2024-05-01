package com.example.client.material;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс материалов для взаимодействия с сервером
 */
@Setter
@Getter
public class ListOfMaterialTemp {
    public String id;
    public String name;
    public ListOfMaterialTemp(){}
    public ListOfMaterialTemp(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
