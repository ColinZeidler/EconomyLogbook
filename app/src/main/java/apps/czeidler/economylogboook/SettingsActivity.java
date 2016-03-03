package apps.czeidler.economylogboook;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toolbar;


/**
 * Created by Colin on 2016-02-29.
 * Settings
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Toolbar tb = new Toolbar(getBaseContext());
        setActionBar(tb);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
