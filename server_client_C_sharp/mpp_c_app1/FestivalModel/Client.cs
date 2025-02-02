using System;

namespace FestivalModel
{

    [Serializable]

    public class Client : Persoana
    {
        public Client(long idPersoana, string nume, string prenume) : base(idPersoana, nume, prenume)
        {
        }

        public Client(string nume, string prenume) : base(nume, prenume)
        {
        }
    }
}