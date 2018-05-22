package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class KnjigePoznanika extends IntentService {
    int STATUS_START = 0, STATUS_FINISH = 1, STATUS_ERROR = 2;

    public KnjigePoznanika() {
        super(null);
    }
    public KnjigePoznanika(String name) {
        super(name);
    }
    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startBackgroundTask(intent, startId);
        return Service.START_STICKY;
    }*/
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String idKorisnika = intent.getStringExtra("idKorisnika");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        receiver.send(STATUS_START, Bundle.EMPTY);

        String url1 = "https://www.googleapis.com/books/v1/users/" + idKorisnika + "/bookshelves";
        try {

            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject bookshelf = items.getJSONObject(i);
                String idShelfa = bookshelf.getString("id");
                String url2 = "https://www.googleapis.com/books/v1/users/" + idKorisnika +
                        "/bookshelves/" + idShelfa + "/volumes";
                try {
                    URL Url = new URL(url2);
                    HttpURLConnection urlConnection1 = (HttpURLConnection)Url.openConnection();
                    InputStream in2 = new BufferedInputStream(urlConnection1.getInputStream());
                    String rezultat2 = convertStreamToString(in2);
                    JSONObject jo2 = new JSONObject(rezultat2);
                    JSONArray items2 = jo2.getJSONArray("items");
                    for (int j = 0; j < items2.length(); j++) {
                        JSONObject knjiga = items2.getJSONObject(j);
                        String nazivDjela = knjiga.getJSONObject("volumeInfo").optString("title");
                        String idDjela = knjiga.optString("id");
                        JSONArray jsonPisac = knjiga.getJSONObject("volumeInfo").optJSONArray("authors");
                        ArrayList<Autor> autori = new ArrayList<Autor>();
                        if (jsonPisac != null)
                        for (int jj = 0; jj < jsonPisac.length(); jj++) {
                            String imePisca = jsonPisac.getString(jj);
                            Autor tempAutor = new Autor(imePisca, idDjela);
                            autori.add(tempAutor);
                        }
                        String opis = knjiga.getJSONObject("volumeInfo").optString("description");
                        String datumObjave = knjiga.getJSONObject("volumeInfo").optString("publishedDate");
                        String slika = null;
                        JSONObject imageLinks = knjiga.getJSONObject("volumeInfo").optJSONObject("imageLinks");
                        if (imageLinks != null)
                            slika = imageLinks.optString("thumbnail");

                        String stranice = knjiga.getJSONObject("volumeInfo").optString("pageCount");
                        int brojStrinica = 0;
                        if (!stranice.equals("")) brojStrinica = Integer.parseInt(stranice);

                        if (nazivDjela == null) nazivDjela = "";
                        if (idDjela == null) idDjela = "";
                        if (opis == null) opis = "";
                        if (datumObjave == null) datumObjave = "";
                        if (slika == null) slika = "http://books.google.com/books/content?id=mWggzqOEzAsC&printsec=frontcover&img=1&zoom=1&source=gbs_ap";

                        URL urlSlika = new URL(slika);
                        Knjiga uzeta = new Knjiga(idDjela, nazivDjela, autori, opis, datumObjave, urlSlika, brojStrinica);
                        uzete.add(uzeta);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listaKnjiga", uzete);
                    receiver.send(STATUS_FINISH, bundle);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    receiver.send(STATUS_ERROR, Bundle.EMPTY);
                } catch (IOException e) {
                    e.printStackTrace();
                    receiver.send(STATUS_ERROR, Bundle.EMPTY);
                } catch (JSONException e) {
                    e.printStackTrace();
                    receiver.send(STATUS_ERROR, Bundle.EMPTY);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            receiver.send(STATUS_ERROR, Bundle.EMPTY);
        } catch (IOException e) {
            e.printStackTrace();
            receiver.send(STATUS_ERROR, Bundle.EMPTY);
        } catch (JSONException e) {
            e.printStackTrace();
            receiver.send(STATUS_ERROR, Bundle.EMPTY);
        }



    }
    private ArrayList<Knjiga> uzete = new ArrayList<>();
    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
}
