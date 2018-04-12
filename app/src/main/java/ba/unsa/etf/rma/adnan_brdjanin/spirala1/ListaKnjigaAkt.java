package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaKnjigaAkt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_knjiga_akt);

        Boolean siriL = false;
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout ldetalji = (FrameLayout)findViewById(R.id.mjestoF3);
        if (ldetalji != null) {
            siriL = true;
            KnjigeFragment fd = (KnjigeFragment) fm.findFragmentById(R.id.mjestoF3);
            if (fd == null) {
                fd = new KnjigeFragment();
                fm.beginTransaction().replace(R.id.mjestoF3, fd).commit();
            } else {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }


}
