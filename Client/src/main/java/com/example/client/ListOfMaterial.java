package com.example.client;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Класс для учебных материалов
 * тесно взаимосвязан с таблицей FX во вкладке "Учебный материал"
 */
public class ListOfMaterial {
    //Порядковый номер учебного материала
    private StringProperty id;
    //Наименование самого материала
    private StringProperty materialName;
    public ListOfMaterial(String id, String materialName) {
        this.id = new SimpleStringProperty(this, "id", id);
        this.materialName = new SimpleStringProperty(this, "materialName", materialName);
    }
    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public String getMaterialName() {
        return materialName.get();
    }
    public void setMaterialName(String materialName) {
        this.materialName.set(materialName);
    }
    public StringProperty idProperty() {
        return id;
    }
    public StringProperty materialNameProperty() {
        return materialName;
    }
}
