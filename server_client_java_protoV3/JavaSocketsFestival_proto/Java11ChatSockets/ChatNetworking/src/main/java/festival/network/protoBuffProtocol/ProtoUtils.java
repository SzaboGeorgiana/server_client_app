package festival.network.protoBuffProtocol;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {


    public static FestivalProtobufs.FestRequest createLoginRequest(Angajat user){
        FestivalProtobufs.Angajat userDTO=FestivalProtobufs.Angajat.newBuilder().setUsername(user.getUsername()).setPassword(user.getPassword()).setId("1").setNume("ss").setOficiu("aa").setPrenume("sws").build();
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.VALID)
                .setUser(userDTO).build();
        return request;
    }
    public static FestivalProtobufs.FestRequest createLogoutRequest(Angajat user){
        System.out.println("IN PROTOUTILS IN CLIENT(SRV PROXY)"+user.getId());
        FestivalProtobufs.Angajat userDTO=FestivalProtobufs.Angajat.newBuilder().setUsername(user.getUsername()).setId(user.getId().toString()).build();
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.LOGOUT)
                .setUser(userDTO).build();
        return request;
    }


    public static FestivalProtobufs.FestResponse createOkResponse(){
        FestivalProtobufs.FestResponse response=FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.OK).build();
        return response;
    }


    public static FestivalProtobufs.FestResponse createErrorResponse(String text){
        FestivalProtobufs.FestResponse response=FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.ERROR)
                .setError(text).build();
        return response;
    }
//
    public static FestivalProtobufs.FestResponse createAngajatLoggedInResponse(Angajat user){
        if(user!=null){
        FestivalProtobufs.Angajat userDTO= FestivalProtobufs.Angajat.newBuilder().setUsername(user.getUsername()).setId(user.getId().toString()).setNume(user.getNume()).setPrenume(user.getPrenume()).setPassword(user.getPassword()).build();

            return FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.OK)
                .setUser(userDTO).build();
        }else{
            FestivalProtobufs.Angajat userDTO= FestivalProtobufs.Angajat.newBuilder().setNume("null").build();

            FestivalProtobufs.FestResponse response= FestivalProtobufs.FestResponse.newBuilder()
                    .setType(FestivalProtobufs.FestResponse.Type.OK)
                    .setUser(userDTO).build();
        return response;
        }
    }

//    public static ChatProtobufs.ChatResponse createFriendLoggedOutResponse(User user){
//        ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.FriendLoggedOut)
//                .setUser(userDTO).build();
//        return response;
//    }
//    public static ChatProtobufs.ChatResponse createNewMessageResponse(Message message){
//        ChatProtobufs.Message messageDTO=ChatProtobufs.Message.newBuilder()
//                .setSenderId(message.getSender().getId())
//                .setReceiverId(message.getReceiver().getId())
//                .setText(message.getText())
//                .build();
//
//        ChatProtobufs.ChatResponse response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.NewMessage)
//                .setMessage(messageDTO).build();
//        return response;
//    }
//
//    public static ChatProtobufs.ChatResponse createLoggedFriendsResponse(User[] users){
//        ChatProtobufs.ChatResponse.Builder response=ChatProtobufs.ChatResponse.newBuilder()
//                .setType(ChatProtobufs.ChatResponse.Type.GetLoggedFriends);
//        for (User user: users){
//            ChatProtobufs.User userDTO=ChatProtobufs.User.newBuilder().setId(user.getId()).build();
//            response.addFriends(userDTO);
//        }
//
//        return response.build();
//    }
//
    public static String getError(FestivalProtobufs.FestResponse response){
        String errorMessage=response.getError();
        return errorMessage;
    }
//
    public static Angajat getUser(FestivalProtobufs.FestRequest request){
        Angajat user=new Angajat(request.getUser().getUsername(),request.getUser().getPassword());
        user.setNume(request.getUser().getNume()) ;
        user.setPrenume(request.getUser().getPrenume());
        if((request.getUser().getId())!="")
            user.setId(Long.valueOf(request.getUser().getId()));

        return user;
    }

    public static Angajat getUser(FestivalProtobufs.FestResponse response){
        if(response.getUser().getNume().equals("null")){
            Angajat user=new Angajat();
        user.setNume(response.getUser().getNume()) ;
        return user;}
        else{
            Angajat user=new Angajat(response.getUser().getUsername(),response.getUser().getPassword());
            user.setNume(response.getUser().getNume()) ;
            user.setPrenume(response.getUser().getPrenume());
            user.setId(Long.valueOf(response.getUser().getId()));
            return user;
        }
    }


    public synchronized static FestivalProtobufs.FestRequest createfindArtistiSpectacoleBileteRequest() {
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.FIND_ARTISTI_SPECTACOLE_BILETE)
                .build();
        return request;
    }


    public synchronized static FestivalProtobufs.FestRequest createfindArtistiSpectacoleBileteDupaDataRequest(LocalDateTime date) {
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA).setMessage(date.toString())
                .build();
        return request;
    }

    public synchronized static String getString(FestivalProtobufs.FestRequest request) {
        String user=request.getMessage();
        return user;
    }

    public synchronized static List<String> getListaStringuri(FestivalProtobufs.FestResponse response) {
//         l=response.getListaList();
        List<String> friends =new ArrayList<>();
        for(int i=0;i<response.getListaCount();i++){
            String userDTO=response.getLista(i);
            friends.add(userDTO);
        }
        return friends;
    }

    public synchronized static FestivalProtobufs.FestResponse createfindArtistiSpectacoleBileteResponse(List<String> l) {
//        FestivalProtobufs.ListaString userDTO= FestivalProtobufs.ListaString.newBuilder().addAllLista(l).build();

        return FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.OK)
                .addAllLista(l).build();
    }

    public static FestivalProtobufs.FestRequest findArtistiSpectacoleBileteIndexRequest(int index) {
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.FIND_ARTISTI_SPECTACOLE_BILETE_INDEX).setMessage(String.valueOf(index))
                .build();
        return request;
    }

    public static Spectecol getSpectacol(FestivalProtobufs.FestResponse response) {
        if(response.getSpectacol()==null)
            return  null;
        else{

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
            LocalDateTime dateTime = null;
            try {
                // Parsarea șirului într-un obiect LocalDateTime
                dateTime = LocalDateTime.parse(response.getSpectacol().getDataInc(), formatter);

                // Afișarea rezultatului
                System.out.println("Data și ora parsate: " + dateTime);
            } catch (DateTimeParseException e) {
                // În caz de eroare în parsare
                System.out.println("Nu s-a putut parsa șirul într-o dată și oră.");
                e.printStackTrace();
            }
            Spectecol user=new Spectecol(response.getSpectacol().getIdArtist(),response.getSpectacol().getLocatie(), dateTime,response.getSpectacol().getNrLocuri());
            user.setId(response.getSpectacol().getIdSpectacol());
            return user;
        }

    }

    public static FestivalProtobufs.FestResponse findArtistiSpectacoleBileteIndexResponse(Spectecol l) {
        FestivalProtobufs.Spectecol userDTO=FestivalProtobufs.Spectecol.newBuilder().setIdArtist(l.getId_artist()).setDataInc((l.getData_inc()).toString()).setLocatie(l.getLocatie()).setNrLocuri(l.getNr_locuri()).setIdSpectacol(l.getId()).build();

        return FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.OK).setSpectacol(userDTO).build();

    }

    public static FestivalProtobufs.FestRequest saveBiletRequest(String text) {
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.SAVE_BILET).setMessage(String.valueOf(text))
                .build();
        return request;
    }

    public static FestivalProtobufs.FestResponse SAVEbILETrESPONSE(Bilet l) {

        FestivalProtobufs.Bilet userDTO=FestivalProtobufs.Bilet.newBuilder().setNrLocuri((l.getNr_locuri())).setIdAngajat(l.getId_angajat()).setIdClient(l.getId_client()).setIdSpectacol(l.getId_spectacol()).build();
//        setIdBilet(l.getId())
        return FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.OK).setBilet(userDTO).build();

    }
    public static Bilet getBilet(FestivalProtobufs.FestResponse updateResponse) {
        //            user.setId(updateResponse.getBilet().getIdBilet());
        return new Bilet(updateResponse.getBilet().getIdAngajat(), updateResponse.getBilet().getIdClient(), (updateResponse.getBilet().getIdSpectacol()), updateResponse.getBilet().getNrLocuri());
    }

    public static FestivalProtobufs.FestRequest findArtistiSpectacoleBileteDupaDataIndexRequest(String text) {

        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.FIND_ARTISTI_SPECTACOLE_BILETE_DUPA_DATA_INDEX).setMessage(String.valueOf(text))
                .build();
        return request;
    }

    public static FestivalProtobufs.FestResponse createNewBiletResponse(Bilet l) {

        FestivalProtobufs.Bilet userDTO = FestivalProtobufs.Bilet.newBuilder().setNrLocuri((l.getNr_locuri())).setIdAngajat(l.getId_angajat()).setIdClient(l.getId_client()).setIdSpectacol(l.getId_spectacol()).build();
//        setIdBilet(l.getId())
        return FestivalProtobufs.FestResponse.newBuilder()
                .setType(FestivalProtobufs.FestResponse.Type.BILET_SAVED).setBilet(userDTO).build();

    }

    public static FestivalProtobufs.FestRequest probaRequest(String proba) {
        FestivalProtobufs.FestRequest request= FestivalProtobufs.FestRequest.newBuilder().setType(FestivalProtobufs.FestRequest.Type.VALID)
                .setMessage(proba).build();
        return request;
    }

}
