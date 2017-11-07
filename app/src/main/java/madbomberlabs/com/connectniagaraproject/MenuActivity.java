package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MenuActivity extends Activity
{

    Button btn_Dir, btn_Search, btn_Fav;
    DBAdapter niagaraDB;

    String insert_key, key;

    SharedPreferences mPrefs;
    SharedPreferences.Editor editor;

    // For Reading CSV DB file
    InputStream inputStream;
    List<String[]> dataList = new ArrayList<>();

    // For loop variables when inserting records
    String id, org, firstname, lastname, email, phone, website, servicetype, service, streetaddress,
            city, state, zip, favorite;

    String myURL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialize
        btn_Dir = (Button) findViewById(R.id.btn_directory);
        btn_Search = (Button) findViewById(R.id.btn_search);
        btn_Fav = (Button) findViewById(R.id.btn_favorites);

        // Declare sharedPrefs
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mPrefs.edit();

        // Declare DB
        niagaraDB = new DBAdapter(this);

        // Declare shared prefs keys
        key = mPrefs.getString(insert_key, null);

        // Declare URL variable
        myURL = "http://www.madbomberlabs.com/CSV/androidtest.csv";

    // Check if file exists at URL
        new Thread(){

            @Override
            public void run()
            {
                super.run();

                try {
                    URL url = new URL(myURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    Log.i(TAG, "con.getResponseCode() IS : " + con.getResponseCode());
                    if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
                    {
                        Log.i (TAG, "Sucess");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.i (TAG, "fail");
                }
            }

        }.start();

    // If key isn't blank, add the contacts
        if (key == null || key.equals("false") )
        {
            niagaraDB.open();

            // Insert records here
            addContacts();

            niagaraDB.close();

            // Write SharedPrefs Data
            editor.putString(insert_key, "true");
            editor.apply();
        }


        setContentView(R.layout.activity_menu);

    }

// Set Methods for buttons
    public void goDirectory(View v)
    {
        startActivity(new Intent(this, DirectoryActivity.class));
    }

    public void goSearch(View v)
    {
        startActivity(new Intent(this, FilterActivity.class));
    }

    public void goFavorites(View v)
    {
        startActivity(new Intent(this, FavoritesActivity.class));
    }

    /**
     * Need to check if file can be updated from internet, then update records when file is updated
     * in a method here. Figure out method for updating DB version.
     */

// Read data from csv file
    private void readData()
    {
        inputStream = getResources().openRawResource(R.raw.contacts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String csvLine;

        try
        {
            // Separate csv file by comma, insert into array
            while ((csvLine = reader.readLine()) != null)
            {
                String[] row = csvLine.split(",");
                dataList.add(row);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

// Write data to msqlite db
    private void writeData()
    {
        int row_count = dataList.size();

        for (int i=0; i < row_count; i++)
        {
            id = dataList.get(i)[0];
            org = dataList.get(i)[1];
            firstname = dataList.get(i)[2];
            lastname = dataList.get(i)[3];
            email = dataList.get(i)[4];
            phone = dataList.get(i)[5];
            website = dataList.get(i)[6];
            servicetype = dataList.get(i)[7];
            service = dataList.get(i)[8];
            streetaddress = dataList.get(i)[9];
            city = dataList.get(i)[10];
            state = dataList.get(i)[11];
            zip = dataList.get(i)[12];
            favorite = dataList.get(i)[13];

            niagaraDB.insertContact(id, org, firstname, lastname, email, phone, website,
                    servicetype, service, streetaddress, city, state, zip, favorite);
        }
    }

// Method for inserting contacts
    public void addContacts()
    {
        readData();
        writeData();
    }

}
