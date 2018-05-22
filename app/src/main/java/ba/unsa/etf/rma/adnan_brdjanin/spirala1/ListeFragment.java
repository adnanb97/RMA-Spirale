package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListeFragment extends Fragment {

    public ListeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liste, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button dugmeDodajKategoriju = (Button)getView().findViewById(R.id.dDodajKategoriju);
        dugmeDodajKategoriju.setEnabled(false);

        final EditText tekstPretraga = (EditText)getView().findViewById(R.id.tekstPretraga);
        final Button dugmePretraga = (Button)getView().findViewById(R.id.dPretraga);
        final ListView listaKategorija = (ListView)getView().findViewById(R.id.listaKategorija);
        final Button dugmeDodajKnjigu = (Button)getView().findViewById(R.id.dDodajKnjigu);
        final Button dugmeDKategorije = (Button)getView().findViewById(R.id.dKategorije);
        final Button dugmeDAutori = (Button)getView().findViewById(R.id.dAutori);
        final Button dugmeOnline = (Button)getView().findViewById(R.id.dDodajOnline);

        //final ArrayList<String> lista = new ArrayList<String>();
        final ArrayAdapter<String> adapter;

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, KategorijeAkt.lista);
        listaKategorija.setAdapter(adapter);

        dugmeDKategorije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, KategorijeAkt.lista);
                listaKategorija.setAdapter(adapter2);

                dugmePretraga.setVisibility(View.VISIBLE);
                dugmeDodajKategoriju.setVisibility(View.VISIBLE);
                tekstPretraga.setVisibility(View.VISIBLE);
            }
        });

        dugmeDAutori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dugmePretraga.setVisibility(View.GONE);
                dugmeDodajKategoriju.setVisibility(View.GONE);
                tekstPretraga.setVisibility(View.GONE);
                ArrayList<String> listaAutora = new ArrayList<>();

                /*for (int i = 0; i < KategorijeAkt.listaKnjiga.size(); i++) {
                    int brojDjela = 0;
                    Boolean dodajem = true;

                    for (int j = 0; j < KategorijeAkt.listaKnjiga.size(); j++)
                        if (KategorijeAkt.listaKnjiga.get(j).pisac.equals(KategorijeAkt.listaKnjiga.get(i).pisac)) brojDjela++;

                    if (!listaAutora.contains("Ime: \"" + KategorijeAkt.listaKnjiga.get(i).pisac + "\" Broj djela: " + brojDjela)) {
                        listaAutora.add("Ime: \"" + KategorijeAkt.listaKnjiga.get(i).pisac + "\" Broj djela: " + brojDjela);
                    }
                }*/

                for (int i = 0; i < KategorijeAkt.listaAutora.size(); i++) {
                    int brojDjela = KategorijeAkt.listaAutora.get(i).knjige.size();
                    /*for (int j = 0; j < KategorijeAkt.listaKnjiga.size(); j++)
                        for (int k = 0; k < KategorijeAkt.listaKnjiga.get(j).autori.size(); k++)
                            if (KategorijeAkt.listaKnjiga.get(j).autori.get(k).equals(KategorijeAkt.listaAutora.get(i).imeiPrezime)) brojDjela++;
                    */
                    if (!listaAutora.contains("Ime: \"" + KategorijeAkt.listaAutora.get(i).imeiPrezime + "\" Broj djela: " + brojDjela))
                        listaAutora.add("Ime: \"" + KategorijeAkt.listaAutora.get(i).imeiPrezime + "\" Broj djela: " + brojDjela);

                }
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listaAutora);
                listaKategorija.setAdapter(adapter3);
            }
        });
        dugmePretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaKategorija.setAdapter(adapter);
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
                    KategorijeAkt.lista.add(0, uneseniTekst);
                    adapter.add(uneseniTekst);
                    adapter.notifyDataSetChanged();
                }
                Collections.sort(KategorijeAkt.lista, new Comparator<String>() {
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
                Intent i = new Intent(getActivity(), DodavanjeKnjigeAkt.class);
                i.putExtra("lista", KategorijeAkt.lista);
                startActivity(i);
            }
        });


        dugmeOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FragmentOnlineAkt.class);
                i.putExtra("lista", KategorijeAkt.lista);
                startActivity(i);
            }
        });

        listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = listaKategorija.getItemAtPosition(position).toString();
                if (KategorijeAkt.siriL) {
                    /*FragmentManager fm = getFragmentManager();
                    Bundle param = new Bundle();
                    param.putString("kategorija", s);

                    KnjigeFragment fragment = new KnjigeFragment();
                    fm.beginTransaction().replace(R.id.mjestoF3, fragment).commit();*/
                    Intent i = new Intent(getActivity(), KategorijeAkt.class);
                    i.putExtra("kategorija", s);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getActivity(), ListaKnjigaAkt.class);
                    i.putExtra("kategorija", listaKategorija.getItemAtPosition(position).toString());
                    startActivity(i);
                }
            }
        });
    }
}
