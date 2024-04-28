package com.example.client;

import lombok.Getter;
import lombok.Setter;
/**
 * Класс материалов для взаимодействия с сервером
 */
@Setter
@Getter
public class ListOfMaterialTemp {
    private int id;
    private String materialName;
    public ListOfMaterialTemp(){}
    public ListOfMaterialTemp(int id, String materialName) {
        this.id = id;
        this.materialName = materialName;
    }
}
