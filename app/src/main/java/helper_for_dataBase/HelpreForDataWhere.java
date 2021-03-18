package helper_for_dataBase;

import org.w3c.dom.NodeList;

import dataBase.DataWhere;
import io.realm.Realm;
import io.realm.RealmResults;

public class HelpreForDataWhere {
    private static int id;

    public static void add(Realm realm, final String where, final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWhere dataWhere = realm.createObject(DataWhere.class, id);
                dataWhere.setWhere(where);
            }
        });


    }

    public static void delete(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DataWhere> dataWhere = realm.where(DataWhere.class).findAll();
                dataWhere.deleteAllFromRealm();
            }
        });
    }
public  static void addStock(Realm realm, final String where, final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataWhere dataWhere = realm.createObject(DataWhere.class, id);
                 dataWhere.setWhere(where);
            }
        });
}
    public static int increment(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number number = realm.where(DataWhere.class).max("id");
                if (number == null) {
                    id = 1;
                } else {
                    id = number.intValue() + 1;
                }
            }
        });


        return id;

    }
}
