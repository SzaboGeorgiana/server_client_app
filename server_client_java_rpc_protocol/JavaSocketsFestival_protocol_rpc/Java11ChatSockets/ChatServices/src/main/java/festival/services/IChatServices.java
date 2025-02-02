package festival.services;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;

import java.time.LocalDateTime;
import java.util.List;

public interface IChatServices {
     List<String> findArtistiSpectacoleBilete() throws ChatException;

     List<String> findArtistiSpectacoleBileteDupaData(LocalDateTime date) throws ChatException;;

     Spectecol findArtistiSpectacoleBileteIndex(int index) throws ChatException;;

     Spectecol findArtistiSpectacoleBileteDupaDataIndex(String text) throws ChatException;;

     Long savePersoanaReturnIdPers(String nume, String prenume) throws ChatException;;

     Long saveClientReturnIdClient(String nume, String prenume) throws ChatException;;

     Bilet saveBilet(String text) throws ChatException;;

     Angajat valid(String pass, String u,IChatObserver observer) throws ChatException;

     void logout(Angajat user,IChatObserver observer) throws ChatException;
//     , IObserver client

}
