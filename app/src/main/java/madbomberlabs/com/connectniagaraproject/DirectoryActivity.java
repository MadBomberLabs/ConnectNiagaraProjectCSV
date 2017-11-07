package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectoryActivity extends Activity
{

    ListView lvContacts;
    List<Org> arrlstOrg;

    // FOR NETWORK REQUEST
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        lvContacts = (ListView) findViewById(R.id.lvContacts);
        arrlstOrg = new ArrayList<>();

       getAllOrgs();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        lvContacts.setAdapter(null);
        getAllOrgs();
    }

// SET METHODS FOR BUTTONS
    public void goMenu(View v)
    {
        startActivity(new Intent(this, MenuActivity.class));
    }
    public void goFilter(View v)
    {
        startActivity(new Intent(this, FilterActivity.class));
    }

// RETRIEVE ALL ORGS
    private void getAllOrgs()
    {
        PerformNetworkRequest request = new PerformNetworkRequest(APIHandler.URL_READ_ORGS, null, CODE_GET_REQUEST);
        request.execute();
    }

// REFRESH ORG LIST
    private void refreshOrgList(JSONArray orgs) throws JSONException
    {
        // CLEAR PREVIOUS ORGS
        arrlstOrg.clear();

        // CYCLE ALL ITEMS IN JSON ARRAY RETRIEVED FROM NETWORK REQUEST
        for (int i=0; i < orgs.length(); i++)
        {
            // GETTING EACH ORG OBJECT
            JSONObject obj = orgs.getJSONObject(i);

            // ADDING THE ORG TO THE LIST
            arrlstOrg.add(new Org(
                    obj.getString("id"),
                    obj.getString("org"),
                    obj.getString("firstName"),
                    obj.getString("lastName"),
                    obj.getString("email"),
                    obj.getString("phone"),
                    obj.getString("website"),
                    obj.getString("serviceType"),
                    obj.getString("service"),
                    obj.getString("streetAddress"),
                    obj.getString("city"),
                    obj.getString("state"),
                    obj.getString("zip"),
                    obj.getString("favorite")
            ));
        }
    }

// CLASS FOR PERFORMING NETWORK REQUEST EXTENDING AN AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        // URL WHERE REQUEST IS SENT
        String url;

        //the parameters
        HashMap<String, String> params;

        //the request code to define whether it is a GET or POST
        int requestCode;

        //constructor to initialize values
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //when the task started displaying a progressbar
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }


        //this method will give the response from the request
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    //refreshing the orgList after every operation so we get an updated list
                    refreshOrgList(object.getJSONArray("orgs"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //the network operation will be performed in background
        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
}
