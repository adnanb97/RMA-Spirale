package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.app.PendingIntent.getActivity;


public class KategorijeAkt extends AppCompatActivity {
    public static ArrayList<Knjiga> listaKnjiga = new ArrayList<>();
    public static ArrayList<String> lista = new ArrayList<String>();
    public static Boolean siriL = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);

        if (savedInstanceState != null) {
            listaKnjiga = savedInstanceState.getParcelableArrayList("mojaListaPodataka");
        }


        FragmentManager fm = getSupportFragmentManager();
        FrameLayout ldetalji = (FrameLayout)findViewById(R.id.mjestoF3);

        siriL = false;

        if (ldetalji != null) {
            siriL = true;
            ListeFragment fd = (ListeFragment)fm.findFragmentById(R.id.mjestoF1);
            KnjigeFragment kf = (KnjigeFragment) fm.findFragmentById(R.id.mjestoF3);
            if (fd == null) {
                fd = new ListeFragment();
                Bundle argumenti = new Bundle();
                argumenti.putParcelableArrayList("mojaListaPodataka", listaKnjiga);
                fd.setArguments(argumenti);
                fm.beginTransaction().replace(R.id.mjestoF1, fd).commit();
            }
            if (kf == null) {
                kf = new KnjigeFragment();

                Bundle argumenti = new Bundle();
                argumenti.putParcelableArrayList("mojaListaPodataka", listaKnjiga);
                kf.setArguments(argumenti);
                fm.beginTransaction().replace(R.id.mjestoF3, kf).commit();
            }
        } else {
            ListeFragment fd = (ListeFragment)fm.findFragmentById(R.id.mjestoF1);
            if (fd == null) {
                fd = new ListeFragment();
                fm.beginTransaction().replace(R.id.mjestoF1, fd).commit();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putParcelableArrayList("mojaListaPodataka", listaKnjiga);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        listaKnjiga = savedInstanceState.getParcelableArrayList("mojaListaPodataka");
    }
}
