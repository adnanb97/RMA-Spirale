package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class DodavanjeKnjigeFragment extends Fragment {
    public DodavanjeKnjigeFragment() {
        // Required empty public constructor
    }

    public Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView)getView().findViewById(R.id.naslovnaStr);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dodavanje_knjige, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Spinner spinnerPrikaz = (Spinner)getView().findViewById(R.id.sKategorijaKnjige);

        Bundle bundleObject = getActivity().getIntent().getExtras();


        final EditText imeAutora = (EditText)getView().findViewById(R.id.imeAutora);
        final EditText nazivDjela = (EditText)getView().findViewById(R.id.nazivKnjige);

        // populisanje spinnera
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            ArrayList<String> lista = extras.getStringArrayList("lista");
            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lista);
            spinnerPrikaz.setAdapter(adapterSpinner);

        }
        //dodavanje knjige
        final Button dugmeUpisiKnjigu = (Button)getView().findViewById(R.id.dUpisiKnjigu);
        final ImageView naslovna = (ImageView)getView().findViewById(R.id.naslovnaStr);

        dugmeUpisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pisac = imeAutora.getText().toString();
                String naziv = nazivDjela.getText().toString();
                String kategorija;

                try {
                    kategorija = spinnerPrikaz.getSelectedItem().toString();
                    KategorijeAkt.listaKnjiga.add(new Knjiga(pisac, naziv, kategorija, bitmap));
                    Toast.makeText(getActivity(), getString(R.string.Toast_poruka_dodavanje_knjige), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imeAutora.setText("");
                nazivDjela.setText("");
            }
        });

        final Button dugmeIzadji = (Button)getView().findViewById(R.id.dPonisti);

        dugmeIzadji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        final Button dugmeDodajSliku = (Button)getView().findViewById(R.id.dNadjiSliku);

        dugmeDodajSliku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
    }
}
