package apps.czeidler.economylogboook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import apps.czeidler.economylogboook.data.DistanceUnits;
import apps.czeidler.economylogboook.data.EconEntry;
import apps.czeidler.economylogboook.data.FuelUnits;

public class EntryDisplay extends AppCompatActivity {
    private EconListAdapter econListAdapter;
    private ArrayList<EconEntry> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added new list item", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                list.add(new EconEntry(Calendar.getInstance().getTimeInMillis(),
                        10f, FuelUnits.LITERS.getRatio(),
                        200f, DistanceUnits.KILOMETERS.getRatio()));
                econListAdapter.notifyDataSetChanged();
            }
        });

        //TODO init Totals display
        //TODO init ListView
        list = new ArrayList<>();
        list.add(new EconEntry(Calendar.getInstance().getTimeInMillis(),
                34.5f, FuelUnits.LITERS.getRatio(),
                557.8f, DistanceUnits.KILOMETERS.getRatio()));

        list.add(new EconEntry(Calendar.getInstance().getTimeInMillis(),
                10f, FuelUnits.GALLONS.getRatio(),
                100f, DistanceUnits.MILES.getRatio()));

        list.add(new EconEntry(Calendar.getInstance().getTimeInMillis(),
                10f, FuelUnits.LITERS.getRatio(),
                200f, DistanceUnits.KILOMETERS.getRatio()));
        econListAdapter = new EconListAdapter(getApplicationContext(),
                R.layout.econ_entry_adapter,
                list);
        ListView lView = (ListView) findViewById(R.id.entry_list);
        lView.setAdapter(econListAdapter);
        //TODO register for preferences change update
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry_display, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        econListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
