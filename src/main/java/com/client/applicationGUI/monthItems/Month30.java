package com.client.applicationGUI.monthItems;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class Month30 extends Month29{
    @FXML
    private SimpleStringProperty day30;

    public String getDay30() {
        return day30.get();
    }

    public SimpleStringProperty day30Property() {
        return day30;
    }
}
