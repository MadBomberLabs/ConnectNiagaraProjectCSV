package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MenuActivity extends Activity
{
    // DEFINE BUTTONS
    Button btn_Dir, btn_Search, btn_Fav;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // INITIALIZE BUTTONS
        btn_Dir = (Button) findViewById(R.id.btn_directory);
        btn_Search = (Button) findViewById(R.id.btn_search);
        btn_Fav = (Button) findViewById(R.id.btn_favorites);

        setContentView(R.layout.activity_menu);
    }

// SET METHODS FOR XML BUTTONS
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
}