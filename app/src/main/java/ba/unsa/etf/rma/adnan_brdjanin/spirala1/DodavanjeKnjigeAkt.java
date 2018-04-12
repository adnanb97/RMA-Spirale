package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DodavanjeKnjigeAkt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodavanje_knjige_akt);

        Boolean siriL = false;
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mjestoF2);

        if(frameLayout != null){
            siriL = true;
            DodavanjeKnjigeFragment fragment = (DodavanjeKnjigeFragment)fm.findFragmentById(R.id.mjestoF2);
            if(fragment == null) {
                fragment = new DodavanjeKnjigeFragment();
                fm.beginTransaction().replace(R.id.mjestoF2, fragment).commit();
            }else {
                fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }

    }
}
