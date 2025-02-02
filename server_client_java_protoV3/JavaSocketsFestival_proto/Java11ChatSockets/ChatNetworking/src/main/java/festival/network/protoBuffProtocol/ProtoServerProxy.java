package festival.network.protoBuffProtocol;

import festival.model.Angajat;
import festival.model.Bilet;
import festival.model.Spectecol;
import festival.network.rpcprotocol.Request;
import festival.network.rpcprotocol.RequestType;
import festival.network.rpcprotocol.Response;
import festival.network.rpcprotocol.ResponseType;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoServerProxy implements IChatServices {
    private String host;
    private int port;

    private IChatObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<FestivalProtobufs.FestResponse> qresponses;
    private volatile boolean finished;

    public ProtoServerProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<FestivalProtobufs.FestResponse>();
    }


    @Override
    public Angajat valid(String pass, String u, IChatObserver observer) throws ChatException {
        initializeConnection();
        System.out.println("Login request ...");

        Angajat a = new Angajat(u, pass);
        a.setId(1L);
        a.setOficiu("aa");
        a.setNume("a");
        a.setPrenume("k");

        sendRequest(ProtoUtils.createLoginRequest(a));
      //  sendRequest(ProtoUtils.probaRequest("proba"));

        FestivalProtobufs.FestResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
            this.client = observer;
            return ProtoUtils.getUser(response);
        }
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            //closeConnection();
            throw new ChatException(errorText);
        }
        return null;
    }

    @Override
    public void logout(Angajat user, IChatObserver observer) throws ChatException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        FestivalProtobufs.FestResponse response = readResponse();
        closeConnection();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            throw new ChatException(errorText);
        }
    }


    @Override
    public List<String> findArtistiSpectacoleBilete() throws ChatException {
        System.out.println("findArtistiSpectacoleBilete request ...");

        sendRequest(ProtoUtils.createfindArtistiSpectacoleBileteRequest());
        FestivalProtobufs.FestResponse response = readResponse();
        if (response != null) {
            if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
                return ProtoUtils.getListaStringuri(response);
            }
            if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
                String errorText = ProtoUtils.getError(response);
                closeConnection();
                throw new ChatException(errorText);
            }
            return null;
        } else {
            // Tratează situația în care response este null
            System.out.println("Obiectul response este null.");
            return null;

        }


    }

    @Override
    public List<String> findArtistiSpectacoleBileteDupaData(LocalDateTime date) throws ChatException {
        System.out.println("findArtistiSpectacoleBileteDupaData... request ...");

        sendRequest(ProtoUtils.createfindArtistiSpectacoleBileteDupaDataRequest(date));
        FestivalProtobufs.FestResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
            return ProtoUtils.getListaStringuri(response);
        }
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new ChatException(errorText);
        }
        return null;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteIndex(int index) throws ChatException {
        System.out.println("findArtistiSpectacoleBileteIndex... request ...");

        sendRequest(ProtoUtils.findArtistiSpectacoleBileteIndexRequest(index));
        FestivalProtobufs.FestResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
            return ProtoUtils.getSpectacol(response);
        }
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new ChatException(errorText);
        }
        return null;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteDupaDataIndex(String text) throws ChatException {
        System.out.println("findArtistiSpectacoleBileteDupaDataIndex... request ...");

        sendRequest(ProtoUtils.findArtistiSpectacoleBileteDupaDataIndexRequest(text));
        FestivalProtobufs.FestResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
            return ProtoUtils.getSpectacol(response);
        }
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new ChatException(errorText);
        }
        return null;
    }

    @Override
    public Bilet saveBilet(String text) throws ChatException {
        System.out.println("saveBilet... request ...");

        sendRequest(ProtoUtils.saveBiletRequest(text));
        FestivalProtobufs.FestResponse response = readResponse();
        if (response.getType() == FestivalProtobufs.FestResponse.Type.OK) {
            return ProtoUtils.getBilet(response);
        }
        if (response.getType() == FestivalProtobufs.FestResponse.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            closeConnection();
            throw new ChatException(errorText);
        }
        return null;
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /*  private void sendRequest(FestivalProtobufs.FestRequest request) throws ChatException {
        try {
            System.out.println("Sending request ..." + request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new ChatException("Error sending object " + e);
        }

    }*/
  private void sendRequest(FestivalProtobufs.FestRequest request) throws ChatException {
      // Verificarea fluxului de ieșire
      if (output == null) {
          throw new ChatException("Output stream is not initialized.");
      }

      try {
          System.out.println("Sending request: " + request);

          // Trimiterea mesajului de solicitare în fluxul de ieșire
          request.writeDelimitedTo(output);
          output.flush();

          System.out.println("Request sent.");
      } catch (IOException e) {
          // Gestionarea excepțiilor
          throw new ChatException("Error sending object: " + e.getMessage(), e);
      } /*finally {
          // Închiderea fluxului de ieșire în caz de excepție
          if (output != null) {
              try {
                  output.close();
              } catch (IOException e) {
                  // Tratează excepția în cazul în care închiderea fluxului eșuează
                  e.printStackTrace();
              }
          }*/
      }



    private FestivalProtobufs.FestResponse readResponse() throws ChatException {
        FestivalProtobufs.FestResponse response = null;
        try {
            response = qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() throws ChatException {
        try {
            connection = new Socket(host, port);
            output = connection.getOutputStream();
            //output.flush();
            input = connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(FestivalProtobufs.FestResponse updateResponse) {
        switch (updateResponse.getType()) {
            case BILET_SAVED: {
                Bilet message = ProtoUtils.getBilet(updateResponse);
                try {
                    client.rezervareReceived(message);
                } catch (ChatException e) {
                    e.printStackTrace();
                }
                break;
            }

        }

    }

    @Override
    public Long savePersoanaReturnIdPers(String nume, String prenume) throws ChatException {
        return null;
    }

    @Override
    public Long saveClientReturnIdClient(String nume, String prenume) throws ChatException {
        return null;
    }


    private class ReaderThread implements Runnable {
        public void run() {
            int w = 0;
            while (!finished) {
                try {
                    FestivalProtobufs.FestResponse response = FestivalProtobufs.FestResponse.parseDelimitedFrom(input);
//                   if(response==null&&w<4) {
//                       System.out.println("response received "+response);
//                       w+=1;
//                   }
                    if (response != null) {
                        System.out.println("response received " + response);

                        if (isUpdateResponse(response.getType())) {
                            handleUpdate(response);
                        } else {
                            try {
                                qresponses.put(response);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error " + e);
                }

            }
        }
    }

    private boolean isUpdateResponse(FestivalProtobufs.FestResponse.Type type) {
        return Objects.requireNonNull(type) == FestivalProtobufs.FestResponse.Type.BILET_SAVED;
    }
}