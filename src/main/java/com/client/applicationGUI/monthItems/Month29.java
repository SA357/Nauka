package com.client.applicationGUI.monthItems;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Month29 extends Month28{
    @FXML
    private SimpleStringProperty day29;

    public String getDay29() {
        return day29.get();
    }

    public SimpleStringProperty day29Property() {
        return day29;
    }
}
