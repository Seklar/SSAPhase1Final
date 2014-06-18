package staysafe.andy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

public class SplashActivity extends Activity {

	public void onCreate(Bundle bundle)
	{
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		super.onCreate(bundle);
		setContentView(R.layout.splash_activity);
		
		new CountDownTimer(1500, 1000){
			
			public void onFinish()
			{
				finishedCountdown();
			}

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		}.start();
	}
	
	public void finishedCountdown()
	{
		Intent createintent = new Intent(this, MenuActivity.class);
		startActivity(createintent);
	}
	
}
