package com.client.applicationGUI;

import com.DB;
import com.client.applicationGUI.monthItems.MonthTableItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class AdminTableItem {
    @FXML private SimpleStringProperty userName;
    @FXML private SimpleStringProperty data;
    @FXML private SimpleStringProperty words;
    AdminTableItem(String userName, String data, String words) {
        this.userName = new SimpleStringProperty(userName);
        this.data = new SimpleStringProperty(data);
        this.words = new SimpleStringProperty(words);
    }
    SimpleStringProperty userNameProperty(){return userName;}
    SimpleStringProperty dataProperty(){return data;}
    SimpleStringProperty wordsProperty(){return words;}
}

public class GUIController {
    private DB db = new DB();
//    @FXML private TextField enemyNameTextField;
//    @FXML private ChoiceBox<String> weaponChoiceBox;
//    @FXML private ImageView weaponImage;
//    @FXML private DatePicker datePicker;
//    @FXML private TextField userName;
//    @FXML private TextField words;
//
//    @FXML private TableView<AdminTableItem> queryTable;
//    @FXML private TableColumn<AdminTableItem, String> columnDate;
//    @FXML private TableColumn<AdminTableItem, String> columnAuthor;
//    @FXML private TableColumn<AdminTableItem, String> columnMsg;
//
//    @FXML private TextField newName;
//    @FXML private TextField newPort;
//    @FXML private TextField newPassword;
//    @FXML private TextField textField;
//    @FXML private TextArea textArea;
//    @FXML private TextArea activeUsers;
//    @FXML private TextArea activeUsersCopy;
//    @FXML private Tab adminTab;
    @FXML private TabPane monthsPane;
    @FXML private VBox buttonVBox;
//    private Tab[] tabs = {new Tab("Январь"), new Tab("Февраль"),  new Tab("Март"), new Tab("Апрель"),
//            new Tab("Май"), new Tab("Июнь"), new Tab("Июль"), new Tab("Август"), new Tab("Сентябрь"),
//            new Tab("Октябрь"), new Tab("Ноябрь"), new Tab("Декабрь")};
    String[] months = {null, "Январь", "Февраль",  "Март", "Апрель",
            "Май", "Июнь", "Июль", "Август", "Сентябрь",
            "Октябрь", "Ноябрь", "Декабрь"};
    private static GUIController instance;
    private Set<Button> buttons = new HashSet<>();

    @FXML private void initialize() {
//        columnDate.setCellValueFactory(x -> x.getValue().dataProperty());
//        columnAuthor.setCellValueFactory(x -> x.getValue().userNameProperty());
//        columnMsg.setCellValueFactory(x -> x.getValue().wordsProperty());
//

        departmentInitialize();
        //tableInitialize();
    }

    private void departmentInitialize(){
        try {
            Set<String> departments = db.getDepartments();
            for (String department : departments) {
                Button button = new Button(department);
                button.setPrefSize(400, 50);
                buttons.add(button);
                button.setOnAction(event -> {
                    for (Button b : buttons) {
                        if (!b.equals(button)){
                            b.getStylesheets().clear();
                        }
                    }
                    button.getStylesheets().add("css/green_button.css");
                    changeDepartment(button.getText());
                });
            }
            buttonVBox.getChildren().addAll(buttons);

            Button b = ((Button)buttons.toArray()[0]);
            b.fire();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private void changeDepartment(String department) {
        try {
            Map<Integer, List<String>> map = db.getCalendar();
            monthsPane.getTabs().clear();
            for (int month : map.keySet()) {
                Node tableView = getTableView(map, month, department);
                Tab tab = new Tab(months[month], tableView);
                monthsPane.getTabs().add(tab);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Node getTableView(Map<Integer, List<String>> map, int month, String department) throws SQLException {
        int amountOfDays = map.get(month).size();
        switch (amountOfDays){
            case 28:
                return getMonth28TableView(month, department);
            case 29:
                return getMonth29TableView(month, department);
            case 30:
                return getMonth30TableView(month, department);
            case 31:
                return getMonth31TableView(month, department);
            default: throw new RuntimeException("!!! " + amountOfDays);
        }
    }

    private TableView<MonthTableItem> getMonth28TableView(int month, String department) throws SQLException {
        TableView<MonthTableItem> tableView = new TableView<>();
        Set<Integer> employeesId = db.getEmployeesId(department);
        ObservableList<MonthTableItem> list = getObservableList(month, employeesId);
        tableView.setItems(list);

        TableColumn<MonthTableItem, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("emp_name"));
        tableView.getColumns().add(nameColumn);

        TableColumn<MonthTableItem, String> positionColumn = new TableColumn<>("Должность");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("emp_position"));
        tableView.getColumns().add(positionColumn);

        TableColumn<MonthTableItem, String> idColumn = new TableColumn<>("Табельный №");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("emp_id"));
        tableView.getColumns().add(idColumn);

        for (int i = 1; i <= 28; i++) {
            TableColumn<MonthTableItem, String> dayI = new TableColumn<>(String.valueOf(i));
            dayI.setCellValueFactory(new PropertyValueFactory<>("day"+ i));
            tableView.getColumns().add(dayI);
        }
        TableColumn<MonthTableItem, String> summaryColumn = new TableColumn<>("Итого");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        tableView.getColumns().add(summaryColumn);
        return tableView;
    }

    private TableView<MonthTableItem> getMonth29TableView(int month, String department) throws SQLException {
        TableView<MonthTableItem> tableView = getMonth28TableView(month, department);
        TableColumn<MonthTableItem, String> dayI = new TableColumn<>(String.valueOf(29));
        dayI.setCellValueFactory(new PropertyValueFactory<>("day"+ 29));
        tableView.getColumns().add(dayI);
        return tableView;
    }

    private TableView<MonthTableItem> getMonth30TableView(int month, String department) throws SQLException {
        TableView<MonthTableItem> tableView = getMonth29TableView(month, department);
        TableColumn<MonthTableItem, String> dayI = new TableColumn<>(String.valueOf(30));
        dayI.setCellValueFactory(new PropertyValueFactory<>("day"+ 30));
        tableView.getColumns().add(dayI);
        return tableView;
    }

    private TableView<MonthTableItem> getMonth31TableView(int month, String department) throws SQLException {
        TableView<MonthTableItem> tableView = getMonth28TableView(month, department);
        TableColumn<MonthTableItem, String> dayI = new TableColumn<>(String.valueOf(31));
        dayI.setCellValueFactory(new PropertyValueFactory<>("day"+ 31));
        tableView.getColumns().add(dayI);
        return tableView;
    }

    private ObservableList<MonthTableItem> getObservableList(int month, Set<Integer> employeesId) throws SQLException {
        ObservableList<MonthTableItem> list = FXCollections.observableArrayList();
        for (int id : employeesId) {

            list.add(new MonthTableItem(
                    db.getEmployeesFullName(id),
                    db.getEmployeesPosition(id),
                    String.valueOf(id),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 1)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 2)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 3)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 4)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 5)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 6)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 7)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 8)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 9)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 10)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 11)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 12)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 13)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 14)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 15)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 16)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 17)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 18)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 19)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 20)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 21)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 22)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 23)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 24)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 25)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 26)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 27)),
                    db.getMark(id, new Date(LocalDate.now().getYear(), month, 28))
            ));
        }
        return list;
    }


//    public void setAdmin() {
//        Platform.runLater(() -> adminTab.setDisable(false));
//    }

//    public void setUser() {
//        Platform.runLater(() -> tabPane.getTabs().remove(adminTab));
//    }

    public static GUIController getInstance() {
        return instance;
    }

    public static void setInstance(GUIController instance) {
        GUIController.instance = instance;
    }

//    @FXML private void sendMessage() {
//        String text = textField.getText();
//        new Thread(() -> {
//            try {
//                transport.sendMessage_CRYPTED(new TextMessage(text, Account.getName()),
//                        ClientApp.getServerAddress(), Account.getPassword());
//            } catch (Exception e) {
//                Platform.runLater(() -> {
//                    Alert alert = new Alert(Alert.AlertType.WARNING);
//                    alert.setTitle("ОЙ-ой-ОЙ");
//                    alert.setHeaderText(null);
//                    alert.setContentText(e.getMessage());
//                    alert.showAndWait();
//                });
//                e.printStackTrace();
//            }
//        }).start();
//        Platform.runLater(() -> textField.setText(""));
//    }

//    @FXML private void changeSettings() {
//        String password2 = newPassword.getText().equals("") ? Account.getPassword() : newPassword.getText();
//        String name2 = newName.getText().equals("") ? Account.getName() : newName.getText();
//        int port2 = newPort.getText().equals("") ? Account.getClientServerPartPort() : Integer.parseInt(newPort.getText());
//        new Thread(() -> {
//            try {
//                SettingReplyMessage check = (SettingReplyMessage) transport.sendAndReceive_CRYPTED(new SettingMessage(Account.getName(),
//                        name2, password2, new InetSocketAddress("localhost", port2)), ClientApp.getServerAddress(), Account.getPassword());
//                if (check.isChanged()) {
//                    Account.setName(name2);
//                    Account.setClientServerPartPort(port2);
//                    Account.setPassword(password2);
//                    ClientServerPart.shutdown();
//                    ClientServerPart clientServerPart = new ClientServerPart(port2);
//                    Thread.sleep(100);
//                    ClientServerPart.reload();
//                    new Thread(clientServerPart).start();
//                } else {
//                    Platform.runLater(() -> {
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("ОЙ-ой-ОЙ");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Не получилося, воть так воть(((");
//                        alert.showAndWait();
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Platform.runLater(() -> {
//                newPassword.setText("");
//                newName.setText("");
//                newPort.setText("");
//            });
//        }).start();
//    }

//    @FXML private void adminQuery() {
//        LocalDate date1 = datePicker.getValue();
//        String name1 = userName.getText();
//        String words1 = words.getText();
//        System.out.println("СЧИТАЛ ЗАПРОС");
//        try {
//            Date date2 = (date1 != null) ? Date.valueOf(date1) : null;
//            AdminQueryReplyMessage replyMessage = (AdminQueryReplyMessage) transport
//                    .sendAndReceive_CRYPTED(
//                            new AdminQueryMessage(Account.getName(), name1, words1, date2), ClientApp.getServerAddress(), Account.getPassword());
////            System.out.println(replyMessage.getList());
//            Platform.runLater(() -> {
//                ObservableList<AdminTableItem> list = FXCollections.observableArrayList();
//                for (AdminQueryReplyMessage.Entry entry : replyMessage.getList()) {
//                    list.add(new AdminTableItem(entry.getNameOfUser(), entry.getDate(), entry.getWords()));
//                    queryTable.setItems(list);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void showMessage(String line) {
//        Platform.runLater(() -> textArea.appendText(line + "\n"));
//    }
//
//    public void addActiveClient(String name) {
//        Platform.runLater(() -> activeUsers.appendText(name + "\n"));
//    }
//
//    public void deleteActiveClient(String name) {
//        String line = activeUsers.getText();
//        Platform.runLater(() -> {
//            activeUsers.setText("");
//            Arrays.stream(line.split("[\n]")).filter(x -> !x.equals(name)).forEach(x -> activeUsers.appendText(x + "\n"));
//        });
//    }
}