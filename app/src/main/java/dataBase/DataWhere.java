package dataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataWhere extends RealmObject {
    @PrimaryKey
    private  int id;
    private  String  Where;
    public int getId() {
        return id;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }
}
