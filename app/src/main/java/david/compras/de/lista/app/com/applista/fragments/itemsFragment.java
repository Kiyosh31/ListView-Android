package david.compras.de.lista.app.com.applista.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    dataBaseHelper myDBHelper;
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
        //dialogo para agregar un nuevo item
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Click 1", Toast.LENGTH_SHORT).show();

                //ventana para agregar el item
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                //titulo
                builder.setTitle("Agregar un item");
                //nuevo EditText donde se ingresara el nuevo item
                final EditText input = new EditText(getActivity());
                //se especifica el tipo de dato
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                //se agrega
                builder.setView(input);

                //boton positivo
                builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseHelper myDBHelper = new dataBaseHelper(getActivity().getApplicationContext());
                        SQLiteDatabase db = myDBHelper.getWritableDatabase();
                        ContentValues valores = new ContentValues();
                        valores.put("item", input.getText().toString());

                        db.insert(lista, null, valores);
                        db.close();

                        //actualizar interfaz grafica
                        try
                        {
                            myDBHelper.openDataBase();
                            Cursor cursor = myDBHelper.fetchItemsList(lista);
                            from = new String[]{"_id", "item"};
                            to = new int[]{R.id.id, R.id.title_item};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),
                                            R.layout.item_list, cursor, from, to);
                            listView.setAdapter(cursorAdapter);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                //boton negativo
                builder.setNegativeButton("Canelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //mostramos
                builder.show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "Click 2", Toast.LENGTH_SHORT).show();

                final TextView ID = (TextView) view.findViewById(R.id.id);

                //ventana emergente
                AlertDialog.Builder abd = new AlertDialog.Builder(getActivity());
                //titulo
                abd.setTitle("Borrar");
                //mensaje
                abd.setMessage("¿Deseas eliminar el item?");

                //boton negativo
                abd.setNegativeButton("Cancelar", null);

                //boton positivo
                abd.setPositiveButton("Aceptar", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getActivity(), "si si si", Toast.LENGTH_SHORT).show();

                        myDBHelper = new dataBaseHelper(getActivity().getApplicationContext());
                        SQLiteDatabase db = myDBHelper.getWritableDatabase();

                        //sentencia SQL para eliminar por ID
                        String where = "_id = ''" + ID.getText().toString() + "''";
                        //Eliminacion
                        db.delete(lista, where, null);
                        //cerramos DB
                        db.close();

                        //actualizar interfaz grafica
                        try
                        {
                            myDBHelper.openDataBase();
                            Cursor cursor = myDBHelper.fetchItemsList(lista);
                            from = new String[]{"_id", "item"};
                            to = new int[]{R.id.id, R.id.title_item};
                            cursorAdapter =
                                    new SimpleCursorAdapter(getActivity().getApplicationContext(),
                                            R.layout.item_list, cursor, from, to);
                            listView.setAdapter(cursorAdapter);
                        }
                        catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                abd.show();
            }
        });

        return rootView;
    }
}
