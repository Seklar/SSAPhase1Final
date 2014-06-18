package staysafe.andy;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefsFragment extends PreferenceActivity{

    static final String PREFS_NAME = "defaults";

    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
