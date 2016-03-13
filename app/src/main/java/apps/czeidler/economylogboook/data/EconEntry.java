package apps.czeidler.economylogboook.data;

import java.sql.Date;
import java.text.DateFormat;

/**
 * Created by Colin on 2016-02-28.
 * Stores Economy entry data and converts to appropriate units on get.
 */
public class EconEntry {
    private Date entryDate;
    private double fuelCount;
    private double fuelMult;
    private double distanceCount;
    private double distanceMult;

    public EconEntry(long theDate, double fuel, double fueltMult, double dist, double distMult) {
        entryDate = new Date(theDate);
        this.fuelCount = fuel;
        this.fuelMult = fueltMult;
        this.distanceCount = dist;
        this.distanceMult = distMult;
    }

    long getDateLong() {
        return entryDate.getTime();
    }

    public String getDateString() {
        DateFormat format = DateFormat.getDateInstance();
        return format.format(entryDate);
    }

    public double getFuelCount(double expectedUnitMult) {
        return convertUnit(expectedUnitMult, this.fuelMult, this.fuelCount);
    }


    public double getDistanceCount(double expectedUnitMult) {
        return convertUnit(expectedUnitMult, this.distanceMult, this.distanceCount);
    }

    private double convertUnit(double expected, double current, double value) {
        double result = value;
        result = result * current;
        result = result / expected;
        return result;
    }

    protected double getDistance() {
        return distanceCount;
    }

    protected double getFuel() {
        return fuelCount;
    }

    protected double getFuelMult() {
        return fuelMult;
    }

    protected double getDistanceMult() {
        return distanceMult;
    }

}
