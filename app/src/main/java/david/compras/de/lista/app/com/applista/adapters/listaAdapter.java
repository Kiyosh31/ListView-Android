package david.compras.de.lista.app.com.applista.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import david.compras.de.lista.app.com.applista.R;

/**
 * Created by david on 12/12/2016.
 */

public class listaAdapter extends RecyclerView.Adapter<listaAdapter.ViewHolder> {
    Context myContext;
    CursorAdapter myCursorAdapter;

    public listaAdapter(Context context, Cursor cursor){
        myContext = context;

        //cursor
        myCursorAdapter = new CursorAdapter(myContext, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View retView = inflater.inflate(R.layout.item, parent, false);

                return retView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView textView = (TextView) view.findViewById(R.id.title);
                TextView listaNombre = (TextView) view.findViewById(R.id.listaNombre);

                textView.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                listaNombre.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.textView.setText(myData[position]);
        //cuando haya terminado se regresa a la ultima posicion
        myCursorAdapter.getCursor().moveToPosition(position);
        myCursorAdapter.bindView(holder.itemView, myContext, myCursorAdapter.getCursor());
    }

    @Override
    public int getItemCount() {
        return myCursorAdapter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView, listaNombre;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.title);
            listaNombre = (TextView) itemView.findViewById(R.id.listaNombre);
        }
    }
}
