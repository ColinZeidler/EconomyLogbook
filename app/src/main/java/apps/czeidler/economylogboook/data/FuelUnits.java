package apps.czeidler.economylogboook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Colin on 2016-03-01.
 */
public enum FuelUnits {
    LITERS (1f, "L"),
    GALLONS(3.78f, "G");

    private final float ratio;
    private final String unit;
    FuelUnits(float ratio, String unit) {
        this.ratio = ratio;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public float getRatio() {
        return ratio;
    }

    public static String getSystemUnit(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String fuelPref = prefs.getString("pref_fuelunit", "LITERS");
        return valueOf(fuelPref).getUnit();
    }

    public static float getSystemRatio(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String fuelPref = prefs.getString("pref_fuelunit", "LITERS");
        return valueOf(fuelPref).getRatio();
    }
}
