package staysafe.andy;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class AlarmActivity extends Activity implements OnClickListener {
	
	SharedPreferences prefs;
	private String phnumber = "";

	final MediaPlayer mp = new MediaPlayer();
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.alarm_activity);
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
	
	public void HouseAlarm(View v)
	{
		
		if(mp.isPlaying())
		{
			mp.stop();
			mp.reset();
		}
		try {
            AssetFileDescriptor afd;
            afd = getAssets().openFd("HouseAlarm.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void DogAlarm(View v)
	{
		MediaPlayer mp = new MediaPlayer();
		if(mp.isPlaying())
		{
			mp.stop();
			mp.reset();
		}
		try {
            AssetFileDescriptor afd;
            afd = getAssets().openFd("DogAlarm.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void PoliceAlarm(View v)
	{
		MediaPlayer mp = new MediaPlayer();
		if(mp.isPlaying())
		{
			mp.stop();
			mp.reset();
		}
		try {
            AssetFileDescriptor afd;
            afd = getAssets().openFd("PoliceAlarm.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

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
}