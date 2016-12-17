package david.compras.de.lista.app.com.applista.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import david.compras.de.lista.app.com.applista.R;
import david.compras.de.lista.app.com.applista.fragments.itemsFragment;
import david.compras.de.lista.app.com.applista.fragments.listaFragment;

public class MainActivity extends AppCompatActivity implements listaFragment.callBacks{
    public boolean mTwoPane;
    private static final String ELEMENTS_TAG = "ELEMENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //si existe en la interfaz un list_item
        if(findViewById(R.id.list_item) != null)
        {
            mTwoPane = true;

            //si el savedInstanceState esta vacio
            if(savedInstanceState == null)
            {
                // getSupportFragmentManager -> agrega fragments cuando exista el espacio para hacerlo
                // replace -> se reemplaza por itemsFragment
                // commit -> actualiza
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_item, new itemsFragment(), ELEMENTS_TAG).commit();
            }
            else
            {
                mTwoPane = false;
            }
        }
    }

    public boolean getMTwoPane(){
        return this.mTwoPane;
    }

    @Override
    public void onItemSelected(String nombreLista, String lista){

        if(mTwoPane)
        {
            Bundle bundle = new Bundle();
            bundle.putString("nombreLista", nombreLista);
            bundle.putString("lista ", lista);
            itemsFragment itemsFragment = new itemsFragment();
            itemsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.list_item, itemsFragment).commit();
        }
        else
        {
            Intent intent = new Intent(this, itemList.class);
            intent.putExtra("nombreLista", nombreLista);
            intent.putExtra("lista", lista);
            startActivity(intent);
        }
    }
}
