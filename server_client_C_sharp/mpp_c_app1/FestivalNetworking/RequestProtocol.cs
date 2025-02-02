using System;
using FestivalModel;

namespace FestivalNetworking
{

    //using UserDTO = chat.network.dto.UserDTO;
    //using MessageDTO = chat.network.dto.MessageDTO;


    public interface Request
    {
    }


    [Serializable]
    public class LoginRequest : Request
    {
        private String user;

        public LoginRequest(String user)
        {
            this.user = user;
        }

        public virtual String User
        {
            get { return user; }
        }
    }
    [Serializable]
    public class SaveBilet : Request
    {
        private String user;

        public SaveBilet(String user)
        {
            this.user = user;
        }

        public virtual String User
        {
            get { return user; }
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private Angajat user;

        public LogoutRequest(Angajat user)
        {
            this.user = user;
        }

        public virtual Angajat User
        {
            get { return user; }
        }
    }

    /*[Serializable]
    public class SendMessageRequest : Request
    {
        private MessageDTO message;

        public SendMessageRequest(MessageDTO message)
        {
            this.message = message;
        }

        public virtual MessageDTO Message
        {
            get { return message; }
        }
    }*/

    [Serializable]
    public class FindArtistiSpectacoleBilete : Request
    {
       
    }

    [Serializable]
    public class FindArtistiSpectacoleBileteDupaData : Request
    {
        private DateTime user;

        public FindArtistiSpectacoleBileteDupaData(DateTime user)
        {
            this.user = user;
        }

        public virtual DateTime User
        {
            get { return user; }
        }
    }
    [Serializable]
    public class FindArtistiSpectacoleBileteIndex : Request
    {
        private int user;

        public FindArtistiSpectacoleBileteIndex(int user)
        {
            this.user = user;
        }

        public virtual int User
        {
            get { return user; }
        }
    }
    [Serializable]
    public class FindArtistiSpectacoleBileteDupaDataIndex : Request
    {
        private string user;

        public FindArtistiSpectacoleBileteDupaDataIndex(string user)
        {
            this.user = user;
        }

        public virtual string User
        {
            get { return user; }
        }
    }
    
    
}