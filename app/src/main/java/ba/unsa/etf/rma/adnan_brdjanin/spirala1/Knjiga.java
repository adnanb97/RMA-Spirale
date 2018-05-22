package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Adnan on 3/26/2018.
 */

public class Knjiga implements Parcelable {
    // novi parametri
    String id;
    String naziv;
    ArrayList<Autor> autori;
    String opis;
    String datumObjavljivanja;
    URL slika;
    int brojStrinica;

    //ctor
    Knjiga(String _id, String _naziv, ArrayList<Autor> _autori, String _opis, String _datumObjavljivanja, URL _slika, int _brojStrinica) {
        id = _id;
        naziv = _naziv;
        autori = _autori;
        opis = _opis;
        datumObjavljivanja = _datumObjavljivanja;
        slika = _slika;
        brojStrinica = _brojStrinica;
    }

    //getteri
    String getNaziv() { return naziv; }
    ArrayList<Autor> getAutori() {
        String fetchAutor = "";
        for (int i = 0; i < autori.size(); i++) {
            fetchAutor += autori.get(i).imeiPrezime;
            if (i != autori.size() - 1) fetchAutor += ',';
        }
        return autori;
    }
    String getOpis() { return opis; }
    String getDatumObjavljivanja() { return datumObjavljivanja; }
    URL getSlika() { return slika; }
    int getBrojStrinica() { return brojStrinica; }

    //setteri
    void setNaziv(String _naziv) { naziv = _naziv; }
    void setAutori(ArrayList<Autor> _autori) { autori = _autori; }
    void setOpis(String _opis) { opis = _opis; }
    void setDatumObjavljivanja(String _datumObjavljivanja) { datumObjavljivanja = _datumObjavljivanja; }
    void setSlika(URL _slika) { slika = _slika; }
    void setBrojStrinica(int _brojStrinica) { brojStrinica = _brojStrinica; }

    public String pisac;
    public String nazivDjela = naziv;
    public String kategorija;
    public Bitmap slikaBmp;
    public boolean selektovan;

    Knjiga() {
        pisac = nazivDjela = kategorija = ""; selektovan = false;
    }

    Knjiga(String _pisac, String _nazivDjela, String _kategorija, Bitmap _slika) {
        pisac = _pisac;
        naziv = _nazivDjela;
        kategorija = _kategorija;
        slikaBmp = _slika;
        selektovan = false;
    }
    Knjiga(String _pisac, String _nazivDjela, String _kategorija) {
        pisac = _pisac;
        naziv = _nazivDjela;
        kategorija = _kategorija;
        selektovan = false;
    }

    private Knjiga(Parcel in) {
        pisac = in.readString();
        naziv = in.readString();
        kategorija = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(pisac);
        out.writeString(naziv);
        out.writeString(kategorija);
    }

    public static final Parcelable.Creator<Knjiga> CREATOR = new Parcelable.Creator<Knjiga>() {
        @Override
        public Knjiga createFromParcel(Parcel in) {
            return new Knjiga(in);
        }
        @Override
        public Knjiga[] newArray(int size) {
            return new Knjiga[size];
        }
    };
}
