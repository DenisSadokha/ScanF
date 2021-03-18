package com.example.scanf;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.sql.RowSetReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import helper_for_dataBase.HelperForDataWho;
import helper_for_dataBase.HelpreForDataWhere;
import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Settings extends AppCompatActivity {
    EditText etLogin, etPassword, etAdress;
    Button butSafe, butBack, bUpdate;
    String login;
    String password;
    String adress;
    DialogProgress d;
    boolean check;

    String xmlData;
    String tagWho = "empl";
    String tagWhere = "stock";
    ArrayList<String> who, stock = new ArrayList<>();
    String urlWho = "http://192.168.0.227/SkladService/hs/sklad/returnEmpl/";
    String urlStock = "http://192.168.0.227/SkladService/hs/sklad/returnStl/";
    private String readStringAdress;
    private String readStringLogin;
    private String readStringPassword;
    Realm realm;
 private  SharedPreferences sharedPreferences;
 SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_act);
        sharedPreferences= getSharedPreferences(DialogWhere.APP_PREFERENCE, Context.MODE_PRIVATE);
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etAdress = findViewById(R.id.etAdres);
        butSafe = findViewById(R.id.butOk);
        butBack = findViewById(R.id.butExit);
        bUpdate = findViewById(R.id.bUpdate);
        Realm.init(this);
        realm = Realm.getInstance(DataUtility.getDefaultConfig());
        FileInputStream log = null;
        FileInputStream pass;
        FileInputStream ip;
        // etAdress.setInputType(InputType.TYPE_NULL);
        //    etLogin.setInputType(InputType.TYPE_NULL);
        // etPassword.setInputType(InputType.TYPE_NULL);


        try {
            log = openFileInput("filelogin.txt");
            InputStreamReader r = new InputStreamReader(log);
            BufferedReader bufferedReader = new BufferedReader(r);
            String logs = bufferedReader.readLine();
            log.close();
            pass = openFileInput("filepassword.txt");
            InputStreamReader in = new InputStreamReader(pass);
            BufferedReader reader = new BufferedReader(in);
            String p = reader.readLine();
            pass.close();
            ip = openFileInput("fileadress.txt");
            InputStreamReader inw = new InputStreamReader(ip);
            BufferedReader f = new BufferedReader(inw);
            String i = f.readLine();
            ip.close();
            urlWho = "http://" + i + "/SkladService/hs/sklad/returnEmpl/";
            urlStock = "http://" + i + "/SkladService/hs/sklad/returnStl/";
            etLogin.setText(logs);
            etPassword.setText(p);
            etAdress.setText(i);
            etAdress.setSelection(etAdress.getText().length());
            etLogin.setSelection(etLogin.getText().length());
            etPassword.setSelection(etPassword.getText().length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        butSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = etLogin.getText().toString();
                password = etPassword.getText().toString();
                System.out.println(password);
                adress = etAdress.getText().toString();


                if (adress.equals("") || login.equals("")) {
                    Toast.makeText(getApplicationContext(), "Введите все данные", Toast.LENGTH_LONG).show();
                    etAdress.setText("");
                    etLogin.setText("");
                    etPassword.setText("");
                    etAdress.requestFocus();

                } else {
                    try {
                        deleteFile("filelogin.txt");
                        deleteFile("filepassword.txt");
                        deleteFile("fileadress.txt");
                        FileOutputStream fOut = openFileOutput("filelogin.txt", MODE_PRIVATE);
                        OutputStreamWriter osw = new OutputStreamWriter(fOut);
                        osw.write(login);
                        FileOutputStream foutpass = openFileOutput("filepassword.txt", MODE_PRIVATE);
                        OutputStreamWriter outopass = new OutputStreamWriter(foutpass);
                        outopass.write(password);
                        osw.flush();
                        osw.close();
                        FileOutputStream foutadress = openFileOutput("fileadress.txt", MODE_PRIVATE);
                        OutputStreamWriter outadress = new OutputStreamWriter(foutadress);
                        outadress.write(adress);
                        outadress.flush();
                        outadress.close();
                        outopass.flush();
                        outopass.close();
                        Toast.makeText(getApplicationContext(), "СОХРАНЕНО", Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogProgress();
                connect(urlWho, 0, tagWho);

            }
        });


        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void connect(String url, final int checkInt, final String tag) {

        readFile();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newBuilder().readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS);
        String auth = readStringLogin + ":" + readStringPassword;
        Log.d("NEwTagLog", "pass " + readStringPassword + "login " + readStringLogin + "adress " + readStringAdress);
        Log.d("xmlrequest", "" + auth);
        final String basic =
                "Basic " + android.util.Base64.encodeToString(auth.getBytes(), android.util.Base64.NO_WRAP);
        final Request request = new Request.Builder().url(url)
                .header("Authorization", basic)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // uncomment under code after ready app
                        Toast.makeText(getApplicationContext(), "Ошибка при соединении", Toast.LENGTH_LONG).show();
                        closeDialogProgress();
                        // delete that  under after correct work service
//                        if (checkInt == 0) {
//                            connect(urlStock, 1, tagWhere);
//                            Toast.makeText(getApplicationContext(), "tag 0 ", Toast.LENGTH_LONG).show();
//
//                        }
//                        if (checkInt == 1) {
//                            Toast.makeText(getApplicationContext(), "Ошибка при соединении", Toast.LENGTH_LONG).show();
//                            Log.d("NEwTagLog", "Fail" + check);
//                            closeDialogProgress();
//                        }
                    }
                });


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    xmlData = response.body().string();
                    System.out.println(xmlData);
                    check = true;
                    Settings.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parseXml(checkInt, tag);
                            if (checkInt == 0) {
                                connect(urlStock, 1, tagWhere);

                            }
                            if (checkInt == 1) {
                                Toast.makeText(getApplicationContext(), "База обновлена", Toast.LENGTH_LONG).show();
                                Log.d("NEwTagLog", "Fail" + check);
                                closeDialogProgress();
                            }

                        }
                    });
                } else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Ошибка при соединении", Toast.LENGTH_LONG).show();
                            closeDialogProgress();
                        }
                    });


            }
        });


    }

    private void parseXml(int check, String xmlTag) {
        try {
            String tag = "/answer/" + xmlTag;
            System.out.println(tag);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            InputSource inputSource = new InputSource(new StringReader(xmlData));

            Document doc = (Document) xpath.evaluate("/", inputSource, XPathConstants.NODE);
            NodeList nodes = (NodeList) xpath.evaluate(tag, doc, XPathConstants.NODESET);
            if (check == 1) {
                HelpreForDataWhere.delete(realm);
                editor = sharedPreferences.edit();
                editor.remove(DialogWhere.SEND_WHERE);
                editor.apply();
            } else if (check == 0) {
                HelperForDataWho.delete(realm);
                editor=sharedPreferences.edit();
                editor.remove(DialogWho.SEND_WHO);
                editor.apply();
            }
            for (int i = 0; i < nodes.getLength(); i++) {
                Log.d("TagArrayLsit", "" + nodes.item(i).getTextContent());
                if (check == 1) {
                    int id = HelpreForDataWhere.increment(realm);

                    HelpreForDataWhere.addStock(realm, nodes.item(i).getTextContent(), id);
                } else if (check == 0) {
                    int id = HelperForDataWho.increment(realm);
                    HelperForDataWho.addWho(realm, nodes.item(i).getTextContent(), id);
                }
            }


        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }

    public void openDialogProgress() {
        d = new DialogProgress(this);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setCancelable(false);
        d.show();


    }

    public void closeDialogProgress() {
        d.dismiss();


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
