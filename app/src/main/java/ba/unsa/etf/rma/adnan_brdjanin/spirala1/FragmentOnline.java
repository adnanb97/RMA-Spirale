package ba.unsa.etf.rma.adnan_brdjanin.spirala1;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentOnline extends Fragment implements DohvatiKnjige.IDohvatiKnjigeDone, DohvatiNajnovije.IDohvatiNajnovijeDone, MojResultReceiver.Receiver {
    public FragmentOnline() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_online, container, false);
    }
    Button dugmePokreni = null;
    Button dugmeDodaj = null;
    Button dugmePonisti = null;
    Spinner spinnerRez = null;
    Spinner spinnerKat = null;
    EditText txtPretraga = null;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dugmePokreni  = (Button)getView().findViewById(R.id.dRun);
        dugmeDodaj    = (Button)getView().findViewById(R.id.dAdd);
        dugmePonisti  = (Button)getView().findViewById(R.id.dPovratak);
        spinnerRez    = (Spinner)getView().findViewById(R.id.sRezultat);
        spinnerKat    = (Spinner)getView().findViewById(R.id.sKategorije);
        txtPretraga   = (EditText)getView().findViewById(R.id.tekstUpit);

        ArrayAdapter<String> katSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, KategorijeAkt.lista);
        spinnerKat.setAdapter(katSpinner);

        dugmePonisti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        dugmePokreni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tekstUpit = txtPretraga.getText().toString();
                if (tekstUpit.length() >= 6 && tekstUpit.substring(0, 6).equals("autor:")) {
                    String upit = tekstUpit.substring(6);
                    new DohvatiNajnovije((DohvatiNajnovije.IDohvatiNajnovijeDone)FragmentOnline.this).execute(upit);

                } else if (tekstUpit.length() >= 9 && tekstUpit.substring(0, 9).equals("korisnik:")){
                    String upit = tekstUpit.substring(9);

                    Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), KnjigePoznanika.class);
                    MojResultReceiver mReceiver = new MojResultReceiver(new Handler());
                    mReceiver.setReceiver(FragmentOnline.this);
                    intent.putExtra("idKorisnika", upit);
                    intent.putExtra("receiver", mReceiver);
                    getActivity().startService(intent);
                    //new DohvatiNajnovije((DohvatiNajnovije.IDohvatiNajnovijeDone) FragmentOnline.this).execute(upit);

                } else {
                    String[] niz = tekstUpit.split(";");

                    for (int i = 0; i < niz.length; i++)
                        new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone) FragmentOnline.this).execute(niz[i]);

                    //new DohvatiKnjige((DohvatiKnjige.IDohvatiKnjigeDone) FragmentOnline.this).execute(tekstUpit);
                }
            }
        });
        dugmeDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selektovana = null;
                String kategorijaA = null;

                if (spinnerRez.getCount() == 0) {
                    Toast.makeText(getActivity(), R.string.Odaberite_knjigu, Toast.LENGTH_SHORT).show();
                } else if (spinnerKat.getCount() == 0) {
                    Toast.makeText(getActivity(), R.string.Odaberite_kategoriju, Toast.LENGTH_SHORT).show();
                } else {
                    selektovana = spinnerRez.getSelectedItem().toString();
                    kategorijaA = spinnerKat.getSelectedItem().toString();
                    Knjiga x = new Knjiga();
                    for (int i = 0; i < rez.size(); i++)
                        if (rez.get(i).getNaziv().equals(selektovana)) {
                            x = rez.get(i);
                            break;
                        }
                    x.kategorija = kategorijaA;
                    if (!KategorijeAkt.listaKnjiga.contains(x)) {
                        KategorijeAkt.listaKnjiga.add(x);
                        KategorijeAkt.BAZA_PODATAKA.dodajKnjigu(x);
                    }

                    if (x.autori != null)
                    for (int i = 0; i < x.autori.size(); i++) {
                        int indeks = -1;
                        for (int j = 0; j < KategorijeAkt.listaAutora.size(); j++)
                            if (KategorijeAkt.listaAutora.get(j).imeiPrezime.equals(x.autori.get(i).imeiPrezime)) {
                                indeks = j;
                                break;
                            }

                        if (indeks == -1)
                            KategorijeAkt.listaAutora.add(new Autor(x.autori.get(i).imeiPrezime, x.id));
                        else {
                            boolean ima = false;
                            for (int j = 0; j < KategorijeAkt.listaAutora.get(indeks).knjige.size(); j++)
                                if (KategorijeAkt.listaAutora.get(indeks).knjige.get(j).equals(x.id)) {
                                    ima = true;
                                    break;
                                }

                            if (!ima)
                                KategorijeAkt.listaAutora.get(indeks).knjige.add(x.id);
                        }
                    }

                    //KategorijeAkt.lista.add(kategorijaA);
                    Toast.makeText(getActivity(), getString(R.string.Toast_poruka_dodavanje_knjige), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<Knjiga> rez = new ArrayList<>();
    private ArrayList<String> nazivi = new ArrayList<>();

    @Override
    public void onDohvatiDone(ArrayList<Knjiga> x) {
        for (int i = 0; i < x.size(); i++)
            rez.add(x.get(i));
        for (int i = 0; i < rez.size(); i++)
            nazivi.add(rez.get(i).getNaziv());

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nazivi);
        spinnerRez.setAdapter(adapterSpinner);
    }

    @Override
    public void onNajnovijeDone(ArrayList<Knjiga> x) {
        for (int i = 0; i < x.size(); i++)
            rez.add(x.get(i));
        nazivi.clear();
        for (int i = 0; i < rez.size(); i++)
            nazivi.add(rez.get(i).getNaziv());

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nazivi);
        spinnerRez.setAdapter(adapterSpinner);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case 0:
                Toast.makeText(getActivity(), R.string.Servis_pokrenut, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                ArrayList<Knjiga> listaKnjiga = resultData.getParcelableArrayList("listaKnjiga");
                rez = listaKnjiga;
                nazivi.clear();
                for (int i = 0; i < rez.size(); i++)
                    nazivi.add(rez.get(i).getNaziv());

                ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nazivi);
                spinnerRez.setAdapter(adapterSpinner);
                break;
            case 2:
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), R.string.Greska_u_prikupljanju, Toast.LENGTH_SHORT).show();
        }
    }
}
