using System;

namespace FestivalModel
{
    [Serializable]

    public class Spectacol : Identifier<long>
    {
        public long IdSpectacol;

        public long IdArtist { get; set; }
        public String Locatie { get; set; }
        public DateTime DataIncepere { get; set; }
        public int NumarLocuri { get; set; }

        public Spectacol(long IdArtist, String Locatie, DateTime DataIncepere, int NumarLocuri)
        {
            this.IdArtist = IdArtist;
            this.Locatie = Locatie;
            this.DataIncepere = DataIncepere;
            this.NumarLocuri = NumarLocuri;
        }
/*
@Override
public Long getId() {
    return id_s;
}

@Override
public void setId(Long aLong) {
    this.id_s=aLong;
}*/


        public String toString()
        {
            return "Spectecol{" +
                   "id_ar=" + IdArtist +
                   ", locatie='" + Locatie + '\'' +
                   ", data_inc=" + DataIncepere +
                   ", nr_locuri=" + NumarLocuri +
                   '}';
        }
    }
}