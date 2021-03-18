package helper_for_dataBase;

import dataBase.DataWhere;
import dataBase.DataWho;
import io.realm.Realm;
import io.realm.RealmResults;

public class HelperForDataWho {
    private static int id;

    public static void add(Realm realm, final String who, final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWho dataWho = realm.createObject(DataWho.class, id);
                dataWho.setWho(who);
            }
        });


    }
    public static int increment(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number number = realm.where(DataWho.class).max("id");
                if (number == null) {
                    id = 1;
                } else {
                    id = number.intValue() + 1;
                }
            }
        });


        return id;

    }
    public  static void addWho(Realm realm, final String who, final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWho  dataWho = realm.createObject(DataWho.class,id);
                dataWho.setWho(who);
            }
        });
    }
    public static void delete(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DataWho> dataWho = realm.where(DataWho.class).findAll();
                dataWho.deleteAllFromRealm();
            }
        });
    }

}
