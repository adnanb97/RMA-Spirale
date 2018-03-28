package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DodavanjeKnjigeAkt extends AppCompatActivity {
    public Bitmap bitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.naslovnaStr);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_knjige_akt);
        final Spinner spinnerPrikaz = (Spinner)findViewById(R.id.sKategorijaKnjige);

        Bundle bundleObject = getIntent().getExtras();


        final EditText imeAutora = (EditText)findViewById(R.id.imeAutora);
        final EditText nazivDjela = (EditText)findViewById(R.id.nazivKnjige);

        // populisanje spinnera
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<String> lista = extras.getStringArrayList("lista");
            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            spinnerPrikaz.setAdapter(adapterSpinner);

        }
        //dodavanje knjige
        final Button dugmeUpisiKnjigu = (Button) findViewById(R.id.dUpisiKnjigu);
        final ImageView naslovna = (ImageView) findViewById(R.id.naslovnaStr);

        dugmeUpisiKnjigu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pisac = imeAutora.getText().toString();
                String naziv = nazivDjela.getText().toString();
                String kategorija;

                try {
                    kategorija = spinnerPrikaz.getSelectedItem().toString();
                    KategorijeAkt.listaKnjiga.add(new Knjiga(pisac, naziv, kategorija, bitmap));
                    Toast.makeText(DodavanjeKnjigeAkt.this, "Knjiga uspjesno dodana!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imeAutora.setText("");
                nazivDjela.setText("");
            }
        });

        final Button dugmeIzadji = (Button)findViewById(R.id.dPonisti);

        dugmeIzadji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Button dugmeDodajSliku = (Button)findViewById(R.id.dNadjiSliku);

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
