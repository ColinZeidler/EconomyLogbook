package apps.czeidler.economylogboook;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import apps.czeidler.economylogboook.data.DataManager;
import apps.czeidler.economylogboook.data.DistanceUnits;
import apps.czeidler.economylogboook.data.EconEntry;
import apps.czeidler.economylogboook.data.FuelUnits;

public class EntryCreate extends AppCompatActivity implements CalendarDatePickerDialogFragment.OnDateSetListener{

    private DateFormat format = DateFormat.getDateInstance();
    private Calendar mCal = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Entry created", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                double fuel = Double.valueOf(((EditText)findViewById(R.id.fuel_edit)).getText().toString());
                double dist = Double.valueOf(((EditText)findViewById(R.id.dist_edit)).getText().toString());
                Date d = mCal.getTime();
                EconEntry inputItem = new EconEntry(d.getTime(),
                        fuel, FuelUnits.getSystemRatio(getBaseContext()),
                        dist, DistanceUnits.getSystemRatio(getBaseContext()));

                DataManager.getInstance(getBaseContext()).addEntry(inputItem);
                finish();
            }
        });


        Button calendar = (Button) findViewById(R.id.date_button);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment dp = new CalendarDatePickerDialogFragment()
                        .setPreselectedDate(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), mCal.get(Calendar.DAY_OF_MONTH))
                        .setOnDateSetListener(EntryCreate.this);
                dp.show(getSupportFragmentManager(), "date_picker_tag");
            }
        });
        calendar.setText(format.format(mCal.getTime()));
        EditText dist = (EditText) findViewById(R.id.dist_edit);
        EditText fuel = (EditText) findViewById(R.id.fuel_edit);
        dist.setHint(getResources().getString(R.string.dist_hint, DistanceUnits.getSystemUnit(getBaseContext())));
        fuel.setHint(getResources().getString(R.string.fuel_hint, FuelUnits.getSystemUnit(getBaseContext())));
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Button calendar = (Button) findViewById(R.id.date_button);
        mCal.set(year, monthOfYear, dayOfMonth);
        Log.d("date_picker", "Date set");
        calendar.setText(format.format(mCal.getTime()));
    }
}
