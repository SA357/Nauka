package com.client.applicationGUI.monthItems;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Month28 extends MonthItem{
    @FXML private SimpleStringProperty day28;

    public String getDay28() {
        return day28.get();
    }

    public SimpleStringProperty day28Property() {
        return day28;
    }
}
