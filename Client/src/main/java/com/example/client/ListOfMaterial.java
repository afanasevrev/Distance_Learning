package com.example.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для учебных материалов
 * тесно взаимосвязан с таблицей FX
 */
@Setter
@Getter
public class ListOfMaterial {
    //Порядковый номер учебного материала
    private StringProperty id;
    //Наименование самого материала
    private StringProperty materialName;
    public ListOfMaterial(String id, String materialName) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.materialName = new SimpleStringProperty(this, "materialName", materialName);
    }
    public StringProperty idProperty() {
        return id;
    }
    public StringProperty materialNameProperty() {
        return materialName;
    }
}
