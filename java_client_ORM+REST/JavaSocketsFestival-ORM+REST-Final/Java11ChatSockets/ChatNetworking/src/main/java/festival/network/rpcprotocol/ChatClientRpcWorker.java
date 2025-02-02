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
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatClientRpcWorker implements Runnable,IChatObserver {
    private IChatServices service;
    private Socket connection;

    private ObjectInputStream input;

    private ObjectOutputStream output;

    private volatile boolean connected;

    private static Response okResponse;

    public ChatClientRpcWorker(IChatServices service, Socket connection){
        this.service = service;
        this.connection = connection;
        try{
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    private synchronized Response handleRequest(Request request) {
        System.out.println("Suntem in handleRequest la Client");
        Angajat user;
        if(request.type() == RequestType.LOGOUT){
            System.out.println("Get logout request");
            user = (Angajat) request.data();
            try{
                service.logout(user, (IChatObserver) this);
                connected = false;
                return okResponse;
            }catch (ChatException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.FIND_ARTISTI_SPECTACOLE_BILETE){
            System.out.println("Get all artisti request ...." + request.type());
            try{
                List<String> l = this.service.findArtistiSpectacoleBilete();
                return new Response.Builder().type(ResponseType.OK).data(l).build();
            } catch (ChatException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA){
            System.out.println("Get all artisti dupa datan request ...." + request.type());
            LocalDateTime data_sp= (LocalDateTime) request.data();
            try{
                List<String> l = this.service.findArtistiSpectacoleBileteDupaData(data_sp);
                return new Response.Builder().type(ResponseType.OK).data(l).build();
            } catch (ChatException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_INDEX){
            System.out.println("Get spectacolul selectat request ...." + request.type());
            int index= (int) request.data();
            try{
                Spectecol l = this.service.findArtistiSpectacoleBileteIndex(index);
                return new Response.Builder().type(ResponseType.OK).data(l).build();
            } catch (ChatException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA_INDEX){
            System.out.println("Get spectacolul selectat dupa data request ...." + request.type());
            String index= (String) request.data();
            try{
                Spectecol l = this.service.findArtistiSpectacoleBileteDupaDataIndex(index);
                return new Response.Builder().type(ResponseType.OK).data(l).build();
            } catch (ChatException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.SAVE_BILET){
            System.out.println("Save bilet request ...." + request.type());
            String index= (String) request.data();
            try{
                Bilet l = this.service.saveBilet(index);
                return new Response.Builder().type(ResponseType.OK).data(l).build();
            } catch (ChatException e){
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }
        if (request.type() == RequestType.VALID) {
            System.out.println("Login request ..." + request.type());
            user = (Angajat) request.data();

            try {
                Angajat an=this.service.valid(user.getUsername(), user.getPassword(), (IChatObserver) this);
                return new Response.Builder().type(ResponseType.OK).data(an).build();

//                return okResponse;
            } catch (ChatException var7) {
                this.connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(var7.getMessage()).build();
            }
        } else {
            // Tratați toate celelalte tipuri de solicitări în mod corespunzător aici
            return new Response.Builder().type(ResponseType.ERROR).data("Invalid request type").build();
        }
    }



    static {
        okResponse = (new Response.Builder()).type(ResponseType.OK).build();
    }

    @Override
    public void rezervareReceived(Bilet rezervare) throws ChatException {
        Response response = new Response.Builder().type(ResponseType.BILET_SAVED).data(rezervare).build();
        System.out.println("Rezervare received " + rezervare);
        try{
            sendResponse(response);
        }catch (IOException e){
            throw new ChatException("Sending error: " +  e.getMessage());
        }
    }
}
