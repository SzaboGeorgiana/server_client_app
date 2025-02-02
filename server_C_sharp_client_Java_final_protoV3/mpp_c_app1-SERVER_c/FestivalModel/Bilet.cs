using System;

namespace FestivalModel
{
    [Serializable]

    public class Bilet : Identifier<long>
    {
        public long IdBilet;
        public long IdAngajat { get; set; }


        public long IdClient { get; set; }
        public long IdSpectacol { get; set; }

        public int NrLocuri { get; set; }

        public Bilet(long idAngajat, long idClient, long idSpectacol, int nrLocuri)
        {
            this.IdAngajat = idAngajat;
            this.IdClient = idClient;
            this.IdSpectacol = idSpectacol;
            this.NrLocuri = nrLocuri;
        }
/*
@Override
public long getId() {
    return id_b;
}

@Override
public void setId(long aLong) {
    this.id_b = aLong;
}*/


        public String toString()
        {
            return "Bilet{" +
                   "id_an=" + IdAngajat +
                   ", id_cl=" + IdClient +
                   ", id_s=" + IdSpectacol +
                   ", nr_locuri=" + NrLocuri +
                   '}';
        }
    }
}