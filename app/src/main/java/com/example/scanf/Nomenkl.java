package com.example.scanf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.scanf.R.drawable.back_but;
import static com.example.scanf.R.drawable.edittext_off;


public class Nomenkl extends AppCompatActivity {
    static String getCode;
    Button butCode;
    String ss;
    String postBalance;
    String postName;
    Integer neHvataet;
    String maxCount;
    static String postCount;
    String postCode;
    static ArrayList<String> arrayList = new ArrayList<>();
    Button butOk;
    TextView tvPostBalance;
    Button butext;
    static TextView tvCode, tvPost;
    static Integer b;
    static Integer g;
    static ArrayList<String> arrayListCode = new ArrayList<>();
    static ArrayList<String> arrayListCount = new ArrayList<>();
    EditText etCode;
    String username = "Дмитрий Алексеевич";
    String username1;
    String password = "";
    String password1;
    EditText getCount;
    String codeForRequest;
     String readStringAdress;
     String readStringLogin;
     String readStringPassword;
    TextView tvStatus;
    String credentials = username + ":" + password;
   private DialogProgress d;
    String credentials1;
    Realm realm;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            setContentView(R.layout.nomenkl_act);
        } else {
            setContentView(R.layout.nomenkl_act);

        }

        butCode = findViewById(R.id.butCode);
        tvPostBalance = findViewById(R.id.tvPostBalance);
        butOk = findViewById(R.id.butOk);
        etCode = findViewById(R.id.etCode);
        tvCode = findViewById(R.id.tvCode);
        getCount = findViewById(R.id.getCount);
        tvPost = findViewById(R.id.tvPost);
        etCode.setInputType(InputType.TYPE_NULL);
        getCount.setInputType(InputType.TYPE_NULL);
        Realm.init(getApplicationContext());
        realm = Realm.getInstance(DataUtility.getDefaultConfig());






        getCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getCount.setBackgroundResource(R.drawable.edittext);
                } else getCount.setBackgroundResource(R.drawable.edittext_off);

            }
        });
        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etCode.setBackgroundResource(R.drawable.edittext);
                } else etCode.setBackgroundResource(R.drawable.edittext_off);

            }
        });
        etCode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    readFile();
                    credentials1 = readStringLogin + ":" + readStringPassword;
                    if (readStringPassword == null) {
                        readStringPassword = "";
                    }
                    System.out.println(readStringLogin + " " + readStringPassword);
                    getCode = etCode.getText().toString();

                    connect();


                    return true;
                }
                return false;
            }
        });


        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {


                String postLocalBalance = postBalance;
                postCount = getCount.getText().toString();


                if (postBalance.equals("Отсутствует")) {
                    etCode.setText("");
                    tvPost.setText("");
                    getCount.setText("");
                    tvPostBalance.setText("");
                    etCode.requestFocus();
                    tvPost.setText("ТОВАР ОТСУТСТВУЕТ");
                    tvPost.setTextColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Данный товар отсутсвтвует", Toast.LENGTH_LONG).show();

                } else {
                    if (!postLocalBalance.equals("") && postLocalBalance != null) {
                        b = Integer.parseInt(postLocalBalance);
                    }

                    if (!postCount.equals("") && postCount != null) {
                        g = Integer.parseInt(postCount);
                    }

                    if (!postCount.equals("") && !postLocalBalance.equals("") && !tvPost.getText().toString().equals("")) {
                         if(g==0){
                            tvPost.setText("Нэма на складе");
                            tvPost.setTextColor(Color.RED);
                            getCount.setText("");
                            etCode.requestFocus();
                            etCode.setText("");
                        }
                       else if (b >= g) {

                            addNom(postName, postCount, postCode, postLocalBalance);

                            etCode.setText("");
                            tvPost.setText("");
                            getCount.setText("");
                            tvPostBalance.setText("");
                            etCode.requestFocus();
                            tvPost.setText("");
                            tvPost.setTextColor(Color.BLACK);
                            tvPost.setText("ДОБАВЛЕНО");
                            tvPost.setTextColor(Color.GREEN);
                            Toast.makeText(getApplicationContext(), "Добавлено", Toast.LENGTH_LONG).show();
                        }
                        else {
                            neHvataet = Math.abs(b - g);
                            String raznost = neHvataet.toString();
                            tvPost.setText("Не хватает: " + raznost);
                            tvPost.setTextColor(Color.RED);
                            Toast.makeText(getApplicationContext(), "Введенное количество превышает остаток", Toast.LENGTH_LONG).show();
                            getCount.setText("");
                            getCount.requestFocus();

                        }

                    } else if (getCode.equals("")) {
                        etCode.requestFocus();
                        Toast.makeText(getApplicationContext(), "Введите код", Toast.LENGTH_LONG).show();


                    } else if (getCount.getText().toString().equals("")) {
                        getCount.requestFocus();
                        Toast.makeText(getApplicationContext(), "Введите количество!", Toast.LENGTH_LONG).show();
                    }


                }

} catch (Exception e){
    Toast.makeText(getApplicationContext(), "nope", Toast.LENGTH_SHORT).show();
}
            }

        });


        butext = findViewById(R.id.butext);
        butext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Nomenkl.this, Choose.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public String getAuth() {
        readFile();
        return readStringLogin + ":" + readStringPassword;
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

    public void getXml() {
        try {

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            InputSource source = new InputSource(new StringReader(ss));
            Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
            postName = xpath.evaluate("/answer/name", doc);
            postBalance = xpath.evaluate("/answer/balance", doc);
            postCode = xpath.evaluate("/answer/code", doc);
            tvPost.setText(postName);
            tvPostBalance.setText(postBalance);
            getCount.setHint(DataHelper.getCount(realm,postCode));
            tvPost.setTextColor(Color.BLACK);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCount.requestFocus();
                        }
                    });
                }
            }, 200);
            tvPost.setTextColor(Color.BLACK);


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }


    }


    public void connect() {
        openDialog();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        String url = "http://" + readStringAdress + "/SkladService/hs/sklad/returnNom/" + getCode;
        System.out.println(credentials1 + " " + credentials);

        // okHttpClient.connectTimeoutMillis();
        // okHttpClient.readTimeoutMillis();


        final String basic =
                "Basic " + android.util.Base64.encodeToString(credentials1.getBytes(), android.util.Base64.NO_WRAP);
        final Request request = new Request.Builder().url(url)
                .header("Authorization", basic)
                .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPost.setText("ОТСУТСТВУЕТ ПОДКЛЮЧЕНИЕ");
                        tvPost.setTextColor(Color.RED);
                        etCode.setText("");
                        tvPostBalance.setText("");
                        getCount.setText("");
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        etCode.requestFocus();
                                    }
                                });
                            }
                        }, 200);
                        System.out.println("error1");
                        closeDialog();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        ss = response.body().string();
                        System.out.println(ss);
                        Nomenkl.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getXml();
                                closeDialog();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("error2");
                                tvPost.setText("НЕ НАЙДЕНО!" + "(" + getCode + ")");
                                tvPost.setTextColor(Color.RED);
                                etCode.setText("");
                                getCount.setText("");
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                etCode.requestFocus();
                                            }
                                        });
                                    }
                                }, 200);
                                closeDialog();
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void openDialog() {
        d = new DialogProgress(this);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);
        d.show();
    }

    public void closeDialog() {
        d.dismiss();
    }

    public void addNom(String name, String count, String code, String maxCount) {
        int id = DataHelper.increment(realm);
        System.out.println(id);
        DataHelper.add(realm, id, name, count, code, maxCount);


    }


}
