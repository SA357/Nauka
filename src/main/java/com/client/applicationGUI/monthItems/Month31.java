package com.client.applicationGUI.monthItems;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Month31 extends Month30{
    @FXML
    private SimpleStringProperty day31;

    public String getDay31() {
        return day31.get();
    }

    public SimpleStringProperty day31Property() {
        return day31;
    }
}
