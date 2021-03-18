package com.example.scanf;

import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpConn extends AsyncTask<String, Void, String> {
    BufferedReader reader = null;

    static String content = null;
    StringBuilder stringBuilder = null;
    String line;
    HttpURLConnection httpURLConnection;


    @Override
    protected String doInBackground(String... path) {
   /*     OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://192.168.1.40/SkladService/hs/sklad/returnNom/"+Nomenkl.getCode;
        final String basic =
                "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        final Request request = new Request.Builder().url(url)
                .header("Authorization",basic)
                .build();
okHttpClient.newCall(request).enqueue(new Callback() {
    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
if(response.isSuccessful()){
  ss = response.body().string();
}
    }
});*/




      /*  try {


          URL url = new URL("http://localhost/SkladService/hs/sklad/returnNom/"+Nomenkl.getCode);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);

         //   httpURLConnection.setRequestProperty("Дмитрий Адексеевич", "");

            System.out.println(url);
            httpURLConnection.connect();
            System.out.println(line);
            if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                line = reader.readLine();
                System.out.println(line);
            }
            else System.out.println("no");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } */
        return line;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Nomenkl.tvPost.setText(line);

    }
}