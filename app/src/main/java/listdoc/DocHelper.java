package listdoc;

import android.provider.ContactsContract;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class DocHelper {
    public static int key;
    public static int increment(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number number = realm.where(DataDoc.class).max("id");
                if(number==null){
                    key=1;

                }
                else{
                    key=number.intValue()+1;
                }

            }
        });
        return key;

    }
    public static void deleteAll(Realm realm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DataDoc> dataDoc = realm.where(DataDoc.class).findAll();
                dataDoc.deleteAllFromRealm();
            }
        });

    }
    public static void wtireData(Realm realm, String docVal,final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DataDoc dataDoc = realm.createObject(DataDoc.class,id);
                dataDoc.setDocVal("");


            }
        });


    }
}
