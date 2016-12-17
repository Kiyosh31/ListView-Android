package david.compras.de.lista.app.com.applista.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import david.compras.de.lista.app.com.applista.R;
import david.compras.de.lista.app.com.applista.fragments.itemsFragment;

/**
 * Created by david on 16/12/2016.
 */

public class itemList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemlist);

        Bundle intent = getIntent().getExtras();

        if(savedInstanceState == null)
        {
            itemsFragment fragment = new itemsFragment();
            fragment.setArguments(intent);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_item, fragment).commit();
        }
    }
}
