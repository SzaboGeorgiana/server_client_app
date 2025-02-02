

import festival.network.utils.AbstractServer;
import festival.network.utils.ChatProtobuffConcurrentServer;
import festival.network.utils.ServerException;
import festival.persistence.repository.*;
import festival.server.Service;
import festival.services.ChatException;
import festival.services.IChatServices;

import java.io.IOException;
import java.util.Properties;


public class StartProtoServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {


        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/chatserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        DBRepoArtist ArtistRepo=new DBRepoArtist(serverProps);
        DBRepoSpectacol SpectacolRepo=new DBRepoSpectacol(serverProps);
        DBRepoBilet BiletRepo=new DBRepoBilet(serverProps);
        DBRepoAngajat AngajatRepo=new DBRepoAngajat(serverProps);
        DBRepoClient ClientRepo=new DBRepoClient(serverProps);
        DBRepoPersoana PersoanaRepo=new DBRepoPersoana(serverProps);



        IChatServices srv=new Service(BiletRepo,ArtistRepo,SpectacolRepo,AngajatRepo,ClientRepo,PersoanaRepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatProtobuffConcurrentServer(chatServerPort, srv);
        try {
            server.start();
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }


    }
}
