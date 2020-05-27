package com.client.applicationGUI;

import com.DB;
import com.client.applicationGUI.GUI;
import javafx.application.Application;
import javafx.stage.Stage;
import com.network.client.Account;


import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.Random;

public class ClientApp extends Application {

    private static InetSocketAddress serverAddress = new InetSocketAddress("localhost", 10000);

    public static InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(InetSocketAddress serverAddress) {
        ClientApp.serverAddress = serverAddress;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Files.deleteIfExists(Paths.get("DB.db"));

        if(!Files.exists(Paths.get("DB.db"))) {
            new DB().create();
        }
//        Account.setClientServerPartPort(new Random().nextInt(10000) + 6000);
//        Thread clientServerPart = new Thread(new ClientServerPart(Account.getClientServerPartPort()));
//        clientServerPart.start();
//        Registration registration = new Registration();
//        registration.init();
//        ResultSet rs=new DB().getConnection().createStatement().executeQuery("select * from Calendar");
//        while (rs.next()){
//            System.out.println(rs.getDate(1) + " " + rs.getString(2));
//        }
        GUI gui = new GUI();
        gui.init();
        GUI.getStage().show();
//        Registration.getStage().show();
    }
}
