package festival.client.gui;

import festival.model.Angajat;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Objects;

public class LoginController {
    public TextField username;
    public TextField Parola;
    // public TextField text;

    IChatServices service;
    private AngajatView chatCtrl;


    Parent mainChatParent;
    public void setServer(IChatServices s){
        service=s;
    }

    @FXML
    public void initialize() {
    }


    public void initModel(Angajat a) {
        try {
            // create a new stage for the popup dialog.
//            FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("/view-angajat.fxml").toUri().toURL());
            FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("C:\\Users\\andre\\Documents\\facultate\\an2\\MAP\\JavaSocketsFestival\\Java11ChatSockets\\ChatClientFX\\src\\main\\resources\\angajat-view.fxml").toUri().toURL());


            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Stage dialogStage = new Stage();
            //dialogStage.setTitle(s+" "+p.getNume());
            dialogStage.setTitle("Contul lui "+a.getNume()+"  "+a.getPrenume());

            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AngajatView editMessageViewController = fxmlLoader.getController();
            editMessageViewController.setService(service,a);//,p

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleValid() {
        String parola = Parola.getText();
        String usrn = username.getText();
        Parola.clear();
        username.clear();
        if (!Objects.equals(parola, "") && !Objects.equals(usrn, "")) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Paths.get("C:\\Users\\andre\\Documents\\facultate\\an2\\MAP\\JavaSocketsFestival\\Java11ChatSockets\\ChatClientFX\\src\\main\\resources\\angajat-view.fxml").toUri().toURL());


                AnchorPane root = (AnchorPane) fxmlLoader.load();
                Stage dialogStage = new Stage();
                //dialogStage.setTitle(s+" "+p.getNume());

                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                AngajatView editMessageViewController = fxmlLoader.getController();

                Angajat a = service.valid(parola, usrn, (IChatObserver) editMessageViewController);
                if (Objects.equals(a.getNume(), "null"))
                    MessageAlert.showErrorMessage(null, "Parola gresita. Incearca din nou.");
                else {
//               initModel(a);

                    editMessageViewController.setService(service, a);//,p
                    dialogStage.setTitle("Contul lui " + a.getNume() + "  " + a.getPrenume());

                    dialogStage.show();
                }


            } catch (ChatException e) {
                MessageAlert.showErrorMessage(null, "Error: " + e.getMessage());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else
            MessageAlert.showErrorMessage(null, "Error: " + "Completati campurile");
    }

    public void setChatController(AngajatView chatController) {

        this.chatCtrl = chatController;
    }

    public void setParent(Parent p) {
         mainChatParent = p;

    }
}
