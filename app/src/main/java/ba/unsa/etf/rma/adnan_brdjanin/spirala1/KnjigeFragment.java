package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class KnjigeFragment extends Fragment {
    public String kliknut;
    public ArrayList<Knjiga> nova = new ArrayList<Knjiga>();

    public KnjigeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_knjige, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button natrag = (Button)getView().findViewById(R.id.dPovratak);

        natrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        final ListView listaKnjigaView = (ListView)getView().findViewById(R.id.listaKnjiga);
        CustomAdapter customAdapter = new CustomAdapter();
        //Intent intent = getActivity().getIntent();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            kliknut = extras.getString("kategorija");
            String[] nizRijeci = kliknut.split("\"");


            if (nizRijeci.length == 1) {
                for (int i = 0; i < KategorijeAkt.listaKnjiga.size(); i++)
                    if (KategorijeAkt.listaKnjiga.get(i).kategorija.equals(kliknut))
                        nova.add(KategorijeAkt.listaKnjiga.get(i));
                listaKnjigaView.setAdapter(customAdapter);
            } else {

                for (int i = 0; i < KategorijeAkt.listaAutora.size(); i++)
                    if (KategorijeAkt.listaAutora.get(i).imeiPrezime.equals(nizRijeci[1])) {
                        for (int k = 0; k < KategorijeAkt.listaKnjiga.size(); k++)
                            for (int j = 0; j < KategorijeAkt.listaAutora.get(i).knjige.size(); j++)
                                if (KategorijeAkt.listaKnjiga.get(k).id.equals(KategorijeAkt.listaAutora.get(i).knjige.get(j)))
                                    if (!nova.contains(KategorijeAkt.listaKnjiga.get(k)))
                                        nova.add(KategorijeAkt.listaKnjiga.get(k));
                    }
                listaKnjigaView.setAdapter(customAdapter);
            }
            listaKnjigaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

                    view.setSelected(true);
                    //for (int i = 0; i < nova.size(); i++) nova.get(i).selektovan = false;
                    nova.get(position).selektovan = true;

                }


            });
        }
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

        @SuppressLint("ResourceAsColor")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.customlayout, null);

            Knjiga trazena = nova.get(i);
            if (trazena.selektovan) view.setBackgroundColor(R.color.pressed_color);

            ImageView imageView = (ImageView) view.findViewById(R.id.eNaslovna);
            TextView textView_naziv = (TextView) view.findViewById(R.id.eNaziv);
            TextView textView_autor = (TextView) view.findViewById(R.id.eAutor);

            String fetchAutor = "";
            if (trazena.autori != null) {
                for (int x = 0; x < trazena.autori.size(); x++) {
                    fetchAutor += trazena.autori.get(x).imeiPrezime;
                    if (x != trazena.autori.size() - 1) fetchAutor += ",";
                }


                imageView.setImageBitmap(trazena.slikaBmp);
                textView_naziv.setText(trazena.naziv);
                textView_autor.setText(fetchAutor);
            }

            return view;
        }

    }
}
