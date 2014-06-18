package staysafe.andy;

import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class CountdownActivity extends Activity implements OnClickListener {

	CountDownTimer countdown;
	
	private String endlocation;
	private String startlocation;
	private String methodoftravel;
	private String interval;
	private String contactnumber;
	private String contactnumber2;
	private String noofpeople;
	
	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.countdown);

		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
        keyguardLock.disableKeyguard();
		
		Intent intent = getIntent();
		endlocation = intent.getStringExtra("endlocation");
		startlocation = intent.getStringExtra("startlocation");
		methodoftravel = intent.getStringExtra("methodoftravel");
		interval = intent.getStringExtra("interval");
		contactnumber = intent.getStringExtra("contactnumber");
		contactnumber2 = intent.getStringExtra("contactnumber2");
		noofpeople = intent.getStringExtra("noofpeople");
		
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		final TextView mTextView = (TextView)this.findViewById(R.id.interval_image);
		
		countdown = new CountDownTimer((Long.valueOf(interval)*60000), 1000){
			
			public void onTick(long millisUntilFinished)
			{
				String t = getDurationBreakdown(millisUntilFinished);
				mTextView.setText(t + " Minutes");
			}
			
			public void onFinish()
			{
				finishedCountdown();
				mTextView.setText("ARRIVED");
			}
			
			
		}.start();
	}
	
	public void finishedCountdown()
	{
		Intent createintent = new Intent(this, AlertActivity.class);
		createintent.putExtra("methodoftravel", methodoftravel);
		createintent.putExtra("contactnumber", contactnumber);
		createintent.putExtra("contactnumber2", contactnumber2);
		createintent.putExtra("noofpeople", noofpeople);
		createintent.putExtra("startlocation", startlocation);
		createintent.putExtra("endlocation", endlocation);
		createintent.putExtra("interval", interval);		
		startActivity(createintent);
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(hours);
        sb.append(":");
        sb.append(minutes);
        sb.append(":");
        sb.append(seconds);
        sb.append("");

        return(sb.toString());
    }
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	public void Arrived(View v)
	{
		countdown.cancel();
		Toast.makeText(getBaseContext(), "Interval Cancelled!", Toast.LENGTH_SHORT).show();
		Intent createintent = new Intent(this, MenuActivity.class);
		startActivity(createintent);
		finish();
	}
	
	public void Minimize(View v)
	{
		
	}
}
