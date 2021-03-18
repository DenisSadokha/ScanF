package com.example.scanf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NomList extends AppCompatActivity {
    TextView textView;
    RecyclerView listView;
    Realm realm;
    ArrayAdapter<String> arrayAdapterCode;
    ArrayAdapter<String> arrayAdapterCount;
    ArrayAdapter<String> arrayAdapter;
    static String postDocument;
    String it;
    MyAdapter myAdapter;
    String readStringAdress;
    String readStringLogin;
    String readStringPassword;
    final static String username = "Дмитрий Алексеевич";
    final static String password = "";
    String credentials = username + ":" + password;
    String credentials1;
    Button butRequest;
    DialogProgress dialogProgress;
    int count;
    Button butExit;
    String answer;
    String xmlRequest;
    StringBuilder stringBuilderRequest = new StringBuilder();
    RecyclerView.LayoutManager layoutManager;
    List<DataCenter> list = new ArrayList<>();

    AdapterNew adapterNew;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.listView:
                menu.add(0, 1, 0, "Удалить");
                break;

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                int b = (int) arrayAdapter.getItemId(info.position);
                System.out.println(b);
                arrayAdapter.remove(arrayAdapter.getItem(info.position));
                arrayAdapter.notifyDataSetChanged();
                Nomenkl.arrayListCount.remove(b);
                Nomenkl.arrayListCode.remove(b);
                //  Nomenkl.arrayListCount.remove(arrayAdapter.getItem(info.position));
                // Nomenkl.arrayListCode.remove(arrayAdapter.getItem(info.position));

                System.out.println(Nomenkl.arrayListCode + " " + Nomenkl.arrayListCount);
                if (Nomenkl.arrayListCount.size() == 0) {
                    butRequest.setEnabled(false);
                    butRequest.setBackgroundResource(R.drawable.but_back3);

                } else {
                    butRequest.setEnabled(true);
                    butRequest.setBackgroundResource(R.color.colorList);
                }


                Toast.makeText(getApplicationContext(), "Удалено", Toast.LENGTH_SHORT).show();
                int g = Nomenkl.arrayList.size();
                if (g == 0) {
                    textView.setText("Список пуст");
                }
                break;


        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_nom);

        Realm.init(getApplicationContext());
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        myAdapter = new MyAdapter(realm.where(DataCenter.class).findAll(), this);
        count = myAdapter.getItemCount();
        System.out.println(count);
        butRequest = findViewById(R.id.butRequest);

        if (myAdapter.getItemCount() == 0) {
            butRequest.setEnabled(false);
            butRequest.setBackgroundResource(R.drawable.but_back3);

        } else {
            butRequest.setEnabled(true);
            butRequest.setBackgroundResource(R.color.colorList);
        }

        butExit = findViewById(R.id.butExit1);
        butExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Choose.class);
                startActivity(intent);
                finish();

            }
        });

        textView = findViewById(R.id.test);
        listView = findViewById(R.id.listView);

        if (count > 0) {

            arrayAdapter = new ArrayAdapter<>(this, R.layout.cust_list, Nomenkl.arrayList);
            arrayAdapterCode = new ArrayAdapter<>(this, R.layout.cust_list, Nomenkl.arrayListCode);
            arrayAdapterCount = new ArrayAdapter<>(this, R.layout.cust_list, Nomenkl.arrayListCount);
            registerForContextMenu(listView);
            listView.setAdapter(myAdapter);
            listView.setLayoutManager(new LinearLayoutManager(this));
            Touh touh = new Touh();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touh);
            itemTouchHelper.attachToRecyclerView(listView);
            textView.setText("");


        } else
            textView.setText("Список пуст");


        butRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xmlRequest = DataHelper.createXmlFile(realm, NomList.this);
                Log.d("xmlrequest", "" + xmlRequest);
                connect();


            }
        });


    }


    public void openDialogProgress() {

        dialogProgress = new DialogProgress(this);
        dialogProgress.setCancelable(false);
        Objects.requireNonNull(dialogProgress.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogProgress.show();


    }

    public void closeDialogProgress() {
        dialogProgress.dismiss();
    }


    public void connect() {
        readFile();
        credentials1 = readStringLogin + ":" + readStringPassword;
        openDialogProgress();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);
        String url = "http://" + readStringAdress + "/SkladService/hs/sklad/request/";
        MediaType mediaType = MediaType.parse("charset=utf-8");
        assert mediaType != null;
        final String basic =
                "Basic " + android.util.Base64.encodeToString(credentials1.getBytes(), android.util.Base64.NO_WRAP);
        final Request request = new Request.Builder()
                .url(url)
                .header("Authorization", basic)
                .post(RequestBody.create(mediaType, xmlRequest))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "ПОДКЛЮЧЕНИЕ ОТСУТСТВУЕТ", Toast.LENGTH_LONG).show();
                        deleteSettings();
                        closeDialogProgress();
                    }
                });


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    postDocument = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Отправлено", Toast.LENGTH_SHORT).show();
                            DataHelper.deleteAll(realm);
                            textView.setText("Список пуст");
                            butRequest.setEnabled(false);
                            butRequest.setBackgroundResource(R.drawable.but_back3);
                            deleteSettings();
                            closeDialogProgress();
                            Intent intent = new Intent(NomList.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // something here, if not correct connect
                            Toast.makeText(getApplicationContext(), "Ошибка соединения, повторите попытку", Toast.LENGTH_LONG).show();

                            closeDialogProgress();
                        }
                    });
                }

            }
        });

    }

    private void deleteSettings() {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(DialogWhere.APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(DialogWho.SEND_WHO);
        editor.remove(DialogWhere.SEND_WHERE);
        editor.apply();
    }

    public void openDialog1() {
        DidalogChange d = new DidalogChange(this);
        //  d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);
        d.show();
    }

    private class Touh extends ItemTouchHelper.SimpleCallback {
        Touh() {
            super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        }


        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DataHelper.delete(realm, viewHolder.getItemId());
            // DataHelper.update(realm,viewHolder.getItemId(),"15");
            //create dialog accepter!
            myAdapter.notifyDataSetChanged();
            if (myAdapter.getItemCount() == 0) {
                butRequest.setEnabled(false);
                butRequest.setBackgroundResource(R.drawable.but_back3);
                textView.setText("Список пуст");


            } else {
                butRequest.setEnabled(true);
                butRequest.setBackgroundResource(R.color.colorList);
            }


        }
    }

    public void readFile() {
        FileInputStream fIn = null;
        FileInputStream fIn1 = null;
        FileInputStream fIn2 = null;
        try {
            fIn = openFileInput("fileadress.txt");
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader bufferedReader = new BufferedReader(isr);
            readStringAdress = bufferedReader.readLine();
            fIn.close();
            fIn1 = openFileInput("filelogin.txt");
            InputStreamReader isr1 = new InputStreamReader(fIn1);
            BufferedReader bufferedReader1 = new BufferedReader(isr1);
            readStringLogin = bufferedReader1.readLine();
            fIn1.close();
            fIn2 = openFileInput("filepassword.txt");
            InputStreamReader isr2 = new InputStreamReader(fIn2);
            BufferedReader bufferedReader2 = new BufferedReader(isr2);
            readStringPassword = bufferedReader2.readLine();
            fIn2.close();
            if (readStringPassword == null) {
                readStringPassword = "";
            }
            System.out.println(readStringPassword + " " + readStringLogin + " " + readStringAdress);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
