package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaKnjigaAkt extends AppCompatActivity {
    public String kliknut;
    public ArrayList<Knjiga> nova = new ArrayList<Knjiga>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_knjiga_akt);

        final Button natrag = (Button)findViewById(R.id.dPovratak);

        natrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ListView listaKnjigaView = (ListView)findViewById(R.id.listaKnjiga);
        CustomAdapter customAdapter = new CustomAdapter();
        Intent intent = getIntent();
        kliknut = intent.getExtras().getString("kategorija");

        for (int i = 0; i < KategorijeAkt.listaKnjiga.size(); i++)
            if (KategorijeAkt.listaKnjiga.get(i).kategorija.equals(kliknut))
                nova.add(KategorijeAkt.listaKnjiga.get(i));
        listaKnjigaView.setAdapter(customAdapter);

        listaKnjigaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                //for (int i = 0; i < nova.size(); i++) nova.get(i).selektovan = false;
                nova.get(position).selektovan = true;
            }


        });

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return nova.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);

            Knjiga trazena = nova.get(i);
            if (trazena.selektovan) view.setBackgroundColor(0xffaabbed);

            ImageView imageView = (ImageView) view.findViewById(R.id.eNaslovna);
            TextView textView_naziv = (TextView) view.findViewById(R.id.eNaziv);
            TextView textView_autor = (TextView) view.findViewById(R.id.eAutor);



            imageView.setImageBitmap(trazena.slika);
            textView_naziv.setText(trazena.nazivDjela);
            textView_autor.setText(trazena.pisac);


            return view;
        }

    }
}
