package festival.client;

//import festival.client.gui.ClientCtrl;
//import festival.client.gui.LoginController1;
import festival.network.protoBuffProtocol.ProtoServerProxy;
import festival.services.IChatServices;
import festival.client.gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
public class StartProtoClientFX extends Application{
    private static int defaultChatPort=55555;
    private static String defaultServer="localhost";

//    public static void main(String[] args) {
//
//
//
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start client, JAVA, ");

        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/chatclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("chat.server.host",defaultServer);
        int serverPort=defaultChatPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("chat.server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultChatPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);


        IChatServices server = new ProtoServerProxy(serverIP, serverPort);
//        ClientCtrl ctrl=new ClientCtrl(server);


//        LoginController1 logWin=new LoginController1("Chat XYZ", ctrl);
//        logWin.setSize(200,200);
//        logWin.setLocation(150,150);
//        logWin.setVisible(true);
//        FXMLLoader loader = new FXMLLoader(
//                getClass().getClassLoader().getResource("view-controller.fxml"));
//        Parent root=loader.load();


        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("view-controller.fxml"));
        Parent root=loader.load();

        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
