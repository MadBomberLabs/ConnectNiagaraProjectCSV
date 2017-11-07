package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends Activity
{

    ListView lvFavorites;

    DBAdapter niagaraDB;
    Cursor c, c2;

    String itemValue, passedID;

    // Array and ArrayList
    List<String> arrlstOrg = new ArrayList<>();
    String[] arrOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        lvFavorites = (ListView) findViewById(R.id.lvFavorites);
        niagaraDB = new DBAdapter(this);

        showFavorites();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        lvFavorites.setAdapter(null);
        showFavorites();
    }

    public void showFavorites()
    {
        // Clear array so items aren't added to list twice
        arrlstOrg.clear();

        niagaraDB.open();

        //Declare Cursor in memory
        c = niagaraDB.getRecordByFavorite("1");

        /* Get Content, moveToFirst moves cursor to first record,
           if statement is safety for checking that record exists */
        if (c.moveToFirst())
        {
            do
            {
                // Add cursor to OrgLong ArrayList
                arrlstOrg.add(c.getString(1));
            }
            while (c.moveToNext());

            niagaraDB.close();

            // Creates string array of size arrlstOrg
            arrOrg = new String[arrlstOrg.size()];

            // Copy the items from arrlstOrg to Array arrOrg
            // for use in ListView
            arrlstOrg.toArray(arrOrg);

            // Connect the Array arrOrg to ListView
            lvFavorites.setAdapter(new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, arrOrg));

            // onClickListener for ListView items
            lvFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    itemValue = lvFavorites.getItemAtPosition(position).toString();

                    // Get KEY_ID from record by name (itemValue)
                    niagaraDB.open();

                    // Define second cursor used by onclick item
                    c2 = niagaraDB.getIdByName(itemValue);

                    if (c2.moveToFirst())
                    {
                        passedID = c2.getString(0);
                    }

                    niagaraDB.close();

                    // Passes KEY_ID to and opens BusinessCardActivity.class
                    goBusinessCard(passedID);
                }
            });
        }
    }

// Method for opening BusinessCardActivity and passes ordID
    public void goBusinessCard(String orgID)
    {
        Bundle extras = new Bundle();
        extras.putString("pass_id", orgID);
        Intent BusinessCard = new Intent(this, BusinessCardActivity.class);
        BusinessCard.putExtras(extras);
        startActivity(BusinessCard);
    }

    public void closeFavorites(View v)
    {
        this.finish();
    }
}
