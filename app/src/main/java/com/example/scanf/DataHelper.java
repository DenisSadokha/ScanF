package com.example.scanf;

import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.renderscript.Sampler;

import dataBase.DataWho;
import helper_for_dataBase.HelpreForDataWhere;
import io.realm.Realm;
import io.realm.RealmResults;

public class DataHelper {
    private static StringBuilder stringBuilder = new StringBuilder();

    private static String result;
    static int key;
    private static String xmlReady;
    private static String count;
    private static String balanceFromServer;
    private static boolean check;

    public static int increment(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                // increment index
                Number num = realm.where(DataCenter.class).max("id");
                if (num == null) {
                    key = 1;
                } else {
                    key = num.intValue() + 1;
                }
            }
        });
        return key;
    }

    public static boolean updateCount(Realm realm, final String code) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter dataCenter = realm.where(DataCenter.class).equalTo("code", code).findFirst();
                check = dataCenter != null;
            }
        });
        return check;
    }

    public static String createXmlFile(Realm realm, final Activity activity) {
        System.out.println(key);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter dataCenter;
                Number numCheck = realm.where(DataCenter.class).max("id");
                assert numCheck != null;
                String a = numCheck.toString();
                int g = numCheck.intValue();
                String where, who;
                int b = Integer.parseInt(a);
                SharedPreferences sharedPreferences;
                sharedPreferences = activity.getSharedPreferences(DialogWhere.APP_PREFERENCE, Context.MODE_PRIVATE);
                if (sharedPreferences.contains(DialogWhere.SEND_WHERE)) {
                    where = sharedPreferences.getString(DialogWhere.SEND_WHERE, null);

                } else where = null;

                if (sharedPreferences.contains(DialogWho.SEND_WHO)) {
                    who = sharedPreferences.getString(DialogWho.SEND_WHO, null);
                } else
                    who = null;


                stringBuilder.append("<Who>" + who + "</Who>");
                stringBuilder.append("\n" + "<To>" + where + "</To>");
                stringBuilder.append("\n" + "<Position>" + "\n");
                for (int i = 1; i <= b; i++) {
                    dataCenter = realm.where(DataCenter.class).equalTo("id", i).findFirst();
                    if (dataCenter != null) {

                        stringBuilder.append("<item>" + "<code>" + dataCenter.getCode() + "</code>"
                                + "<count>" + dataCenter.getCount() + "</count>" + "</item>" + "\n");
                    }

                }
                stringBuilder.append("</Position>");

                xmlReady = "<answer>" + "\n" + stringBuilder.toString() + "\n" + "</answer>";
                stringBuilder.setLength(0);
                System.out.println(xmlReady);

            }
        });
        return xmlReady;
    }







    public static void add(Realm realm, final int id, final String name, final String count, final String code, final String maxCount) {
        if (updateCount(realm, code)) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    DataCenter dataCenter = realm.where(DataCenter.class).equalTo("code", code).findFirst();
                    assert dataCenter != null;
                    dataCenter.setCount(count);

                }
            });

        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    DataCenter dataCenter = realm.createObject(DataCenter.class, id);
                    dataCenter.setName(name);
                    dataCenter.setCount(count);
                    dataCenter.setCode(code);
                    dataCenter.setMaxCount(maxCount);
                }


            });
        }

    }
  public static String getCount(Realm realm, final  String code){
         realm.executeTransaction(new Realm.Transaction() {
             @Override
             public void execute(Realm realm) {

                 DataCenter dataCenter = realm.where(DataCenter.class).equalTo("code",code).findFirst();
                 if(dataCenter!=null) {
                     count = dataCenter.getCount();

                 } else
                     count="0";
             }
         });
         return count;
  }

    public static void update(Realm realm, final long id, final String count) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter dataCenter = realm.where(DataCenter.class).equalTo("id", id).findFirst();
                assert dataCenter != null;
                dataCenter.setCount(count);

            }
        });

    }

    public static String getMaxCount(Realm realm, final long id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter dataCenter = realm.where(DataCenter.class).equalTo("id", id).findFirst();
                assert dataCenter != null;
                result = dataCenter.getMaxCount();


            }
        });
        return result;

    }

    public static void delete(Realm realm, final long id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataCenter dataCenter = realm.where(DataCenter.class).equalTo("id", id).findFirst();
                if (dataCenter != null) {
                    dataCenter.deleteFromRealm();
                }


            }
        });

    }

    public static void deleteAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DataCenter> dataCenter = realm.where(DataCenter.class).findAll();
                if (dataCenter != null)
                    dataCenter.deleteAllFromRealm();
            }
        });
    }


}
