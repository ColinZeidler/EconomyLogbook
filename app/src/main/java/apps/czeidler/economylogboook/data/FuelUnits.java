package apps.czeidler.economylogboook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Colin on 2016-03-01.
 */
public enum FuelUnits {
    LITERS (1, "L"),
    GALLONS(3.78, "G");

    private final double ratio;
    private final String unit;
    FuelUnits(double ratio, String unit) {
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
        String fuelPref = prefs.getString("pref_fuelunit", "LITERS");
        return valueOf(fuelPref).getUnit();
    }

    public static double getSystemRatio(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        String fuelPref = prefs.getString("pref_fuelunit", "LITERS");
        return valueOf(fuelPref).getRatio();
    }
}
