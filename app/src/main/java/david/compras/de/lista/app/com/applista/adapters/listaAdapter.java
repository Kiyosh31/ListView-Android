package david.compras.de.lista.app.com.applista.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import david.compras.de.lista.app.com.applista.R;

/**
 * Created by david on 12/12/2016.
 */

public class listaAdapter extends RecyclerView.Adapter<listaAdapter.ViewHolder> {
    //Context context;
    private String[] myData;

    public listaAdapter(String[] Data){
        myData = Data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(myData[position]);
    }

    @Override
    public int getItemCount() {
        return myData.length;
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
