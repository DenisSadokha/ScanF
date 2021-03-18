package dataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataWho extends RealmObject {
@PrimaryKey
    private int id;
private String who;

    public int getId() {
        return id;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
