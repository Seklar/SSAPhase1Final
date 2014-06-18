package staysafe.andy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CheckBox;
import android.app.AlertDialog;

import java.io.*;

public class CreateActivity extends Activity implements OnClickListener{

	private String travelMethod = "Bike";
	
	SharedPreferences prefs;
	private String phnumber = "";
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.create);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		phnumber = prefs.getString("pref_emservice", "");
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		RadioGroup categoryGroup = (RadioGroup) findViewById(R.id.radgroup_create_p1);
	    categoryGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	            switch(checkedId) {
	            case R.id.radioBtn_bike:
		                travelMethod = "Bike";
		                Toast.makeText(getBaseContext(), "Method of Travel Set to BIKE", Toast.LENGTH_SHORT).show();
		            break;
		        case R.id.radioBtn_walking:
		            	travelMethod = "Walking";
		            	Toast.makeText(getBaseContext(), "Method of Travel Set to WALKING", Toast.LENGTH_SHORT).show();
		            break;
		        case R.id.radioBtn_bus:
		            	travelMethod = "Bus";
		            	Toast.makeText(getBaseContext(), "Method of Travel Set to BUS", Toast.LENGTH_SHORT).show();
		            break;
		        case R.id.radioBtn_car:
		            	travelMethod = "Car";
		            	Toast.makeText(getBaseContext(), "Method of Travel Set to CAR", Toast.LENGTH_SHORT).show();
		            break;
		        case R.id.radioBtn_taxi:
		            	travelMethod = "Taxi";
		            	Toast.makeText(getBaseContext(), "Method of Travel Set to TAXI", Toast.LENGTH_SHORT).show();
		            break;
	            }
	        }
	    });
		
		}
		
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
			phnumber = prefs.getString("pref_emservice", "");
		}


		@Override
		public boolean onCreateOptionsMenu(Menu menu){
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.menu, menu);
		    return true;
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		phnumber = prefs.getString("pref_emservice", "");
	}
	
	public boolean validationCheck()
	{
		EditText startLocation = (EditText) findViewById(R.id.start_place_input);
		EditText endLocation = (EditText) findViewById(R.id.end_place_input);
		boolean retval = true;
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("ERROR");
		
		if ("".equals(startLocation.getText().toString().trim()))
		{
			builder1.setMessage("Please enter a valid Start Location.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert11 = builder1.create();
            alert11.show();
			retval = false;
		}
		else
		{
		}
		
		if ("".equals(endLocation.getText().toString().trim()))
		{
			builder1.setMessage("Please enter a valid End Location");
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert11 = builder1.create();
            alert11.show();
			retval = false;
		}
		else
		{
		}
		
		return retval;
	}
	
	public void CreatePage2(View v)
	{
		if(validationCheck())
		{
			EditText startLocation = (EditText) findViewById(R.id.start_place_input);
			EditText endLocation = (EditText) findViewById(R.id.end_place_input);
			Intent createintent = new Intent(this, Create2Activity.class);
			createintent.putExtra("endlocation",endLocation.getText().toString());
			createintent.putExtra("startlocation",startLocation.getText().toString());
			createintent.putExtra("methodoftravel", travelMethod);
			startActivity(createintent);
		}
		else
		{
		}
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
		builder1.setMessage("Please confirm you wish to contact Emergency Services (000). This feature should only be used in Emergency situations.");
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
