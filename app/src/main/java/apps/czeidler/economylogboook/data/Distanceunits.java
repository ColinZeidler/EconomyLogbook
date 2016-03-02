package apps.czeidler.economylogboook.data;

/**
 * Created by Colin on 2016-03-01.
 */
public enum DistanceUnits {
    KILOMETERS (1f, "km"),
    MILES (1.6f, "mi");

    private final float ratio;
    private final String unit;
    DistanceUnits(float ratio, String unit) {
        this.ratio = ratio;
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public float getRatio() {
        return ratio;
    }
}
