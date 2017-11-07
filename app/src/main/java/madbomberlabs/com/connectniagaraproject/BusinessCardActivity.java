package madbomberlabs.com.connectniagaraproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BusinessCardActivity extends Activity
{

    TextView tv_org, tv_name, tv_email, tv_phone, tv_website, tv_servicetype,
            tv_servicesprovided, tv_address;

    String Org, FirstName, LastName, Email, Phone, Website, ServiceType, ServicesProvided, Address,
            Favorite, passedID, FavoriteChanged, emailTo, dbPhone, phoneNum, dbWeb, orgWeb, address,
            streetAddress, cityAddress, stateAddress, zipAddress;

    String locationAddressLong, locationAddressLat;

    CheckBox cbFavorite;
    DBAdapter niagaraDB;
    Cursor c, c2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_card);

        // Assign variables
        tv_org = (TextView) findViewById(R.id.tv_org);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_website = (TextView) findViewById(R.id.tv_website);
        tv_servicetype = (TextView) findViewById(R.id.tv_servicetype);
        tv_servicesprovided = (TextView) findViewById(R.id.tv_servicesprovided);
        tv_address = (TextView) findViewById(R.id.tv_address);

        cbFavorite = (CheckBox) findViewById(R.id.cbFavorite);

        niagaraDB = new DBAdapter(this);

        // Set variable for passedID
        passedID = this.getIntent().getExtras().getString("pass_id");

        // Fetch db items and assign to variables
        niagaraDB.open();

        c = niagaraDB.getRecordByID(passedID);

        if (c.moveToFirst()) {
            Org = c.getString(0);
            FirstName = c.getString(1);
            LastName = c.getString(2);
            Email = c.getString(3);
            Phone = c.getString(4);
            Website = c.getString(5);
            ServiceType = c.getString(6);
            ServicesProvided = c.getString(7);
            Address = c.getString(8) + "\n" + c.getString(9) + ", " +
                      c.getString(10) + " " + c.getString(11);
            Favorite = c.getString(12);
        }

        // Set text items to db items
        tv_org.setText(Org);
        tv_name.setText(FirstName + " " + LastName);
        tv_email.setText(Email);
        tv_phone.setText(Phone);
        tv_website.setText(Website);
        tv_servicetype.setText(ServiceType);
        tv_servicesprovided.setText(ServicesProvided);
        tv_address.setText(Address);

        niagaraDB.close();

        // if favorite = 1 then check box
        if (Favorite.equals("1"))
        {
            cbFavorite.setChecked(true);
        }
        else
        {
            cbFavorite.setChecked(false);
        }

    // GeoCoding for address features
        // Get address variable
        niagaraDB.open();
        c3 = niagaraDB.getAddressByID(passedID);
        if (c3.moveToFirst())
        {
            streetAddress = c3.getString(0);
            cityAddress = c3.getString(1);
            stateAddress = c3.getString(2);
            zipAddress = c3.getString(3);
        }
        niagaraDB.close();

        address = streetAddress + " " + cityAddress + " " + stateAddress + " " + zipAddress;

        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address, getApplicationContext(), new GeocoderHandler());

    // onClick for Email
        tv_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showEmailDialog();
            }
        });

    // onClick for Call
        tv_phone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get phone num
                niagaraDB.open();
                c2 = niagaraDB.getPhoneByID(passedID);
                if (c2.moveToFirst())
                {
                    dbPhone = c2.getString(0);
                }
                niagaraDB.close();

                // Replace any non-digit in phone number to make call
                phoneNum = dbPhone.replaceAll("\\D", "");

                // make call
                goCall(phoneNum);
            }
        });

    // onClick for Web
        tv_website.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                niagaraDB.open();
                c2 = niagaraDB.getWebByID(passedID);
                if (c2.moveToFirst())
                {
                    dbWeb = c2.getString(0);
                }
                niagaraDB.close();

                orgWeb = "http://" + dbWeb;

                goWeb(orgWeb);
            }
        });

    // onClick for Address
        tv_address.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                goMap();
            }
        });


    // When checkbox status changes, change value of Favorite
        cbFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                niagaraDB.open();

                // Check if Favorite is 1 or 0
                if (Favorite.equals("0"))
                {
                    FavoriteChanged = "1";
                }
                else if (Favorite.equals("1"))
                {
                    FavoriteChanged = "0";
                }

                niagaraDB.updateFavById(passedID, FavoriteChanged);

                niagaraDB.close();
            }
        });

    }

    public void showEmailDialog()
    {
        // Get dialog_box_goals.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(BusinessCardActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_box_send_email, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BusinessCardActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText etEmailMessage = (EditText) promptView.findViewById(R.id.etMailMessage);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("Submit", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //mycode

                        // Get email
                        niagaraDB.open();
                        c2 = niagaraDB.getEmailByID(passedID);
                        if (c2.moveToFirst())
                        {
                            emailTo = c2.getString(0);
                        }
                        niagaraDB.close();

                        // This is for final code
                        // String to = "mailto:" + emailTo;
                        String to = "snownwakendirt@yahoo.com";
                        String subject = "Mail From Connect & Protect Niagara App";
                        String message = etEmailMessage.getText().toString();

                        if (message.isEmpty())
                        {
                            Toast.makeText(BusinessCardActivity.this,
                                    "Message must contain something",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Intent email = new Intent(Intent.ACTION_SEND);
                            email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                            // email.putExtra(Intent.EXTRA_CC, new String[]{ to});
                            // email.putExtra(Intent.EXTRA_BCC, new String[]{to});
                            email.putExtra(Intent.EXTRA_SUBJECT, subject);
                            email.putExtra(Intent.EXTRA_TEXT, message);

                            // need this to prompt email client only
                            email.setType("message/rfc822");

                            try {
                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                finish();
                                Log.i("Email Sent...", "");
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(BusinessCardActivity.this,
                                        "There is no email client installed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void goCall(final String phoneNum)
    {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNum, null)));
    }

    public void goWeb(String orgWeb)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(orgWeb)));

    }


    public void goCloseBusinessCard(View v)
    {
        this.finish();
    }

    // Method for opening BusinessCardActivity and passes ordID
    public void goMap()
    {
        //int locationAddressLatInt = Integer.parseInt(locationAddressLat);
        //int locationAddressLongInt = Integer.parseInt(locationAddressLong);

        Bundle extras = new Bundle();
        extras.putString("pass_lat", locationAddressLat);
        extras.putString("pass_long", locationAddressLong);
        Intent Map = new Intent(BusinessCardActivity.this, MapActivity.class);
        Map.putExtras(extras);
        startActivity(Map);
    }

    // Handler for latlong
    private class GeocoderHandler extends Handler
    {
        @Override
        public void handleMessage(Message message)
        {

            switch (message.what)
            {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddressLat = bundle.getString("addressLat");
                    locationAddressLong = bundle.getString("addressLong");
                    break;
                default:
                    locationAddressLong = null;
                    locationAddressLat = null;
            }

            Toast.makeText(BusinessCardActivity.this,
                    locationAddressLat + "\n" + locationAddressLong,
                    Toast.LENGTH_LONG).show();
        }
    }
}
