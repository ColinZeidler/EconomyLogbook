package apps.czeidler.economylogboook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import apps.czeidler.economylogboook.data.DataManager;
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
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), EntryCreate.class);
                startActivity(intent);
            }
        });

        ((TextView)findViewById(R.id.dist_unit)).setText(DistanceUnits.getSystemUnit(getBaseContext()));
        ((TextView)findViewById(R.id.fuel_unit)).setText(FuelUnits.getSystemUnit(getBaseContext()));
        ((TextView)findViewById(R.id.econ_unit)).setText(getResources().getString(R.string.econ_total_units,
                DistanceUnits.getSystemUnit(getBaseContext()),
                FuelUnits.getSystemUnit(getBaseContext())));

        ((TextView)findViewById(R.id.dist)).setText("0");
        ((TextView)findViewById(R.id.fuel_data)).setText("0");
        ((TextView)findViewById(R.id.econ_data)).setText("0");

        list = new ArrayList<>();
        econListAdapter = new EconListAdapter(getApplicationContext(),
                R.layout.econ_entry_adapter,
                list);
        ListView lView = (ListView) findViewById(R.id.entry_list);
        lView.setAdapter(econListAdapter);
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
        update();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_delete_all:
                DataManager.getInstance(getBaseContext()).deleteEntries();
                update();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        list.clear();
        list.addAll(DataManager.getInstance(getBaseContext()).getEntries());
        econListAdapter.notifyDataSetChanged();

        ((TextView)findViewById(R.id.dist_unit)).setText(DistanceUnits.getSystemUnit(getBaseContext()));
        ((TextView)findViewById(R.id.fuel_unit)).setText(FuelUnits.getSystemUnit(getBaseContext()));
        ((TextView)findViewById(R.id.econ_unit)).setText(getResources().getString(R.string.econ_total_units,
                DistanceUnits.getSystemUnit(getBaseContext()),
                FuelUnits.getSystemUnit(getBaseContext())));

        double dTotal = 0;
        double fTotal = 0;
        for (EconEntry entry: list) {
            dTotal += entry.getDistanceCount(DistanceUnits.getSystemRatio(getBaseContext()));
            fTotal += entry.getFuelCount(FuelUnits.getSystemRatio(getBaseContext()));
        }
        double eAvg;
        if (fTotal > 0)
            eAvg = dTotal / fTotal;
        else
            eAvg = 0;

        ((TextView)findViewById(R.id.dist)).setText(Double.toString(dTotal));
        ((TextView)findViewById(R.id.fuel_data)).setText(Double.toString(fTotal));
        ((TextView)findViewById(R.id.econ_data)).setText(Double.toString(eAvg));
        //TODO update the totals
    }
}
