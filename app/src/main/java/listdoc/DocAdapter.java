package listdoc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scanf.DataUtility;
import com.example.scanf.R;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class DocAdapter extends RealmRecyclerViewAdapter<DataDoc, DocAdapter.DocHolder> {
     Context context;
     Realm realm;


    public DocAdapter(@Nullable OrderedRealmCollection<DataDoc> data, Context context) {
        super(data, true);
        this.context=context;
    }

    @NonNull
    @Override
    public DocHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_doc, viewGroup,false);
        Realm.init(context);
        realm=Realm.getInstance(DataUtility.getDefaultConfig());

        return new DocAdapter.DocHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DocHolder docHolder, int i) {
        final DataDoc dataDoc = getItem(i);
        assert dataDoc!=null;
        docHolder.tvDocValue.setText(dataDoc.getDocVal());
    }

     class DocHolder  extends RecyclerView.ViewHolder {
        TextView tvDocValue;
        DocHolder(@NonNull View itemView) {
            super(itemView);
            tvDocValue=(TextView) itemView.findViewById(R.id.tvDoc);

        }
    }
}
