package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class PreporuciAkt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preporuci_akt);

        Boolean siriL = false;
        FragmentManager fm = getSupportFragmentManager();
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mjestoF2);

        if(frameLayout != null){
            siriL = true;
            FragmentPreporuci fragment = (FragmentPreporuci) fm.findFragmentById(R.id.mjestoF2);
            if(fragment == null) {
                fragment = new FragmentPreporuci();
                fm.beginTransaction().replace(R.id.mjestoF2, fragment).commit();
            }else {
                fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }
}
