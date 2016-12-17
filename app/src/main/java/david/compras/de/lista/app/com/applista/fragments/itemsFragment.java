package david.compras.de.lista.app.com.applista.fragments;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import david.compras.de.lista.app.com.applista.R;
import david.compras.de.lista.app.com.applista.helpers.dataBaseHelper;

/**
 * Created by david on 16/12/2016.
 */

public class itemsFragment extends Fragment {
    ListView listView;
    TextView addItem;
    String lista;
    SimpleCursorAdapter cursorAdapter;
    Cursor myCursor;
    String[] from;
    int[] to;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.items_fragment, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_item);
        addItem = (TextView) rootView.findViewById(R.id.addItem);

        Bundle bundle = getArguments();

        if(bundle != null)
        {
            lista = (String) bundle.get("lista");
            dataBaseHelper myDBHelper = new dataBaseHelper(getActivity());

            try
            {
                myDBHelper.createDataBase();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                myDBHelper.openDataBase();

                myCursor = myDBHelper.fetchItemsList(lista);

                if(myCursor != null)
                {
                    from = new String[]{"id", "item"};
                    to = new int[]{R.id.title_item};

                    cursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.item_list,
                            myCursor, from, to);
                    listView.setAdapter(cursorAdapter);
                }

                myDBHelper.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }

        return rootView;
    }
}
