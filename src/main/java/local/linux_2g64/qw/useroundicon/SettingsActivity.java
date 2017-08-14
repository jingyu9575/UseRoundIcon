package local.linux_2g64.qw.useroundicon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.io.File;

public class SettingsActivity extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @SuppressLint("SetWorldReadable")
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void fixPermissions() {
        File prefsDir = new File(getApplicationInfo().dataDir, "shared_prefs");
        File prefsFile = new File(prefsDir, PreferenceManager.getDefaultSharedPreferencesName(this) + ".xml");
        if (prefsFile.exists()) {
            prefsFile.setReadable(true, false);
            prefsFile.getParentFile().setExecutable(true, false);
            prefsFile.getParentFile().getParentFile().setExecutable(true, false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        fixPermissions();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        fixPermissions();
    }
}
