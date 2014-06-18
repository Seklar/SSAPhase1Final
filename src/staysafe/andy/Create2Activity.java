package staysafe.andy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Create2Activity extends Activity implements OnClickListener {
	
	protected static final int REQUEST_CONTACTPICKER = 0;
	private static final int REQUEST_CONTACTPICKER1 = 1;
	private String endlocation;
	private String startlocation;
	private String methodoftravel;
	
	SharedPreferences prefs;
	private String phnumber = "";
	private String createnumber = "";
	private String createnumber2 = "";

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.create2);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent intent = getIntent();
		endlocation = intent.getStringExtra("endlocation");
		startlocation = intent.getStringExtra("startlocation");
		methodoftravel = intent.getStringExtra("methodoftravel");
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		phnumber = prefs.getString("pref_emservice", "");
		
		RadioGroup categoryGroup = (RadioGroup) findViewById(R.id.create_radgroup_p2_1);
	    categoryGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	            switch(checkedId) {
	            case R.id.create_rad_btn3:
	            	Toast.makeText(getBaseContext(), "Contact 2 Set to NONE", Toast.LENGTH_SHORT).show();
	            	createnumber2 = "";
	            	TextView text = (TextView) findViewById(R.id.create_spn1);
	        		text.setText("Select");
		            break;
		        case R.id.create_rad_btn4:
		        	Intent intent = new Intent(Intent.ACTION_PICK, 
		        	           ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		        	  startActivityForResult(intent, REQUEST_CONTACTPICKER1);
		            break;
	            }
	        }
	    });
	    
	    RadioGroup categoryGroup1 = (RadioGroup) findViewById(R.id.create_radgroup_p2_2);
	    categoryGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup group, int checkedId) {
	            switch(checkedId) {
	            case R.id.create_rad_btn1:
	            	Toast.makeText(getBaseContext(), "Contact 1 Set to DEFAULT", Toast.LENGTH_SHORT).show();
	            	createnumber = prefs.getString("pref_icecontact_prim_num", "");
	            	TextView text = (TextView) findViewById(R.id.create_spn2);
	        		text.setText("Select");
		            break;
		        case R.id.create_rad_btn2:
		        	Intent intent = new Intent(Intent.ACTION_PICK, 
		        	ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		        	startActivityForResult(intent, REQUEST_CONTACTPICKER);
		            break;
	            }
	        }
	    });
		
		}
	
	
	public void backButton(View v)
	{
		Intent createintent = new Intent(this, CreateActivity.class);
		startActivity(createintent);
		finish();
	}
		
	public void setPhoneNumber(String phoneNumber)
	{
		Toast.makeText(getBaseContext(), "Contact 1 set to " + phoneNumber, Toast.LENGTH_SHORT).show();
		TextView text = (TextView) findViewById(R.id.create_spn1);
		text.setText(phoneNumber);
		createnumber = phoneNumber;
	}
	
	public void setPhoneNumber1(String phoneNumber)
	{
		Toast.makeText(getBaseContext(), "Contact 2 set to " + phoneNumber, Toast.LENGTH_SHORT).show();
		TextView text = (TextView) findViewById(R.id.create_spn2);
		text.setText(phoneNumber);
		createnumber2 = phoneNumber;
	}
		
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key) {
			phnumber = prefs.getString("pref_emservice", "");
		}
		
		@Override
		public void onResume()
		{
			super.onResume();
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
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);  
	  if (requestCode == REQUEST_CONTACTPICKER)
	  {	   
		if(resultCode == RESULT_OK)
	   {
			Uri contentUri = data.getData();
			String contactId = contentUri.getLastPathSegment();
			Cursor cursor = getContentResolver().query(  
			        Phone.CONTENT_URI, null,  
			        Phone._ID + "=?",       // < - Note, not CONTACT_ID!
			        new String[]{contactId}, null);
			startManagingCursor(cursor);
           Boolean numbersExist = cursor.moveToFirst();            
           int phoneNumberColumnIndex = cursor.getColumnIndex(Phone.NUMBER);            
           String phoneNumber = "";
           while (numbersExist) 
           {
             phoneNumber = cursor.getString(phoneNumberColumnIndex);
          	  phoneNumber = phoneNumber.trim();  
             numbersExist = cursor.moveToNext();
           }
			stopManagingCursor(cursor);			
		    if (!phoneNumber.equals("")) 
		    {
			  setPhoneNumber(phoneNumber);		    
			} // phoneNumber != ""
		} // Result Code = RESULT_OK
	  }
	  if (requestCode == REQUEST_CONTACTPICKER1)
	  {	   
		if(resultCode == RESULT_OK)
	   {
			Uri contentUri = data.getData();
			String contactId = contentUri.getLastPathSegment();
			Cursor cursor = getContentResolver().query(  
			        Phone.CONTENT_URI, null,  
			        Phone._ID + "=?",       // < - Note, not CONTACT_ID!
			        new String[]{contactId}, null);
			startManagingCursor(cursor);
           Boolean numbersExist = cursor.moveToFirst();            
           int phoneNumberColumnIndex = cursor.getColumnIndex(Phone.NUMBER);            
           String phoneNumber = "";
           while (numbersExist) 
           {
             phoneNumber = cursor.getString(phoneNumberColumnIndex);
          	  phoneNumber = phoneNumber.trim();  
             numbersExist = cursor.moveToNext();
           }
			stopManagingCursor(cursor);			
		    if (!phoneNumber.equals("")) 
		    {
			  setPhoneNumber1(phoneNumber);		    
			} // phoneNumber != ""
		} // Result Code = RESULT_OK
	  } // Request Code = REQUEST_CONTACTPICER
	 }	// end function
	
	public void Submit(View v)
	{
		EditText interval = (EditText) findViewById(R.id.safety_intervals_input);
		EditText noofpeople = (EditText) findViewById(R.id.no_people_input);
		if(validationCheck())
		{
		Intent createintent = new Intent(this, CountdownActivity.class);
		createintent.putExtra("startlocation", startlocation);
		createintent.putExtra("endlocation", endlocation);
		createintent.putExtra("methodoftravel", methodoftravel);
		createintent.putExtra("interval", interval.getText().toString());
		createintent.putExtra("contactnumber", createnumber);
		createintent.putExtra("contactnumber2", createnumber2);
		createintent.putExtra("noofpeople", noofpeople.getText().toString());
		startActivity(createintent);
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
	
	public boolean validationCheck()
	{
		EditText interval = (EditText) findViewById(R.id.safety_intervals_input);
		EditText numpeople = (EditText) findViewById(R.id.no_people_input);
		boolean retval = true;
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setTitle("ERROR");
		String intervalcheck = interval.getText().toString();
		String numpeoplecheck = numpeople.getText().toString();
		if(intervalcheck.equalsIgnoreCase(""))
		{
			builder2.setMessage("Interval must be 5 or more minutes.");
            builder2.setCancelable(true);
            builder2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert11 = builder2.create();
            alert11.show();
			interval.setText("5");
			retval = false;
		}
		else
		{
		}
		
		if(Double.parseDouble(interval.getText().toString()) < 1.0)
		{
            builder2.setMessage("Interval must be 5 or more minutes.");
            builder2.setCancelable(true);
            builder2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog1, int id) {
                    dialog1.cancel();
                }
            });
            AlertDialog alert2 = builder2.create();
            alert2.show();
			retval = false;
			interval.setText("5");
		}
		else
		{
		}
		
		if(numpeoplecheck.equalsIgnoreCase(""))
		{
			builder2.setMessage("Number of People must be at least 1.");
            builder2.setCancelable(true);
            builder2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert11 = builder2.create();
            alert11.show();
			numpeople.setText("1");
			retval = false;
		}
		else
		{
		}
		
		if(Double.parseDouble(numpeople.getText().toString()) < 1.0)
		{
            builder2.setMessage("Number of People must be at least 1.");
            builder2.setCancelable(true);
            builder2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog1, int id) {
                    dialog1.cancel();
                }
            });
            AlertDialog alert2 = builder2.create();
            alert2.show();
			retval = false;
			numpeople.setText("1");
		}
		else
		{
		}
		
		return retval;
	}
}
