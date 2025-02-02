package festival.services;


import festival.model.Bilet;
import festival.model.Spectecol;

public interface IChatObserver {
    void rezervareReceived(Bilet rezervare) throws ChatException;

}
