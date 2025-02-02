package festival.network.rpcprotocol;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.Provider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ChatServicesRpcProxy implements IChatServices {
    private String host;
    private int port;

    private IChatObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingDeque<Response> qresponses;
    private volatile boolean finished;

    public ChatServicesRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingDeque<Response>();
    }

    @Override
    public List<String> findArtistiSpectacoleBilete() throws ChatException {
        Request req = (new Request.Builder()).type(RequestType.FIND_ARTISTI_SPECTACOLE_BILETE).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            return (List<String>) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;    }

    @Override
    public List<String> findArtistiSpectacoleBileteDupaData(LocalDateTime date) throws ChatException {
        Request req = (new Request.Builder()).type(RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA).data(date).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            return (List<String>) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteIndex(int index) throws ChatException {
        Request req = (new Request.Builder()).type(RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_INDEX).data(index).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            return (Spectecol) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteDupaDataIndex(String text) throws ChatException {
        Request req = (new Request.Builder()).type(RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA_INDEX).data(text).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            return (Spectecol) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    @Override
    public Long savePersoanaReturnIdPers(String nume, String prenume) throws ChatException {
        return null;
    }

    @Override
    public Long saveClientReturnIdClient(String nume, String prenume) throws ChatException {
        return null;
    }

    @Override
    public Bilet saveBilet(String text) throws ChatException {
        Request req = (new Request.Builder()).type(RequestType.SAVE_BILET).data(text).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            return (Bilet) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    @Override
    public synchronized Angajat valid(String usr, String pass, IChatObserver observer) throws ChatException{
        initializeConnection();
        Angajat a=new Angajat(usr,pass);
        Request req = (new Request.Builder()).type(RequestType.VALID).data(a).build();
        this.sendRequest(req);
        Response response = this.readResponse();
        if (response.type() == ResponseType.OK) {
            this.client=observer;
            return (Angajat) response.data();
        }
        else if (response.type() == ResponseType.ERROR) {
            String err = response.data().toString();
            this.closeConnection();
            throw new ChatException(err);
        }
        return null;
    }

    public void logout(Angajat user,IChatObserver observer)throws ChatException{
//        , IObserver client
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
    }




    private void initializeConnection() throws ChatException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.BILET_SAVED ;
    }

//    public void logout(User user, IObserver client)throws ServerException{
//        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
//        sendRequest(req);
//        Response response=readResponse();
//        closeConnection();
//        if (response.type()== ResponseType.ERROR){
//            String err=response.data().toString();
//            throw new ServerException(err);
//        }
//    }
    private void handleUpdate(Response response){
        System.out.println("in handleupdate");
        if(response.type() == ResponseType.BILET_SAVED){
            Bilet rezervare = (Bilet) response.data();
            try{
                client.rezervareReceived(rezervare);
            } catch (ChatException e){
                e.printStackTrace();
            }
        }
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }

    private void sendRequest(Request request)throws ChatException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ChatException("Error sending object "+e);
        }

    }

    private Response readResponse() throws ChatException {
        Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            //client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
