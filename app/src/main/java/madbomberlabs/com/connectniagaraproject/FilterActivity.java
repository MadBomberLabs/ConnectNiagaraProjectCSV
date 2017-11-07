package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FilterActivity extends Activity
{
    RadioGroup rgServiceType;
    RadioButton rbCommunityService, rbCharity, rbAdvisement, rbEducation, rbFinance;
    String passed_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        rgServiceType = (RadioGroup) findViewById(R.id.rgServiceType);

        rbCommunityService = (RadioButton) findViewById(R.id.rbCommunityService);
        rbCharity = (RadioButton) findViewById(R.id.rbCharity);
        rbAdvisement = (RadioButton) findViewById(R.id.rbAdvisement);
        rbEducation = (RadioButton) findViewById(R.id.rbEducation);
        rbFinance = (RadioButton) findViewById(R.id.rbFinance);

        // OnClickListener to change filter variable
        rgServiceType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup rgServiceType, int rgID)
            {
                RadioButton rbSelected = (RadioButton) findViewById(rgID);
                passed_filter = String.valueOf(rbSelected.getText());
            }
        });
    }

    public void goMenu(View v)
    {
        startActivity(new Intent(this, MenuActivity.class));
    }

    public void goApplyFilter(View v)
    {
        // if button is not checked display message
        if (rgServiceType.getCheckedRadioButtonId() == -1)
        {
            String message = "You must select a filter item to apply!";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        else
        {
            // call goFilter to change activity and pass filter variable
            applyFilter(passed_filter);
        }


    }

// Method for opening FilterResultsActivity and passes passed_filter
    public void applyFilter(String filter)
    {
        Bundle extras = new Bundle();
        extras.putString("passed_filter", filter);
        Intent FilterResults = new Intent(this, FilterResultsActivity.class);
        FilterResults.putExtras(extras);
        startActivity(FilterResults);
    }

}
