package madbomberlabs.com.connectniagaraproject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingLocation {

    private static final String TAG = "GeocodingLocation";

    public static void getAddressFromLocation(final String locationAddress,
                                              final Context context, final Handler handler)
    {
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                String resultLong = null;
                String resultLat = null;
                try
                {
                    List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0)
                    {
                        Address address = addressList.get(0);

                        StringBuilder sb1 = new StringBuilder();
                        StringBuilder sb2 = new StringBuilder();

                        sb1.append(address.getLatitude());
                        sb2.append(address.getLongitude());

                        result = sb1.toString() + sb2.toString();
                        resultLat = sb1.toString();
                        resultLong = sb2.toString();
                    }
                }
                catch (IOException e)
                {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }
                finally
                {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (resultLong != null && resultLat != null)
                    {
                        message.what = 1;
                        Bundle bundle = new Bundle();

                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result;

                        bundle.putString("address", result);
                        bundle.putString("addressLong", resultLong);
                        bundle.putString("addressLat", resultLat);

                        message.setData(bundle);
                    }
                    else
                        {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}