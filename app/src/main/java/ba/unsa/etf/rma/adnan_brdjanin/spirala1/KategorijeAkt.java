package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.app.PendingIntent.getActivity;


public class KategorijeAkt extends AppCompatActivity {
    public static ArrayList<Knjiga> listaKnjiga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategorije_akt);

        listaKnjiga = new ArrayList<Knjiga>();

        final Button dugmeDodajKategoriju = (Button)findViewById(R.id.dDodajKategoriju);
        dugmeDodajKategoriju.setEnabled(false);

        final EditText tekstPretraga = (EditText)findViewById(R.id.tekstPretraga);
        final Button dugmePretraga = (Button)findViewById(R.id.dPretraga);
        final ListView listaKategorija = (ListView)findViewById(R.id.listaKategorija);
        final Button dugmeDodajKnjigu = (Button)findViewById(R.id.dDodajKnjigu);

        final ArrayList<String> lista = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listaKategorija.setAdapter(adapter);

        dugmePretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.getFilter().filter(tekstPretraga.getText().toString());

                if (adapter.getCount() == 0) {
                    dugmeDodajKategoriju.setEnabled(true);
                }

            }
        });

        dugmeDodajKategoriju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uneseniTekst = tekstPretraga.getText().toString();

                if (uneseniTekst != null && !uneseniTekst.isEmpty()) {
                    lista.add(0, uneseniTekst);
                    adapter.add(uneseniTekst);
                    adapter.notifyDataSetChanged();
                }
                Collections.sort(lista, new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });
                tekstPretraga.setText("");
                dugmeDodajKategoriju.setEnabled(false);
            }
        });

        dugmeDodajKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DodavanjeKnjigeAkt.class);
                i.putExtra("lista", lista);
                startActivity(i);
            }
        });


        listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ListaKnjigaAkt.class);
                i.putExtra("kategorija", listaKategorija.getItemAtPosition(position).toString());
                startActivity(i);
            }
        });
    }

}
