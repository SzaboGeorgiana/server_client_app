using System.Collections.Generic;
using FestivalModel;

namespace FestivalNetworking
{
    using System;
    
        //using UserDTO = chat.network.dto.UserDTO;
        //using MessageDTO = chat.network.dto.MessageDTO;

        public interface Response 
        {
        }

        [Serializable]
        public class OkResponse : Response
        {
            private string message;

            public OkResponse(string message)
            {
                this.message = message;
            }

            public virtual string Message
            {
                get
                {
                    return message;
                }
            }
        }
        [Serializable]
        public class ListResponse : Response
        {
            private List<string> message;

            public ListResponse(List<string> message)
            {
                this.message = message;
            }

            public virtual List<string> Message
            {
                get
                {
                    return message;
                }
            }
        }
        
        [Serializable]
        public class Update : Response
        {
            private Bilet message;

            public Update(Bilet message)
            {
                this.message = message;
            }

            public virtual Bilet Message
            {
                get
                {
                    return message;
                }
            }
        }
        [Serializable]
        public class BiletDoar : Response
        {
            private Bilet message;

            public BiletDoar(Bilet message)
            {
                this.message = message;
            }

            public virtual Bilet Message
            {
                get
                {
                    return message;
                }
            }
        }
       

        [Serializable]
        public class SpectacolResponse : Response
        {
            private Spectacol message;

            public SpectacolResponse(Spectacol message)
            {
                this.message = message;
            }

            public virtual Spectacol Message
            {
                get
                {
                    return message;
                }
            }
        }

        [Serializable]
        public class ErrorResponse : Response
        {
            private string message;

            public ErrorResponse(string message)
            {
                this.message = message;
            }

            public virtual string Message
            {
                get
                {
                    return message;
                }
            }
        }
       
}
