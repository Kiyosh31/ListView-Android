package david.compras.de.lista.app.com.applista.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import david.compras.de.lista.app.com.applista.R;
import david.compras.de.lista.app.com.applista.adapters.listaAdapter;

/**
 * Created by david on 12/12/2016.
 */

public class listaFragment extends Fragment {
    RecyclerView recyclerView;
    listaAdapter adapter;

    private static final String TAG ="RecyclerViewFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.lista_fragment, container, false);

        String[] Data = new String[]{"Elemento1", "Elemento2", "Elemento3", "Elemento4", "Elemento5"};

        //mecanismo de vistas para guardar algo
        rootView.setTag(TAG);

        //sistema recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lista);
        //vista
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //adaptador
        adapter = new listaAdapter(Data);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
