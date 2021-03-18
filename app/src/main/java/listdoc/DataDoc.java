package listdoc;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataDoc extends RealmObject {
    private  String docVal;
    @PrimaryKey
    private int id;

    public String getDocVal() {
        return docVal;
    }

    public void setDocVal(String docVal) {
        this.docVal = docVal;
    }
}
