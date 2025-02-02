package festival.client.gui;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AngajatView  implements IChatObserver{

    public Button b1;
    public Button b2;
    // public TextField text;
    ObservableList<Pair<String, String>> model = FXCollections.observableArrayList();

    IChatServices service;
    Angajat angajat;
    public void setService(IChatServices s,Angajat a) {
        angajat=a;
        service = s;
        initialize();
        f1();
    }

    public DatePicker calendar;

    public TextField textf1;
    public TextField textf2;
    public TextField textf3;
    public TextField textf4;
    public TextField textf5;
    public TextField textf6;


    @FXML
    TableView<TableRowData> tableView;
    @FXML
    TableColumn<TableRowData, String> tableColumn1;

    @FXML
    TableColumn<TableRowData, String> tableColumn2;
    @FXML
    TableColumn<TableRowData, String> tableColumn3;

    @FXML
    private TableColumn<TableRowData, String> tableColumn4;

    @FXML
    private TableColumn<TableRowData, String> tableColumn5;
    Stage dialogStage;
//    Client u;

//    ObservableList<TableRowData> model = FXCollections.observableArrayList();


    @FXML
    private TableView<TableRowData> tableView2;

    @FXML
    private TableColumn<TableRowData, String> tableColumn6;

    @FXML
    private TableColumn<TableRowData, String> tableColumn7;

    @FXML
    private TableColumn<TableRowData, String> tableColumn8;

    @FXML
    private TableColumn<TableRowData, String> tableColumn9;

    @FXML
    private TableColumn<TableRowData, String> tableColumn10;


    @FXML
    private void initialize() {

        tableColumn1.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana1()) : null);
        tableView.getColumns().add(tableColumn1);

        tableColumn2.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana2()) : null);
        tableView.getColumns().add(tableColumn2);

        tableColumn3.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana3()) : null);
        tableView.getColumns().add(tableColumn3);

        tableColumn4.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana4()) : null);
        tableView.getColumns().add(tableColumn4);

        tableColumn5.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana5()) : null);
        tableView.getColumns().add(tableColumn5);

        // Configurarea legăturilor pentru valorile celulelor în fiecare coloană
        tableColumn6.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana1()) : null);
        tableView2.getColumns().add(tableColumn6);

        tableColumn7.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana2()) : null);
        tableView2.getColumns().add(tableColumn7);

        tableColumn8.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana3()) : null);
        tableView2.getColumns().add(tableColumn8);

        tableColumn9.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana4()) : null);
        tableView2.getColumns().add(tableColumn9);

        tableColumn10.setCellValueFactory(data -> data.getValue() != null ?
                javafx.beans.binding.Bindings.createObjectBinding(() -> data.getValue().getColoana5()) : null);
        tableView2.getColumns().add(tableColumn10);


    }


    public void f1() {
        Platform.runLater(() -> {
            try {
                List<String> lista = service.findArtistiSpectacoleBilete();
                updateTable(tableView, lista);
            } catch (ChatException e) {
                MessageAlert.showErrorMessage(null, "Error: " + e.getMessage());
            }
        }
        );
    }
//    @FXML
//    public void initialize(URL url, ResourceBundle rb) {
//
//        System.out.println("INIT");
////        f1();
//        System.out.println("END INIT!!!!!!!!!");
//    }

    @FXML
    public void f2() {
        Platform.runLater(() -> {

            try {
            LocalDate selectedDate1 = calendar.getValue();
            if(selectedDate1!=null)
            {
                LocalDateTime selectedDate = selectedDate1.atStartOfDay();

                List<String> lista = service.findArtistiSpectacoleBileteDupaData(selectedDate);
                updateTable(tableView2, lista);
            }
        } catch (ChatException e) {
            MessageAlert.showErrorMessage(null, "Error: " + e.getMessage());
        }});
    }

    private void updateTable(TableView<TableRowData> table, List<String> data) {


        if(table==tableView2){
            ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();
            table.getItems().clear();

            if (!data.isEmpty()) {
                for (String str : data) {
                    String[] l = str.split(",");
                    int l3 = Integer.parseInt(l[3]);
                    int l4 = Integer.parseInt(l[4]);
                    String s = String.valueOf((l3 - l4));

                    String ora;
                    if (l.length > 1) {
                     //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");

                        LocalDateTime localDateTime = LocalDateTime.parse(l[1], formatter);
                        ora = String.valueOf(localDateTime.getHour());
                    } else {
                        ora = "";
                    }

                    rowDataList.add(new TableRowData(l[0], l[2], ora, s, l[4]));
                }
                table.setItems(rowDataList);
            } else {
                MessageAlert.showErrorMessage(null, "NU exista spectacole!");
            }
        }
        else
        {
            ObservableList<TableRowData> rowDataList = FXCollections.observableArrayList();
            table.getItems().clear();

            if (!data.isEmpty()) {
                for (String str : data) {
                    String[] l = str.split(",");
                    int l3 = Integer.parseInt(l[3]);
                    int l4 = Integer.parseInt(l[4]);
                    String s = String.valueOf((l3 - l4));

                    String ora;
                    if (l.length > 1) {
                       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");

                        LocalDateTime localDateTime = LocalDateTime.parse(l[1], formatter);
                        ora = String.valueOf(localDateTime);
                    } else {
                        ora = "";
                    }

                    rowDataList.add(new TableRowData(l[0], ora, l[2], s, l[4]));
                }
                table.setItems(rowDataList);
            } else {
                MessageAlert.showErrorMessage(null, "NU exista spectacole!");
            }

        }
    }


    @FXML
    public void f3() {
        try {
            int index = tableView.getSelectionModel().getSelectedIndex();
            if (index > -1) {
                Spectecol sp = service.findArtistiSpectacoleBileteIndex(index);
                String nume = textf1.getText();
                textf1.clear();
                String prenume = textf2.getText();
                textf2.clear();
                String numarLocuri = textf3.getText();
                textf3.clear();
                textf6.clear();
                 long id_angajat = angajat.getId();
               //  long id_angajat = 3;
                System.out.println("angajatul cu id: "+angajat.getId()+angajat.getUsername());

                if(!Objects.equals(nume, "") && !Objects.equals(prenume, "") && !Objects.equals(numarLocuri, "")){
                String text = nume + " " + prenume + " " + numarLocuri + " " + id_angajat + " " + sp.getId();

                service.saveBilet(text);
                MessageAlert.showErrorMessage(null, "Bilet salvat cu succes.");

                f1();
                f2();
                }
                else{
                    MessageAlert.showErrorMessage(null, "NU ati completat toate 3 campurile");

                }
            } else {
                MessageAlert.showErrorMessage(null, "NU ati selectat spectacolul");
            }
        } catch (ChatException e) {
            MessageAlert.showErrorMessage(null, "Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Error: Numarul de locuri trebuie sa fie un numar intreg.");
        }
    }

    @FXML
    public void f4() {
        try {
            int index = tableView2.getSelectionModel().getSelectedIndex();
            if (index > -1) {


// Convertirea LocalDateTime la șir de caractere
                String dateAsString = calendar.getValue().atStartOfDay().toString();

// Construirea textului combinând data ca șir și indexul
                String text = dateAsString + " " + index;
                Spectecol sp = service.findArtistiSpectacoleBileteDupaDataIndex(text);
                String nume = textf4.getText();
                textf4.clear();
                String prenume = textf5.getText();
                textf5.clear();
                String numarLocuri = textf6.getText();
                textf6.clear();
                long id_angajat = angajat.getId();
                if(!Objects.equals(nume, "") && !Objects.equals(prenume, "") && !Objects.equals(numarLocuri, "")){
                    String text1 = nume + " " + prenume + " " + numarLocuri + " " + id_angajat + " " + sp.getId();

                service.saveBilet(text1);
                f2();
                f1();
                }else {
                        MessageAlert.showErrorMessage(null, "NU ati completat toate 3 campurile");
                }
            } else {
                MessageAlert.showErrorMessage(null, "NU ati selectat spectacolul");
            }
        } catch (ChatException e) {
            MessageAlert.showErrorMessage(null, "Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            MessageAlert.showErrorMessage(null, "Error: Numarul de locuri trebuie sa fie un numar intreg.");
        }
    }
    @FXML
    private void loggoutButton() throws IOException {
        try {
            this.service.logout(angajat,  this);

            Stage stage=(Stage) b1.getScene().getWindow();
            stage.close();



            FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("C:\\Users\\andre\\Documents\\facultate\\an2\\MAP\\JavaSocketsFestival\\Java11ChatSockets\\ChatClientFX\\src\\main\\resources\\view-controller.fxml").toUri().toURL());


            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Stage dialogStage = new Stage();
            //dialogStage.setTitle(s+" "+p.getNume());
            dialogStage.setTitle("Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            LoginController editMessageViewController = fxmlLoader.getController();
            editMessageViewController.setServer(service);//,p

            dialogStage.show();
        } catch (ChatException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void rezervareReceived(Bilet rezervare) throws ChatException {
        System.out.println("IN rezervare");
        f1();
        System.out.println("IN rezervare2");
        f2();
    }

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
}
