package staysafe.andy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AlertActivity extends Activity implements OnClickListener {
	
	private String methodoftravel;
	private String contactnumber;
	private String contactnumber2;
	private String noofpeople;
	private String interval;
	private String startlocation;
	private String endlocation;
	
    public MediaPlayer mp;
    Vibrator vib;
    WakeLock wL , fL;

    PowerManager pm;
    CountDownTimer waitTimer;
    
    SharedPreferences prefs;
    
    private double latitude;
	private double longitude;
    
    public void sendSMS(){
    	String message;
    	message = "Please Help - Emergency @ Loc - http://maps.google.com/?q=" + Double.toString(latitude) + "," + Double.toString(longitude) + " | " + noofpeople + " People | Travelling Via " + methodoftravel ;
    	
		Toast.makeText(getApplicationContext(), "SMS Sent! PH: " + contactnumber + " | PH2: " + contactnumber2 + " | MOT: " + methodoftravel + " | NOP: " + noofpeople, Toast.LENGTH_SHORT).show();
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(contactnumber, null , message , null, null);
		
		if(contactnumber2 != "")
		{
			smsManager.sendTextMessage(contactnumber2, null , message , null, null);
		}
		
		if (mp.isPlaying()) {

			vib.cancel();
			mp.stop();
            mp.prepareAsync();
            mp.seekTo(0);    
            wL.release();
			waitTimer.cancel();

			Intent createintent = new Intent(this, MenuActivity.class);
			startActivity(createintent);
			finish();
		  }
		 else {
			 	vib.cancel();
			 	wL.release();
			 	waitTimer.cancel();
				Toast.makeText(getApplicationContext(), "Nothing is playing", Toast.LENGTH_SHORT).show();
				Intent createintent = new Intent(this, MenuActivity.class);
				startActivity(createintent);
				finish();
				  }
		
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert);
        
        Intent intent = getIntent();
        endlocation = intent.getStringExtra("endlocation");
		startlocation = intent.getStringExtra("startlocation");
		methodoftravel = intent.getStringExtra("methodoftravel");
		interval = intent.getStringExtra("interval");
		contactnumber = intent.getStringExtra("contactnumber");
		contactnumber2 = intent.getStringExtra("contactnumber2");
		noofpeople = intent.getStringExtra("noofpeople");
        
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

        
        Button btn_safe = (Button)this.findViewById(R.id.btn_safe);

    	mp = MediaPlayer.create(this, R.raw.soundfile);
    	vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    	btn_safe.setOnClickListener(this);
    	
    	//allowing the app to run in the background
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wL = pm.newWakeLock(pm.PARTIAL_WAKE_LOCK, "My Tag");
		fL = pm.newWakeLock((pm.SCREEN_BRIGHT_WAKE_LOCK | pm.FULL_WAKE_LOCK | pm.ACQUIRE_CAUSES_WAKEUP), "My Tag");
		wL.acquire();
		wakeDevice();

		//setting the pattern and telling it to start
		long[] pattern = {100, 200, 400};
		// starting vibration and music player
		vib.vibrate(pattern, 0);
		mp.start();  


	   waitTimer = new CountDownTimer(60000, 1000){
		public void onFinish()
		{

			sendSMS();
		}
		@Override
		public void onTick(long arg0) {


		}

	}.start();
    }
    
    public void wakeDevice() {
        fL.acquire();
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
    }

	@Override
	public void onClick(View arg0) {
		Toast.makeText(getApplicationContext(), "Clicked Safe", Toast.LENGTH_SHORT).show();
    	if (mp.isPlaying()) {

			vib.cancel();
			mp.stop();
            mp.prepareAsync();
            mp.seekTo(0);    
            wL.release();
			waitTimer.cancel();

			Intent createintent = new Intent(this, CountdownActivity.class);
			createintent.putExtra("methodoftravel", methodoftravel);
			createintent.putExtra("contactnumber", contactnumber);
			createintent.putExtra("contactnumber2", contactnumber2);
			createintent.putExtra("noofpeople", noofpeople);
			createintent.putExtra("startlocation", startlocation);
			createintent.putExtra("endlocation", endlocation);
			createintent.putExtra("interval", interval);	
			startActivity(createintent);
			finish();
		  }
		 else {
				Toast.makeText(getApplicationContext(), "Nothing is playing", Toast.LENGTH_SHORT).show();
				  }
		
	}

}
