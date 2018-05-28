package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.net.URL;
import java.util.ArrayList;

public class BazaOpenHelper extends SQLiteOpenHelper {

    private static BazaOpenHelper sInstance;
    public static synchronized BazaOpenHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BazaOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    private BazaOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /*public BazaOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/
    public static final String DATABASE_NAME = "mojaBaza.db";
    public static final String[] DATABASE_TABLE = {"Kategorija", "Knjiga", "Autor", "Autorstvo"};
    public static final int DATABASE_VERSION = 3;

    //tabela Kategorija
    public static final String KATEGORIJA_ID = "_id";
    public static final String KATEGORIJA_NAZIV = "naziv";

    // tabela Knjiga
    public static final String KNJIGA_ID = "_id";
    public static final String KNJIGA_NAZIV = "naziv";
    public static final String KNJIGA_OPIS = "opis";
    public static final String KNJIGA_DATUMOBJAVLJIVANJA = "datumObjavljivanja";
    public static final String KNJIGA_BROJSTRANICA = "brojStranica";
    public static final String KNJIGA_IDWEBSERVIS = "idWebServis";
    public static final String KNJIGA_IDKATEGORIJE = "idkategorije";
    public static final String KNJIGA_SLIKA = "slika";
    public static final String KNJIGA_PREGLEDANA = "pregledana";

    //tabela autor
    public static final String AUTOR_ID = "_id";
    public static final String AUTOR_IME = "ime";

    //tabela autorstvo
    public static final String AUTORSTVO_ID = "_id";
    public static final String AUTORSTVO_IDAUTORA = "idautora";
    public static final String AUTORSTVO_IDKNJIGE = "idknjige";


    // upiti za kreiranje baze
    private static final String DATABASE_CREATE_KATEGORIJA = "create table " + DATABASE_TABLE[0] + "(" +
            KATEGORIJA_ID + " integer primary key autoincrement, " + KATEGORIJA_NAZIV + " text not null );";

    private static final String DATABASE_CREATE_KNJIGA = "create table " + DATABASE_TABLE[1] + "(" +
            KNJIGA_ID + " integer primary key autoincrement, " + KNJIGA_NAZIV + " text not null, " +
            KNJIGA_OPIS + " text not null, " + KNJIGA_DATUMOBJAVLJIVANJA + " text not null, " +
            KNJIGA_BROJSTRANICA + " integer not null, " + KNJIGA_IDWEBSERVIS + " text not null, " +
            KNJIGA_IDKATEGORIJE + " integer not null, " + KNJIGA_SLIKA + " text not null, " + KNJIGA_PREGLEDANA + " integer not null);";

    private static final String DATABASE_CREATE_AUTOR = "create table " + DATABASE_TABLE[2] + "(" +
            AUTOR_ID + " integer primary key autoincrement, " + AUTOR_IME + " text not null );";

    private static final String DATABASE_CREATE_AUTORSTVO = "create table " + DATABASE_TABLE[3] + "(" +
            AUTORSTVO_ID + " integer primary key autoincrement, " + AUTORSTVO_IDAUTORA + " integer not null, " +
            AUTORSTVO_IDKNJIGE + " integer not null );";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_KATEGORIJA);
        db.execSQL(DATABASE_CREATE_KNJIGA);
        db.execSQL(DATABASE_CREATE_AUTOR);
        db.execSQL(DATABASE_CREATE_AUTORSTVO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        for (int x = 0; x < 4; x++)
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE[x]);
        onCreate(db);
    }

    public long dajIdKategorije(String naziv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = KATEGORIJA_NAZIV + "= \"" + naziv + "\"";
        Cursor cursor = db.query(this.DATABASE_TABLE[0], new String[]{KATEGORIJA_NAZIV, KATEGORIJA_ID}, where, null, null, null, null);
        int idx = 0;
        while(cursor.moveToNext())
            idx = cursor.getInt(cursor.getColumnIndex(KATEGORIJA_ID));
        return idx;
    }

    public long dajIdAutora(String naziv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = AUTOR_IME + "= \"" + naziv + "\"";
        Cursor cursor = db.query(this.DATABASE_TABLE[2], new String[]{AUTOR_IME, AUTOR_ID}, where, null, null, null, null);
        int idx = 0;
        while(cursor.moveToNext())
            idx = cursor.getInt(cursor.getColumnIndex(AUTOR_ID));
        return idx;
    }

    public long dodajKategoriju(String naziv) {
        String where = KATEGORIJA_NAZIV + "= \"" + naziv + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(this.DATABASE_TABLE[0], new String[]{KATEGORIJA_NAZIV}, where, null, null, null, null);
        int brojEl = 0;
        while (cursor.moveToNext()) brojEl++;
        if (brojEl != 0) return -1;

        ContentValues novi = new ContentValues();
        novi.put(KATEGORIJA_NAZIV, naziv);
        long id = db.insert(this.DATABASE_TABLE[0], null, novi);
        return id;
    }

    public long dodajKnjigu(Knjiga knjiga) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = KATEGORIJA_NAZIV + "= \"" + knjiga.kategorija + "\"";;
        Cursor cursor = db.query(this.DATABASE_TABLE[0], new String[]{KATEGORIJA_NAZIV, KATEGORIJA_ID}, where, null, null, null, null);
        long idKategorije = 0;
        if (cursor.moveToFirst())
            idKategorije = cursor.getInt(cursor.getColumnIndex(KATEGORIJA_ID));

        ContentValues novi = new ContentValues();
        //novi.put(KNJIGA_ID, knjiga.id);
        novi.put(KNJIGA_NAZIV, knjiga.naziv);
        novi.put(KNJIGA_OPIS, knjiga.opis);
        novi.put(KNJIGA_BROJSTRANICA, knjiga.brojStrinica);
        novi.put(KNJIGA_DATUMOBJAVLJIVANJA, knjiga.datumObjavljivanja);
        novi.put(KNJIGA_IDKATEGORIJE, idKategorije);
        novi.put(KNJIGA_IDWEBSERVIS, "123");
        novi.put(KNJIGA_SLIKA, knjiga.slika.toString());
        novi.put(KNJIGA_PREGLEDANA, 0);

        where = KNJIGA_ID + "= \"" + knjiga.id + "\"";
        cursor = db.query(this.DATABASE_TABLE[1], new String[]{KNJIGA_NAZIV}, where, null, null, null, null);
        int brojEl = 0, idAutora;
        while (cursor.moveToNext()) brojEl++;

        if (brojEl != 0) return -1;
        cursor.moveToFirst();
        long knjigaVracenId = db.insert(this.DATABASE_TABLE[1], null, novi);

        for (int i = 0; i < knjiga.autori.size(); i++) {
            String pisac = knjiga.autori.get(i).imeiPrezime;
            where = AUTOR_IME + "= \"" + pisac + "\"";
            cursor = db.query(this.DATABASE_TABLE[2], new String[]{AUTOR_IME, AUTOR_ID}, where, null, null, null, null);
            brojEl = 0;
            while (cursor.moveToNext()) brojEl++;
            long autorVracenId = 0, autorstvoVracenId = 0;
            if (brojEl == 0) {
                ContentValues autorPaket = new ContentValues();
                autorPaket.put(AUTOR_IME, pisac);
                autorVracenId = db.insert(this.DATABASE_TABLE[2], null, autorPaket);

                ContentValues autorstvoPaket = new ContentValues();
                autorstvoPaket.put(AUTORSTVO_IDAUTORA, autorVracenId);
                autorstvoPaket.put(AUTORSTVO_IDKNJIGE, knjigaVracenId);
                autorstvoVracenId = db.insert(this.DATABASE_TABLE[3], null, autorstvoPaket);
            } else {
                //cursor.moveToFirst();
                if (cursor.moveToFirst())
                    idAutora = cursor.getInt(cursor.getColumnIndex(AUTOR_ID));
                else idAutora = 0;
                ContentValues autorstvoPaket = new ContentValues();
                autorstvoPaket.put(AUTORSTVO_IDAUTORA, idAutora);
                autorstvoPaket.put(AUTORSTVO_IDKNJIGE, knjigaVracenId);
                autorstvoVracenId = db.insert(this.DATABASE_TABLE[3], null, autorstvoPaket);
            }
        }
        return knjigaVracenId;
    }

    public ArrayList<String> dajSveKategorije() {
        ArrayList<String> ret = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] koloneRezultat = new String[]{KATEGORIJA_ID, KATEGORIJA_NAZIV};
        Cursor cursor = db.query(this.DATABASE_TABLE[0], koloneRezultat, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String naziv;
            naziv = cursor.getString(cursor.getColumnIndex(KATEGORIJA_NAZIV));
            ret.add(naziv);
        }
        return ret;
    }
    public ArrayList<Autor> dajSveAutore() {
        ArrayList<Autor> ret = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String koloneRezultat[] = new String[]{AUTOR_ID, AUTOR_IME};
        Cursor cursor = db.query(this.DATABASE_TABLE[2], koloneRezultat, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String naziv;
            int id;
            naziv = cursor.getString(cursor.getColumnIndex(AUTOR_IME));
            id = cursor.getInt(cursor.getColumnIndex(AUTOR_ID));
            ArrayList<Knjiga> knjige = new ArrayList<>();
            knjige = knjigeAutora(id);
            for (int i = 0; i < knjige.size(); i++)
                ret.add(new Autor(naziv, knjige.get(i).id));
        }
        return ret;
    }
    public ArrayList<Knjiga> dajSveKnjige() {
        ArrayList<Knjiga> ret = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] koloneRezultat = new String[]{KATEGORIJA_ID, KATEGORIJA_NAZIV};
        Cursor cursor = db.query(this.DATABASE_TABLE[0], koloneRezultat, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String naziv;
            int id;
            id = cursor.getInt(cursor.getColumnIndex(KATEGORIJA_ID));
            naziv = cursor.getString(cursor.getColumnIndex(KATEGORIJA_NAZIV));
            ArrayList<Knjiga> knjigeKat = (knjigeKategorije(id));
            for (int i = 0; i < knjigeKat.size(); i++) {
                knjigeKat.get(i).kategorija = naziv;
                ret.add(knjigeKat.get(i));
            }
        }
        return ret;
    }
    public ArrayList<Knjiga> knjigeKategorije(long idKategorije) {
        ArrayList<Knjiga> ret = new ArrayList<>();
        String[] koloneRezultat = new String[]{KNJIGA_ID, KNJIGA_BROJSTRANICA, KNJIGA_DATUMOBJAVLJIVANJA, KNJIGA_NAZIV, KNJIGA_OPIS, KNJIGA_IDKATEGORIJE, KNJIGA_SLIKA, KNJIGA_PREGLEDANA};
        String where = KNJIGA_IDKATEGORIJE + "= \"" + idKategorije + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(this.DATABASE_TABLE[1], koloneRezultat, where, null, null, null, null);
        while (cursor.moveToNext()) {
            String naziv, datumObjave, opis;
            int brojStranica, idKnjige, pregledana;
            String urlSlika;
            naziv = cursor.getString(cursor.getColumnIndex(KNJIGA_NAZIV));
            datumObjave = cursor.getString(cursor.getColumnIndex(KNJIGA_DATUMOBJAVLJIVANJA));
            opis = cursor.getString(cursor.getColumnIndex(KNJIGA_OPIS));
            brojStranica = cursor.getInt(cursor.getColumnIndex(KNJIGA_BROJSTRANICA));
            idKnjige = cursor.getInt(cursor.getColumnIndex(KNJIGA_ID));
            urlSlika = cursor.getString(cursor.getColumnIndex(KNJIGA_SLIKA));
            pregledana = cursor.getInt(cursor.getColumnIndex(KNJIGA_PREGLEDANA));

            ArrayList<Autor> listaAutora = new ArrayList<>();

            String where2 = AUTORSTVO_IDKNJIGE + "= \"" + idKnjige + "\"";
            Cursor cursor2 = db.query(this.DATABASE_TABLE[3], new String[]{AUTORSTVO_IDAUTORA}, where2, null, null, null, null);
            while (cursor2.moveToNext()) {
                int idAutora = cursor2.getInt(cursor2.getColumnIndex(AUTORSTVO_IDAUTORA));
                String where3 = AUTOR_ID + "= \"" + idAutora + "\"";

                Cursor cursor3 = db.query(this.DATABASE_TABLE[2], new String[]{AUTOR_IME}, where3, null, null, null, null);
                while (cursor3.moveToNext()) {
                    String imeAutora = cursor3.getString(cursor3.getColumnIndex(AUTOR_IME));
                    listaAutora.add(new Autor(imeAutora, String.valueOf(idKnjige)));
                }
            }
            URL a = null;
            try {
                a = new URL(urlSlika);
                Knjiga x = new Knjiga(String.valueOf(idKnjige), naziv, listaAutora, opis, datumObjave, a, brojStranica);
                ret.add(x);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        return ret;
    }
    public ArrayList<Knjiga> knjigeAutoraPoImenu(String ime) {
        ArrayList<Knjiga> ret = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] koloneRezultat = new String[]{AUTOR_IME, AUTOR_ID};
        String where = AUTOR_IME + "= \"" + ime + "\"";
        Cursor cursor = db.query(this.DATABASE_TABLE[2], koloneRezultat, where, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(AUTOR_ID));
            ArrayList<Knjiga> knjigeAutora = knjigeAutora(id);
            for (int i = 0; i < knjigeAutora.size(); i++)
                ret.add(knjigeAutora.get(i));
        }
        return ret;
    }
    public ArrayList<Knjiga> knjigeAutora(long idAutora) {
        ArrayList<Knjiga> ret = new ArrayList<>();
        String[] koloneRezultat = new String[]{AUTORSTVO_IDAUTORA, AUTORSTVO_IDKNJIGE};
        String where = AUTORSTVO_IDAUTORA + "= \"" + idAutora + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(this.DATABASE_TABLE[3], koloneRezultat, where, null, null, null, null);
        while (cursor.moveToNext()) {
            int idAutoraVracen, idKnjigeVracen;
            idAutoraVracen = cursor.getInt(cursor.getColumnIndex(AUTORSTVO_IDAUTORA));
            idKnjigeVracen = cursor.getInt(cursor.getColumnIndex(AUTORSTVO_IDKNJIGE));
            ArrayList<Autor> listaAutora = new ArrayList<>();

            String where2 = KNJIGA_ID + "= \"" + idKnjigeVracen + "\"";
            Cursor cursor2 = db.query(this.DATABASE_TABLE[1], new String[]{KNJIGA_ID, KNJIGA_BROJSTRANICA, KNJIGA_DATUMOBJAVLJIVANJA, KNJIGA_NAZIV, KNJIGA_OPIS, KNJIGA_SLIKA, KNJIGA_PREGLEDANA}, where2, null, null, null, null);
            while (cursor2.moveToNext()) {
                String naziv, datumObjave, opis, urlSlika;
                int brojStranica, idKnjige, pregledana;
                naziv = cursor2.getString(cursor2.getColumnIndex(KNJIGA_NAZIV));
                datumObjave = cursor2.getString(cursor2.getColumnIndex(KNJIGA_DATUMOBJAVLJIVANJA));
                opis = cursor2.getString(cursor2.getColumnIndex(KNJIGA_OPIS));
                brojStranica = cursor2.getInt(cursor2.getColumnIndex(KNJIGA_BROJSTRANICA));
                idKnjige = cursor2.getInt(cursor2.getColumnIndex(KNJIGA_ID));
                urlSlika = cursor2.getString(cursor2.getColumnIndex(KNJIGA_SLIKA));
                pregledana = cursor2.getInt(cursor2.getColumnIndex(KNJIGA_PREGLEDANA));
                URL a = null;
                try {
                    a = new URL(urlSlika);
                    Knjiga x = new Knjiga(String.valueOf(idKnjige), naziv, listaAutora, opis, datumObjave, a, brojStranica);
                    ret.add(x);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        return ret;
    }
    public void selektujKnjigu(String idKnjige) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KNJIGA_PREGLEDANA, 1);

        db.update(this.DATABASE_TABLE[1], cv, KNJIGA_ID + "=" + idKnjige, null);
    }
}
