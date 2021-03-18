package listdoc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.scanf.DataUtility;
import com.example.scanf.R;

import io.realm.Realm;

public class DocMain extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    Button butClear, butBack;
    Realm realm;
    DocAdapter docAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doc_act);
        Realm.init(this);
        realm=Realm.getInstance(DataUtility.getDefaultConfig());
        recyclerView=findViewById(R.id.rvDoc);
        butBack=findViewById(R.id.butBack);
        butClear=findViewById(R.id.butClear);
        butClear.setOnClickListener(this);
        butBack.setOnClickListener(this);
        docAdapter = new DocAdapter(realm.where(DataDoc.class).findAll(),this);
        recyclerView.setAdapter(docAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butBack:
                break;
            case R.id.butClear:
                break;
        }

    }
}
