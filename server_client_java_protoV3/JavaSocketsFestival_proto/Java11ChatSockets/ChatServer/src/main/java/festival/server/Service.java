package festival.server;


import festival.model.*;
import festival.persistence.repository.DBRepoAngajat;
import festival.persistence.repository.Repository;
import festival.services.ChatException;
import festival.services.IChatObserver;
import festival.services.IChatServices;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service  <ID, E extends Identifier<ID>> implements IChatServices {
    private Repository<Long, Bilet> repository_bilet;
    private Repository<Long, Artist> repository_artist;
    private Repository<Long, Spectecol> repository_spectacol;
    private Repository<Long, Angajat> repository_angajat;
    private Repository<Long, Client> repository_client;
    private Repository<Long, Persoana> repository_persoana;


    private Map<Long, IChatObserver> loggedClients;

    public Service(Repository<Long, Bilet> repo_b,
                   Repository<Long, Artist> repo_artist,
                   Repository<Long, Spectecol> repo_spectacol,
                   Repository<Long, Angajat> repo_angajat,
                   Repository<Long, Client> repo_client,
                   Repository<Long, Persoana> repo_persoana) {
        this.repository_bilet = repo_b;
        this.repository_artist = repo_artist;
        this.repository_spectacol = repo_spectacol;
        this.repository_angajat = repo_angajat;
        this.repository_client = repo_client;
        this.repository_persoana = repo_persoana;
        loggedClients = new ConcurrentHashMap<>();

    }

    @Override
    public List<String> findArtistiSpectacoleBilete() {

        List<String> listaInregistrariBilete = new ArrayList<>();

        for (Artist artist : repository_artist.findAll()) {
            for (Spectecol spectacol : repository_spectacol.findAll()) {
                if(spectacol.getId_artist()==artist.getId())
                {
                    int nr_bilete_date=0;
                    for (Bilet bilet : repository_bilet.findAll()) {
                        if(bilet.getId_spectacol()==spectacol.getId())
                        {
                            nr_bilete_date+=bilet.getNr_locuri();
                        }
                    }
                    listaInregistrariBilete.add(artist.getNume()+' '+artist.getPrenume()+','
                            + spectacol.getData_inc()+','+spectacol.getLocatie()+','+spectacol.getNr_locuri()+','+nr_bilete_date);
                }
            }
        }


        return listaInregistrariBilete;
    }

    @Override
    public List<String> findArtistiSpectacoleBileteDupaData(LocalDateTime date) {
        List<String> listaInregistrariBilete = new ArrayList<>();

        for (Artist artist : repository_artist.findAll()) {
            for (Spectecol spectacol : repository_spectacol.findAll()) {
                if(spectacol.getData_inc().getDayOfMonth()==date.getDayOfMonth()&&spectacol.getData_inc().getMonth()==date.getMonth()&&spectacol.getData_inc().getYear()==date.getYear())
                    if(spectacol.getId_artist()==artist.getId())
                    {
                        int nr_bilete_date=0;
                        for (Bilet bilet : repository_bilet.findAll()) {
                            if(bilet.getId_spectacol()==spectacol.getId())
                            {
                                nr_bilete_date+=bilet.getNr_locuri();
                            }
                        }
                        listaInregistrariBilete.add(artist.getNume()+' '+artist.getPrenume()+','
                                + spectacol.getData_inc()+','+spectacol.getLocatie()+','+spectacol.getNr_locuri()+','+nr_bilete_date);
                    }
            }
        }
        return listaInregistrariBilete;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteIndex(int index) {
        List<String> listaInregistrariBilete = new ArrayList<>();
        int i = 0;
        for (Artist artist : repository_artist.findAll()) {
            for (Spectecol spectacol : repository_spectacol.findAll()) {
                if (spectacol.getId_artist() == artist.getId()) {
//                    int nr_bilete_date = 0;
//                    for (Bilet bilet : repository_bilet.findAll()) {
//                        if (bilet.getId_spectacol() == spectacol.getId()) {
//                            nr_bilete_date += bilet.getNr_locuri();
//                        }
//                    }
                    if (i == index)
                        return spectacol;
//                    listaInregistrariBilete.add(artist.getNume() + ' ' + artist.getPrenume() + ','
//                            + spectacol.getData_inc() + ',' + spectacol.getLocatie() + ',' + spectacol.getNr_locuri() + ',' + nr_bilete_date);
                    i += 1;
                }
            }
        }
        return null;
    }

    @Override
    public Spectecol findArtistiSpectacoleBileteDupaDataIndex(String text ) {
        String[] parti = text.split("\\s+");

// Extrage prima parte (reprezentarea LocalDateTime)
        String dateAsString = parti[0];

// Extrage a doua parte (indexul)
        int index = Integer.parseInt(parti[1]);

// Convertirea reprezentării LocalDateTime la obiect LocalDateTime
        LocalDateTime date = LocalDateTime.parse(dateAsString);

        List<String> listaInregistrariBilete = new ArrayList<>();
        int i=0;
        for (Artist artist : repository_artist.findAll()) {
            for (Spectecol spectacol : repository_spectacol.findAll()) {
                if(spectacol.getData_inc().getDayOfMonth()==date.getDayOfMonth()&&spectacol.getData_inc().getMonth()==date.getMonth()&&spectacol.getData_inc().getYear()==date.getYear())
                    if(spectacol.getId_artist()==artist.getId())
                    {
//                        int nr_bilete_date=0;
//                        for (Bilet bilet : repository_bilet.findAll()) {
//                            if(bilet.getId_spectacol()==spectacol.getId())
//                            {
//                                nr_bilete_date+=bilet.getNr_locuri();
//                            }
//                        }
                        if(i==index)
                            return spectacol;
                        i+=1;
//                        listaInregistrariBilete.add(artist.getNume()+' '+artist.getPrenume()+','
//                                + spectacol.getData_inc()+','+spectacol.getLocatie()+','+spectacol.getNr_locuri()+','+nr_bilete_date);
                    }
            }
        }
        return null;
    }

    @Override
    public Long savePersoanaReturnIdPers(String nume, String prenume) {
        Persoana persoana=new Persoana(nume,prenume);
        repository_persoana.save(persoana);
        List <Persoana> p= (List<Persoana>) repository_persoana.findAll();

        return p.get(p.size() - 1).getId();
    }

    @Override
    public Long saveClientReturnIdClient(String nume, String prenume) {
        Long id=savePersoanaReturnIdPers(nume,prenume);
        Client pers=new Client(id,nume,prenume);
        repository_client.save( pers);
        List <Client> p= (List<Client>) repository_client.findAll();

        return p.get(p.size() - 1).getId();
    }
    private final int defaultThreadsNo=5;

    @Override
    public Bilet saveBilet(String text) {
        System.out.println(text);
        String[] parti = text.split("\\s+"); // "\\s+" înseamnă "orice caracter de spațiu, una sau mai multe apariții"

// Atribuirea fiecărei părți la variabilele corespunzătoare
        String nume = parti[0];
        String prenume = parti[1];
        int numarLocuriB = Integer.parseInt(parti[2]);
        Long IDAngajat = Long.parseLong(parti[3]);
        Long IDSpectacol = Long.parseLong(parti[4]);

        Long idClient=saveClientReturnIdClient(nume,prenume);
        Bilet bilet=new Bilet(IDAngajat,idClient,IDSpectacol,numarLocuriB);
        repository_bilet.save(bilet);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(IChatObserver observer : loggedClients.values()){
            try{
                observer.rezervareReceived(bilet);
            }catch (ChatException e){
                System.out.println("Eror: " + e.getMessage());
            }
        }

        executor.shutdown();
        System.out.println(bilet.getId());

        return bilet;
    }

    public synchronized Angajat valid(String pass, String u, IChatObserver observer) throws ChatException{
        // Apelăm metoda authenticate
        System.out.println(" login, service");
        Angajat userFound =  ((DBRepoAngajat) repository_angajat).authenticate(u, pass);
        if(userFound != null){
            if(loggedClients.get(userFound.getId())!=null)
                throw new ChatException("User already logged in. ");
            loggedClients.put(userFound.getId(), observer);
            return userFound;
        }else{
//            throw new ServerException("Authentication failed. ");
            return null;

        }
    }

    public synchronized void logout(Angajat user, IChatObserver observer) throws ChatException {
//        , IObserver client
        Angajat userFound = ((DBRepoAngajat) repository_angajat).authenticate(user.getUsername(), user.getPassword());
        IChatObserver localClient = loggedClients.remove(userFound.getId());
        if(localClient == null)
            throw new ChatException("User " + userFound.getId() + " is not logged in. ");
    }

}