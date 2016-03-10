package apps.czeidler.economylogboook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin on 2016-02-28.
 * Data base access manager
 */
public class DataManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Economy db";

    //Entrys Table
    private static final String E_TABLENAME = "Entry";
    private static final String E_DIST = "distance";
    private static final String E_FUEL = "fuel";
    private static final String E_DTYPE = "distanceType"; //conv ratio
    private static final String E_FTYPE = "fuelType"; //conv ratio
    private static final String E_DATE = "date";

    private static DataManager instance;
    private SQLiteDatabase db;

    public DataManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null)
            instance = new DataManager(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Entries
        String createTable = "CREATE TABLE " + E_TABLENAME + "("
                + E_DIST + " REAL, " + E_DTYPE + " REAL, "
                + E_FUEL + " REAL, " + E_FTYPE + " REAL, "
                + E_DATE + " INTEGER);";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + E_TABLENAME);
    }

    public void addEntry(EconEntry entry) {
        ContentValues values = new ContentValues();
        values.put(E_DIST, entry.getDistance());
        values.put(E_DTYPE, entry.getDistanceMult());
        values.put(E_FUEL, entry.getFuel());
        values.put(E_FTYPE, entry.getFuelMult());
        values.put(E_DATE, entry.getDateLong());

        db.insert(E_TABLENAME, null, values);
    }

    public ArrayList<EconEntry> getEntries() {
        ArrayList<EconEntry> eList = new ArrayList<>();
        String query = "SELECT * from " + E_TABLENAME;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                eList.add(buildEntry(cursor));
            }while (cursor.moveToNext());
        }

        return eList;
    }

    private EconEntry buildEntry(Cursor cursor) {
        return new EconEntry(cursor.getLong(5),
                cursor.getFloat(3), // fuel
                cursor.getFloat(4), // fuel mult
                cursor.getFloat(1), // dist
                cursor.getFloat(2)); //dist mult
    }
}
