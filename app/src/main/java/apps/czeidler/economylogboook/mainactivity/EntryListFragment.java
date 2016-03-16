package apps.czeidler.economylogboook.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import apps.czeidler.economylogboook.EconListAdapter;
import apps.czeidler.economylogboook.EntryCreate;
import apps.czeidler.economylogboook.R;
import apps.czeidler.economylogboook.data.DataManager;
import apps.czeidler.economylogboook.data.DistanceUnits;
import apps.czeidler.economylogboook.data.EconEntry;
import apps.czeidler.economylogboook.data.FuelUnits;

/**
 * Created by Colin on 2016-03-13.
 * Fragment to display Economy list elements
 */
public class EntryListFragment extends Fragment {
    private EconListAdapter econListAdapter;
    private ArrayList<EconEntry> list;
    private Context mContext;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_econ_list, container, false);

//        FloatingActionButton fab = (FloatingActionButton) container.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(mContext, EntryCreate.class);
//                startActivity(intent);
//            }
//        });

        ((TextView)rootView.findViewById(R.id.dist_unit)).setText(DistanceUnits.getSystemUnit(mContext));
        ((TextView)rootView.findViewById(R.id.fuel_unit)).setText(FuelUnits.getSystemUnit(mContext));
        ((TextView)rootView.findViewById(R.id.econ_unit)).setText(getResources().getString(R.string.econ_total_units,
                DistanceUnits.getSystemUnit(mContext),
                FuelUnits.getSystemUnit(mContext)));

        ((TextView)rootView.findViewById(R.id.dist)).setText("0");
        ((TextView)rootView.findViewById(R.id.fuel_data)).setText("0");
        ((TextView)rootView.findViewById(R.id.econ_data)).setText("0");

        list = new ArrayList<>();
        econListAdapter = new EconListAdapter(mContext,
                R.layout.econ_entry_adapter,
                list);
        ListView lView = (ListView) rootView.findViewById(R.id.entry_list);
        lView.setAdapter(econListAdapter);
        update();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    public void update() {
        Log.i("EntryFragment", "Updating");
        //Update List
        list.clear();
        list.addAll(DataManager.getInstance(mContext).getEntries());
        econListAdapter.notifyDataSetChanged();

        ((TextView)rootView.findViewById(R.id.dist_unit)).setText(DistanceUnits.getSystemUnit(mContext));
        ((TextView)rootView.findViewById(R.id.fuel_unit)).setText(FuelUnits.getSystemUnit(mContext));
        ((TextView)rootView.findViewById(R.id.econ_unit)).setText(getResources().getString(R.string.econ_total_units,
                DistanceUnits.getSystemUnit(mContext),
                FuelUnits.getSystemUnit(mContext)));


        //Update Totals
        double dTotal = 0;
        double fTotal = 0;
        for (EconEntry entry: list) {
            dTotal += entry.getDistanceCount(DistanceUnits.getSystemRatio(mContext));
            fTotal += entry.getFuelCount(FuelUnits.getSystemRatio(mContext));
        }
        double eAvg;
        if (fTotal > 0)
            eAvg = dTotal / fTotal;
        else
            eAvg = 0;

        ((TextView)rootView.findViewById(R.id.dist)).setText(Double.toString(dTotal));
        ((TextView)rootView.findViewById(R.id.fuel_data)).setText(Double.toString(fTotal));
        ((TextView)rootView.findViewById(R.id.econ_data)).setText(Double.toString(eAvg));
    }
    
}
