package david.compras.de.lista.app.com.applista.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import david.compras.de.lista.app.com.applista.R;

/**
 * Created by david on 16/12/2016.
 */

public class itemsFragment extends Fragment {
    ListView listView;
    TextView addItem;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.items_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_item);
        addItem = (TextView) rootView.findViewById(R.id.addItem);

        Bundle bundle = getArguments();

        if(bundle != null)
        {
            listView.setAdapter();
        }

        return rootView;
    }
}
