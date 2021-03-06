package david.compras.de.lista.app.com.applista.fragments;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import david.compras.de.lista.app.com.applista.R;
import david.compras.de.lista.app.com.applista.adapters.listaAdapter;
import david.compras.de.lista.app.com.applista.helpers.dataBaseHelper;
import david.compras.de.lista.app.com.applista.recyclerItemClickListener;

/**
 * Created by david on 12/12/2016.
 */

public class listaFragment extends Fragment {
    RecyclerView recyclerView;
    listaAdapter adapter;
    dataBaseHelper myDBHelper;

    public interface callBacks{

        public void onItemSelected(String nombreLista, String lista);
    }

    private static final String TAG ="RecyclerViewFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.lista_fragment, container, false);

        //String[] Data = new String[]{"Elemento1", "Elemento2", "Elemento3", "Elemento4", "Elemento5"};

        //mecanismo de vistas para guardar algo
        rootView.setTag(TAG);

        //sistema recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lista);
        //vista
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        myDBHelper = new dataBaseHelper(getActivity().getApplicationContext());
        try
        {
            myDBHelper.createDataBase();
        }
        catch (IOException e)
        {
            throw new Error("No se puede crear DB");
        }

        //cursor
        try
        {
            myDBHelper.openDataBase();
            Cursor myCursor = myDBHelper.fetchAllList();

            if(myCursor != null)
            {
                adapter = new listaAdapter(getActivity().getApplicationContext(), myCursor);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(new recyclerItemClickListener(getActivity().getApplicationContext(),
                        new onItemClickListener()));
            }

        }
        catch (SQLException e)
        {

        }

        return rootView;
    }

    private class onItemClickListener extends recyclerItemClickListener.simpleOnItemClickListener{
        @Override
        public void onItemClick(View childView, int position){
            TextView textView = (TextView) childView.findViewById(R.id.title);
            TextView listaNombre = (TextView) childView.findViewById(R.id.listaNombre);

            ((callBacks) getActivity()).onItemSelected(textView.getText().toString(), listaNombre.getText().toString());
            //Toast.makeText(getActivity(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongPress(View childView, int position){

        }
    }
}
