package ba.unsa.etf.rma.adnan_brdjanin.spirala1;


import java.util.ArrayList;

public class Autor {
    String imeiPrezime;
    ArrayList<String> knjige = new ArrayList<>();

    public Autor() {}
    //ctor
    public Autor(String _imeiPrezime, String _idKnjige) {
        imeiPrezime = _imeiPrezime;
        knjige.add(_idKnjige);
    }

    //getteri
    String getImeiPrezime() { return imeiPrezime; }
    ArrayList<String> getKnjige() { return knjige; }

    //setteri
    void setImeiPrezime(String _imeiPrezime) { imeiPrezime = _imeiPrezime; }
    void setKnjige(ArrayList<String> _knjige) { knjige = _knjige; }

    void dodajKnjigu(String id) {
        if (!knjige.contains(id))
            knjige.add(id);
    }
}
