package local.linux_2g64.qw.useroundicon;

import android.content.res.XResources;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;

@SuppressWarnings("unused")
public class Main implements IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XSharedPreferences prefs = new XSharedPreferences(Main.class.getPackage().getName());
        if (prefs.getBoolean("pref_use_round", true) != prefs.getBoolean("pref_use_round", false))
            XposedBridge.log("Warning: cannot read preference value");
        XResources.setSystemWideReplacement("android", "bool", "config_useRoundIcon",
                prefs.getBoolean("pref_use_round", true));
    }
}
