using System;
using System.Collections.Generic;
using FestivalModel;
using FestivalService;

namespace FestivalClient;

public class ClientCtrl : IObserver
{
    //  public event EventHandler<UserEvent> updateEvent; //ctrl calls it when it has received an update
    private readonly IService server;
    private Angajat currentUser;
    private Form1 _setForm;

    public ClientCtrl(IService server)
    {
        this.server = server;
        currentUser = null;
    }

    public void setForm(Form1 _form)
    {
        _setForm = _form;
    }

    public void rezervareReceived(Bilet bilet)
    {
        _setForm.rezervareReceived(bilet);
    }
    
    public Angajat Valid(String userId, String pass)
    {
        Angajat user= server.Valid(userId,pass,this);
        Console.WriteLine("Login succeeded ....");
        
        Console.WriteLine("Current user {0}", user);
        return user;
    }
    public void logout()
    {
        Console.WriteLine("Ctrl logout");
      //  server.logout(currentUser, this);
        currentUser = null;
    }


    public List<string> FindArtistiSpectacoleBilete()
    {
        List<String> lista=new List<string>();
         lista = server.FindArtistiSpectacoleBilete();
       
        return lista;
    }

    public List<string> FindArtistiSpectacoleBileteDupaData(DateTime value)
    {
        List<String> lista=new List<string>();
        lista = server.FindArtistiSpectacoleBileteDupaData(value);
        return lista;
    }

    public Spectacol FindArtistiSpectacoleBileteIndex(int index)
    {
        Spectacol sp = server.FindArtistiSpectacoleBileteIndex(index);
        return sp;
    }

    public void SaveBilet(string textBox4Text, string textBox3Text, int nrLocuri, long angajatIdPersoana, long spectacolIdSpectacol)
    {
        server.SaveBilet( textBox4Text,  textBox3Text,  nrLocuri,  angajatIdPersoana, spectacolIdSpectacol);
    }

    public Spectacol FindArtistiSpectacoleBileteDupaDataIndex(DateTime value, int index)
    {
        Spectacol sp = server.FindArtistiSpectacoleBileteDupaDataIndex(value,index);
        return sp;
        
    }
}