package com.client.applicationGUI;

import com.network.client.Account;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class GUI {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public void init() throws IOException {
        stage = new Stage();
        stage.setTitle("NAUKA");
        stage.setWidth(900);
        stage.setHeight(700);
//        InputStream iconStream = getClass().getClassLoader().getResourceAsStream("icon.png");
//        assert iconStream != null;
//        Image image = new Image(iconStream);
//        stage.getIcons().add(image);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("GUIScene.fxml"));
        TabPane root = loader.load();
        GUIController.setInstance(loader.getController());
        Scene scene = new Scene(root, 800, 550);
        stage.setOnCloseRequest(event-> Platform.exit());
        stage.setScene(scene);
        stage.setResizable(false);
    }
}