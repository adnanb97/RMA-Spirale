package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class FragmentPreporuci extends Fragment {

    public FragmentPreporuci() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment_preporuci, container, false);
    }
    String imeKorisnika, imeKnjige, imeAutora;
    List<Pair<String, String>> contacts;

    private void showContacts() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
        } else {
            contacts = getContactNames();
            List<String> emailovi = new ArrayList<>();
            for (int i = 0; i < contacts.size(); i++)
                emailovi.add(contacts.get(i).first);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, emailovi);
            spinner_sKontakti.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContacts();
            } else {
                Toast.makeText(getActivity(), R.string.Odobri_permisiju, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private List<Pair<String, String>> getContactNames() {


        List<Pair<String, String>> emailList = new ArrayList<>();

        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {


                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                            emailList.add(new Pair(email, name));

                        }
                        emailCur.close();
                    }
                    pCur.close();
                }
            }
        }

        return emailList;
    }
    Button button_posalji;
    TextView textView_info;
    Spinner spinner_sKontakti;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button_posalji = (Button) getView().findViewById(R.id.dPosalji);
        textView_info = (TextView) getView().findViewById(R.id.dInfoOKnjizi);
        spinner_sKontakti = (Spinner) getView().findViewById(R.id.sKontakti);


        showContacts();

        imeAutora = imeKnjige = imeKorisnika = "";

        Intent i = getActivity().getIntent();
        if (i != null) {
            imeAutora = i.getStringExtra("imeAutora");
            imeKnjige = i.getStringExtra("imeKnjige");
        }

        textView_info.setText("Naziv djela: " + imeKnjige + ", ime autora: " + imeAutora);
        button_posalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selektovani = spinner_sKontakti.getSelectedItem().toString();
                for (int i = 0; i < contacts.size(); i++)
                    if (contacts.get(i).first.equals(selektovani))
                        imeKorisnika = contacts.get(i).second;

                String[] TO = {selektovani};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Preporuka za knjigu");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Zdravo " + imeKorisnika + ",\nProcitaj knjigu " + imeKnjige + " od " + imeAutora + "!");
                //emailIntent.setType("message/rfc822");
                emailIntent.setType("text/plain");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    getActivity().finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), R.string.Nijedan_nije_instaliran, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
