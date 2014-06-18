package staysafe.andy;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapActivity extends Activity implements OnClickListener {

	private double latitude;
	private double longitude;
	SharedPreferences prefs;
	private String phnumber = "";
	private String contactnumber = "";
	private String message;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        GPSTracker tracker = new GPSTracker(this);
        if(tracker.canGetLocation)
        {
        	tracker.getLocation();
        	latitude = tracker.getLatitude();
        	longitude = tracker.getLongitude();
        }
        else
        {
        	
        }

        GoogleMap map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(latitude, longitude), 14));

        map.addMarker(new MarkerOptions()
		.title("Current Position")
		.snippet("Lat:" + latitude + " | Long:" + longitude)
		.anchor(0.5f, 0.5f)
		.position(new LatLng(latitude, longitude)));
    	prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	phnumber = prefs.getString("pref_emservice", "");
    	contactnumber = prefs.getString("pref_icecontact_prim_num", "");
    	}
    	
    	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
    		phnumber = prefs.getString("pref_emservice", "");
    		contactnumber = prefs.getString("pref_icecontact_prim_num", "");
    	}


    	@Override
    	public boolean onCreateOptionsMenu(Menu menu){
    	    MenuInflater inflater = getMenuInflater();
    	    inflater.inflate(R.menu.menu, menu);
    	    return true;
    	}
    	
		@Override
		public void onResume()
		{
			super.onResume();
			phnumber = prefs.getString("pref_emservice", "");
			contactnumber = prefs.getString("pref_icecontact_prim_num", "");
		}
    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item){
    	  switch(item.getItemId()){
    	  case R.id.item_prefs:
    	    startActivity(new Intent(this, PrefsFragment.class));
    	    break;
    	  }
    	  return true;
    	}

	@Override
	public void onClick(View v) {

		
	}
	
	
	public void SendLoc(View v)
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("WARNING");
		builder1.setMessage("Please confirm you wish to send your current location via SMS to " + contactnumber);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Continue",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	Toast.makeText(getBaseContext(), "Location Sent to " + contactnumber, Toast.LENGTH_SHORT).show();
                	SmsManager smsManager = SmsManager.getDefault();
                	message = "Emergency At Location - http://maps.google.com/?q=" + Double.toString(latitude) + "," + Double.toString(longitude);
            		smsManager.sendTextMessage(contactnumber, null , message , null, null);
                    dialog.cancel();
                }
            });
        builder1.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
       AlertDialog alert11 = builder1.create();
       alert11.show();
	}
	
	public void TravelPage(View v)
	{
		Intent createintent = new Intent(this, CreateActivity.class);
		startActivity(createintent);
	}
	
	public void ContactPage(View v)
	{
		Intent createintent = new Intent(this, ContactActivity.class);
		startActivity(createintent);
	}
	
	public void MapPage(View v)
	{
		Intent createintent = new Intent(this, MapActivity.class);
		startActivity(createintent);
	}
	
	public void AboutPage(View v)
	{
		Intent createintent = new Intent(this, AboutActivity.class);
		startActivity(createintent);
	}
	
	public void AlarmPage(View v)
	{
		Intent createintent = new Intent(this, AlarmActivity.class);
		startActivity(createintent);
	}

	public void callEMServices(View v)
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("WARNING");
		builder1.setMessage("Please confirm you wish to contact Emergency Services. This feature should only be used in Emergency situations.");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Continue",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ phnumber));  
                     startActivity(callIntent); 
                }
            });
        builder1.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
       AlertDialog alert11 = builder1.create();
       alert11.show();
	}
}
