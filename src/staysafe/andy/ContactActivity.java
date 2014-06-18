package staysafe.andy;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends Activity implements OnClickListener {

	private EditText name;
	private EditText number;
	SharedPreferences prefs;
	private String phnumber = "";
	final static String LOG_TAG = "SSA";
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.contact);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		phnumber = prefs.getString("pref_emservice", "");
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
	
	public void ContactPage2(View v)
	{
		Intent createintent = new Intent(this, Contact2Activity.class);
		startActivity(createintent);
	}
	
	public void AddContact(View arg0) 
	{
		if(validationCheck())
		{
		// Save the contact to the phone and if successful, clear the text fields ready for the next contact.
		if (SaveContact())
		{
				Toast.makeText(getBaseContext(), "Contact Saved!", Toast.LENGTH_SHORT).show();
				name = (EditText) findViewById(R.id.name_person_input);
				number = (EditText) findViewById(R.id.phone_number_input);
				name.setText("");
				number.setText("");
		}
		else
				Toast.makeText(getBaseContext(), "Error saving contact, see LogCat!", Toast.LENGTH_SHORT).show();
		}
	}
	
	boolean SaveContact() {
		
		name = (EditText) findViewById(R.id.name_person_input);
		number = (EditText) findViewById(R.id.phone_number_input);
		
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = ops.size();
        
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());
        // Insert the name
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name.getText().toString()) // Name of the person
                .build());
        // Insert the phone number
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number.getText().toString()) // Number of the person
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build()); //   
		// Save the contact
		Uri newContactUri = null;
		// Push through to the contacts list
        try
        {
            ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            if (res!=null && res[0]!=null) {
            	newContactUri = res[0].uri;	
            	Log.d(LOG_TAG, "URI added contact:"+ newContactUri);
            }
            else Log.e(LOG_TAG, "Contact not added.");
        }
        catch (RemoteException e)
        { 
            // error
        	Log.e(LOG_TAG, "Error (1) adding contact.");
        	newContactUri = null;
        }
        catch (OperationApplicationException e) 
        {
            // error
        	Log.e(LOG_TAG, "Error (2) adding contact.");
        	newContactUri = null;
        }  
        Log.d(LOG_TAG, "Contact added to system contacts.");
        
        if (newContactUri == null) {
        	Log.e(LOG_TAG, "Error creating contact");
        	return false;
        }
        
        return true;
	}
	
	// Quick Menu Options
	
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
	
	// This code will initiate the call to Emergency Services.

	public void callEMServices(View v)
	{
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("WARNING");
		builder1.setMessage("Please confirm you wish to contact Emergency Services ("+ phnumber +"). This feature should only be used in Emergency situations.");
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
		EditText name = (EditText) findViewById(R.id.name_person_input);
		EditText number = (EditText) findViewById(R.id.phone_number_input);
		boolean retval = true;
		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		builder1.setTitle("ERROR");
		
		if ("".equals(name.getText().toString().trim()))
		{
			builder1.setMessage("Please enter a Name.");
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
		
		if ("".equals(number.getText().toString().trim()))
		{
			builder1.setMessage("Please enter a valid Number");
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
}
