package apps.czeidler.economylogboook.data;

import java.sql.Date;
import java.text.DateFormat;

/**
 * Created by Colin on 2016-02-28.
 * Stores Economy entry data and converts to appropriate units on get.
 */
public class EconEntry {
    private Date entryDate;
    private float fuelCount;
    private float fuelMult;
    private float distanceCount;
    private float distanceMult;

    public EconEntry(long theDate, float fuel, float fueltMult, float dist, float distMult) {
        entryDate = new Date(theDate);
        this.fuelCount = fuel;
        this.fuelMult = fueltMult;
        this.distanceCount = dist;
        this.distanceMult = distMult;
    }

    long GetDateLong() {
        return entryDate.getTime();
    }

    public String getDateString() {
        DateFormat format = DateFormat.getDateInstance();
        return format.format(entryDate);
    }

    public float getFuelCount(float expectedUnitMult) {
        return convertUnit(expectedUnitMult, this.fuelMult, this.fuelCount);
    }


    public float getDistanceCount(float expectedUnitMult) {
        return convertUnit(expectedUnitMult, this.distanceMult, this.distanceCount);
    }

    private float convertUnit(float expected, float current, float value) {
        float result = value;
        result = result * current;
        result = result / expected;
        return result;
    }

}
