package ba.unsa.etf.rma.adnan_brdjanin.spirala1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DohvatiNajnovije extends AsyncTask<String, Integer, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        String query = null;
        try {
            query = URLEncoder.encode(strings[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url1 = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + query + "&maxResults=5&orderBy=newest";
        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject knjiga = items.getJSONObject(i);
                String nazivDjela = knjiga.getJSONObject("volumeInfo").optString("title");
                String idDjela = knjiga.getString("id");
                JSONArray jsonPisac = knjiga.getJSONObject("volumeInfo").optJSONArray("authors");
                ArrayList<Autor> autori = new ArrayList<Autor>();
                if (jsonPisac != null)
                for (int j = 0; j < jsonPisac.length(); j++) {
                    String imePisca = jsonPisac.getString(j);
                    Autor tempAutor = new Autor(imePisca, idDjela);
                    autori.add(tempAutor);
                }
                String opis = knjiga.getJSONObject("volumeInfo").optString("description");
                String datumObjave = knjiga.getJSONObject("volumeInfo").optString("publishedDate");
                JSONObject imageLinks = knjiga.getJSONObject("volumeInfo").optJSONObject("imageLinks");
                String slika = null;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public interface IDohvatiNajnovijeDone {
        public void onNajnovijeDone(ArrayList<Knjiga> x);
    }

    private IDohvatiNajnovijeDone pozivatelj;
    public DohvatiNajnovije(IDohvatiNajnovijeDone p) { pozivatelj = p; }
    private ArrayList<Knjiga> uzete = new ArrayList<>();

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pozivatelj.onNajnovijeDone(uzete);
    }
}
