package festival.network.protoBuffProtocol;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ProtoChatWorker  implements Runnable, IChatObserver {
    private IChatServices server;
    private Socket connection;

    private InputStream input;
    private OutputStream output;
    private volatile boolean connected;
    public ProtoChatWorker(IChatServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
            input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while(connected){
            try {
                // Object request=input.readObject();
                System.out.println("Waiting requests ...");
                FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.parseDelimitedFrom(input);
                System.out.println("Request received: "+request);
                FestivalProtobufs.FestResponse response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
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

//    public void messageReceived(Message message) throws ChatException {
//        System.out.println("Message received  "+message);
//        try {
//            sendResponse(ProtoUtils.createNewMessageResponse(message));
//        } catch (IOException e) {
//            throw new ChatException("Sending error: "+e);
//        }
//    }

//    public void friendLoggedIn(User friend) throws ChatException {
//        System.out.println("Friend logged in "+friend);
//        try {
//            sendResponse(ProtoUtils.createFriendLoggedInResponse(friend));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void friendLoggedOut(User friend) throws ChatException {
//        System.out.println("Friend logged out "+friend);
//        try {
//            sendResponse(ProtoUtils.createFriendLoggedOutResponse(friend));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private FestivalProtobufs.FestResponse handleRequest(FestivalProtobufs.FestRequest request){
        FestivalProtobufs.FestResponse response=null;
        switch (request.getType()){
            case VALID :{
                System.out.println("Login request ...");
                Angajat user=ProtoUtils.getUser(request);
                try {
                    Angajat ang=server.valid(user.getPassword(),user.getUsername(), this);
                    return ProtoUtils.createAngajatLoggedInResponse(ang);
                } catch (ChatException e) {
                    connected=false;
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case LOGOUT:{
                System.out.println("Logout request");
                Angajat user=ProtoUtils.getUser(request);
                try {
                    server.logout(user, this);
                    connected=false;
                    return ProtoUtils.createOkResponse();

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case FIND_ARTISTI_SPECTACOLE_BILETE:{
                System.out.println("FIND_ARTISTI_SPECTACOLE_BILETE request");
                //Angajat user=ProtoUtils.getUser(request);
                try {
                    List<String> l=server.findArtistiSpectacoleBilete();
                   // connected=false;
                    return ProtoUtils.createfindArtistiSpectacoleBileteResponse(l);

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA:{
                System.out.println("FIND_ARTISTI_SPECTACOLE_BILETE_dupa_DATA request");
                String user=ProtoUtils.getString(request);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
                try {
                    // Parsarea șirului într-un obiect LocalDateTime
                    dateTime = LocalDateTime.parse(user, formatter);

                    // Afișarea rezultatului
                    System.out.println("Data și ora parsate: " + dateTime);
                } catch (DateTimeParseException e) {
                    // În caz de eroare în parsare
                    System.out.println("Nu s-a putut parsa șirul într-o dată și oră.");
                    e.printStackTrace();
                }
                try {
                    List<String> l=server.findArtistiSpectacoleBileteDupaData(dateTime);
                  //  connected=false;
                    return ProtoUtils.createfindArtistiSpectacoleBileteResponse(l);

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case FIND_ARTISTI_SPECTACOLE_BILETE_INDEX:{
                System.out.println("FIND_ARTISTI_SPECTACOLE_BILETE_INDEX request");
                String user=ProtoUtils.getString(request);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                int ind= Integer.parseInt(user);
                try {
                    Spectecol l=server.findArtistiSpectacoleBileteIndex(ind);
                    //  connected=false;
                    return ProtoUtils.findArtistiSpectacoleBileteIndexResponse(l);

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA_INDEX:{
                System.out.println("FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA_INDEX request");
                String user=ProtoUtils.getString(request);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String ind= user;
                try {
                    Spectecol l=server.findArtistiSpectacoleBileteDupaDataIndex(ind);
                    //  connected=false;
                    return ProtoUtils.findArtistiSpectacoleBileteIndexResponse(l);

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
            case SAVE_BILET:{
                System.out.println("SAVE_BILET request");
                String user=ProtoUtils.getString(request);
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                try {
                    Bilet l=server.saveBilet(user);
                    System.out.println("SAVE_BILET request"+l.getId());
                    //  connected=false;
                    return ProtoUtils.SAVEbILETrESPONSE(l);

                } catch (ChatException e) {
                    return ProtoUtils.createErrorResponse(e.getMessage());
                }
            }
//            case SendMessage:{
//                System.out.println("SendMessageRequest ...");
//                Message message=ProtoUtils.getMessage(request);
//                try {
//                    server.sendMessage(message);
//                    return ProtoUtils.createOkResponse();
//                } catch (ChatException e) {
//                    return ProtoUtils.createErrorResponse(e.getMessage());
//                }
//            }
//            case GetLoggedFriends:{
//                System.out.println("GetLoggedFriends Request ...");
//                User user=ProtoUtils.getUser(request);
//                try {
//                    User[] friends=server.getLoggedFriends(user);
//                    return ProtoUtils.createLoggedFriendsResponse(friends);
//                } catch (ChatException e) {
//                    return ProtoUtils.createErrorResponse(e.getMessage());
//                }
//            }
        }
        return response;
    }

    private void sendResponse(FestivalProtobufs.FestResponse response) throws IOException{
        System.out.println("sending response "+response);
        response.writeDelimitedTo(output);
        //output.writeObject(response);
        output.flush();
    }

    @Override
    public void rezervareReceived(Bilet rezervare) throws ChatException {
        System.out.println("rezervare received  "+rezervare.getNr_locuri());
        try {
            sendResponse(ProtoUtils.createNewBiletResponse(rezervare));
        } catch (IOException e) {
            throw new ChatException("Sending error: "+e);
        }
    }
}
