package apps.czeidler.economylogboook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Colin on 2016-03-01.
 */
public enum DistanceUnits {
    KILOMETERS (1f, "km"),
    MILES (1.6f, "mi");

    private final double ratio;
    private final String unit;
    DistanceUnits(double ratio, String unit) {
        this.ratio = ratio;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public double getRatio() {
        return ratio;
    }

    public static String getSystemUnit(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String distPref = prefs.getString("pref_distunit", "KILOMETERS");
        return valueOf(distPref).getUnit();
    }

    public static double getSystemRatio(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String distPref = prefs.getString("pref_distunit", "KILOMETERS");
        return valueOf(distPref).getRatio();
    }
}
