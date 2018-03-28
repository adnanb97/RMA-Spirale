package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.app.Application;
import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Adnan on 3/26/2018.
 */

public class Knjiga {
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
}
