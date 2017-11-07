package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jeff on 10/19/17.
 */

public class SplashActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Run Method to check existence and create database here

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
