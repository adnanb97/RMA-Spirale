package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Adnan on 3/26/2018.
 */

public class Knjiga implements Parcelable {
    public String pisac;
    public String nazivDjela;
    public String kategorija;
    public Bitmap slika;
    public boolean selektovan;

    Knjiga() {
        pisac = nazivDjela = kategorija = ""; selektovan = false;
    }

    Knjiga(String _pisac, String _nazivDjela, String _kategorija, Bitmap _slika) {
        pisac = _pisac;
        nazivDjela = _nazivDjela;
        kategorija = _kategorija;
        slika = _slika;
        selektovan = false;
    }
    Knjiga(String _pisac, String _nazivDjela, String _kategorija) {
        pisac = _pisac;
        nazivDjela = _nazivDjela;
        kategorija = _kategorija;
        selektovan = false;
    }

    private Knjiga(Parcel in) {
        pisac = in.readString();
        nazivDjela = in.readString();
        kategorija = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(pisac);
        out.writeString(nazivDjela);
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
