package apps.czeidler.economylogboook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Colin on 2016-02-28.
 * Data base access manager
 */
public class DataManager extends SQLiteOpenHelper {

    //Cars Table
    private static final String C_TABLENAME = "Cars";
    private static final String C_MAKE = "make";
    private static final String C_MODEL = "model";

    //Entrys Table
    private static final String E_TABLENAME = "Entry";
    private static final String E_DIST = "distance";
    private static final String E_FUEL = "fuel";
    private static final String E_DTYPE = "distanceType"; //dConv ID
    private static final String E_FTYPE = "fuelType"; //fConv ID
    private static final String E_CAR = "car"; //Cars ID
    private static final String E_DATE = "date";

    /*
    *Conversion tables
    */
    //Distance Conversion
    private static final String DC_TABLENAME = "DistConv";
    private static final String DC_UNIT = "unitLabel";
    private static final String DC_MULT = "multiplier"; //multipler relative to km

    //Fuel Conversion
    private static final String FC_TABLENAME = "FuelConv";
    private static final String FC_UNIT = "unitLabel";
    private static final String FC_MULT = "multiplier"; //multipler relative to L


    private static DataManager instance;
    private SQLiteDatabase db;

    public DataManager(Context context) {
        super(context, "", null, 0);
        db = this.getWritableDatabase();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null)
            instance = new DataManager(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Cars
        String createTable = "CREATE TABLE " + C_TABLENAME + "("
                + C_MAKE + " TEXT, " + C_MODEL + " TEXT);";
        db.execSQL(createTable);

        //Create Entries
        createTable = "CREATE TABLE " + E_TABLENAME + "("
                + E_CAR + " INTEGER, " + E_DIST + " REAL, "
                + E_DTYPE + " INTEGER, " + E_FUEL + " REAL, "
                + E_FTYPE + " INTEGER, " + E_DATE + " INTEGER, "
                + "FOREIGN KEY (" + E_CAR + ") REFERENCES " + C_TABLENAME + " (row_id), "
                + "FOREIGN KEY (" + E_DTYPE + ") REFERENCES " + DC_TABLENAME + " (row_id), "
                + "FOREIGN KEY (" + E_FTYPE + ") REFERENCES " + FC_TABLENAME + " (row_id));";
        db.execSQL(createTable);

        //Create Conversions
        createTable = "CREATE TABLE " + DC_TABLENAME + "("
                + DC_UNIT + " TEXT, " + DC_MULT + " REAL);";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + FC_TABLENAME + "("
                + FC_UNIT + " TEXT, " + FC_MULT + " REAL);";
        db.execSQL(createTable);

        ContentValues km = new ContentValues();
        km.put(DC_UNIT, "KM");
        km.put(DC_MULT, 1);
        db.insert(FC_TABLENAME, null, km);
        ContentValues miles = new ContentValues();
        miles.put(DC_UNIT, "mi");
        miles.put(DC_MULT, 1.6);
        db.insert(FC_TABLENAME, null, miles);

        ContentValues liters = new ContentValues();
        liters.put(FC_UNIT, "L");
        liters.put(FC_MULT, 1);
        db.insert(FC_TABLENAME, null, liters);
        ContentValues gallons = new ContentValues();
        gallons.put(FC_UNIT, "G");
        gallons.put(FC_MULT, 3.78541);
        db.insert(FC_TABLENAME, null, gallons);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
