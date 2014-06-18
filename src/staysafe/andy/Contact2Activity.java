package staysafe.andy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Contact2Activity extends Activity implements OnClickListener {

	private ListView lv;
	
	SharedPreferences prefs;
	private String phnumber = "";
	
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.contact2);
		
		lv = (ListView) findViewById(R.id.contactslist);
		ArrayList<HashMap<String, String>> ar = getContacts();
		SimpleAdapter newcontact = new SimpleAdapter(this, ar, R.layout.listview_item, new String[] {"name", "phone"}, new int[] {android.R.id.text1, android.R.id.text2} );
		lv.setAdapter(newcontact);
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
	
	private ArrayList<HashMap<String, String>> getContacts() {
		  ContentResolver cr = getContentResolver();
		  Cursor cCur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		  Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		 
		  ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		 
		  HashMap<String, String> contacts = new HashMap<String,String>();
		 
		  while (cCur.moveToNext()) {
		    String id = cCur.getString(cCur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
		    String name = cCur.getString(cCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		    
		 
		    contacts.put(id, name);
		  }
		 
		  while (pCur.moveToNext()) {
		    String id = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
		 
		    String name = contacts.get(id);
		    String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
		 
		    HashMap<String, String> h = new HashMap<String, String>();
		    h.put("name", name);
		    h.put("phone", phone);
		    data.add(h);
		  }
		 
		  pCur.close();
		  cCur.close();
		 
		  return data;
		}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
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
