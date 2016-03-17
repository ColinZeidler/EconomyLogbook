package apps.czeidler.economylogboook.mainactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import apps.czeidler.economylogboook.R;
import apps.czeidler.economylogboook.data.DataManager;
import apps.czeidler.economylogboook.data.DistanceUnits;
import apps.czeidler.economylogboook.data.EconEntry;
import apps.czeidler.economylogboook.data.FuelUnits;

/**
 * Created by Colin on 2016-03-13.
 * Fragment to display the graphs generated from the entry data
 * See https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data for creation of Chart data
 */
public class GraphFragment extends Fragment {

    private Context mContext;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
//        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
//        fab.hide();

        ArrayList<EconEntry> model = DataManager.getInstance(mContext).getEntries();
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis(), 10, 1, 200, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-60480000, 15, 1, 300, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-(60480000 * 2), 11, 1, 274, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-(60480000 * 3), 11.3, 1, 234, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-(60480000 * 4), 26, 1, 543, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-(60480000 * 5), 42, 1, 598, 1));
        model.add(new EconEntry(Calendar.getInstance().getTimeInMillis()-(60480000 * 6), 30, 1, 510, 1));
        //setup LineCharts
        LineChart distChart = (LineChart) rootView.findViewById(R.id.distance_chart);
        LineChart fuelChart = (LineChart) rootView.findViewById(R.id.fuel_chart);
        LineChart econChart = (LineChart) rootView.findViewById(R.id.econ_chart);
        //Create Entry arrays
        ArrayList<Entry> distanceValues = new ArrayList<>();
        ArrayList<Entry> fuelValues = new ArrayList<>();
        ArrayList<Entry> econValues = new ArrayList<>();
        //Create horizontal axis
        ArrayList<String> dates = new ArrayList<>();
        //Add data to Arrays
        for (int i = 0; i < model.size(); i++) {
            float dist = (float) model.get(i).getDistanceCount(DistanceUnits.getSystemRatio(mContext));
            float fuel = (float) model.get(i).getFuelCount(FuelUnits.getSystemRatio(mContext));
            float econ = (float) model.get(i).getEconomy(DistanceUnits.getSystemRatio(mContext),
                    FuelUnits.getSystemRatio(mContext));
            distanceValues.add(new Entry(dist, i));
            fuelValues.add(new Entry(fuel, i));
            econValues.add(new Entry(econ, i));
            dates.add(model.get(i).getDateString());
        }
        //
        LineDataSet distSet = new LineDataSet(distanceValues, "Distance");
        LineDataSet fuelSet = new LineDataSet(fuelValues, "Fuel");
        LineDataSet econSet = new LineDataSet(econValues, "Economy");
        distSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        fuelSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        econSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //
        ArrayList<ILineDataSet> distDataSets = new ArrayList<>();
        ArrayList<ILineDataSet> fuelDataSets = new ArrayList<>();
        ArrayList<ILineDataSet> econDataSets = new ArrayList<>();
        distDataSets.add(distSet);
        fuelDataSets.add(fuelSet);
        econDataSets.add(econSet);
        //
        LineData distData = new LineData(dates, distDataSets);
        LineData fuelData = new LineData(dates, fuelDataSets);
        LineData econData = new LineData(dates, econDataSets);

        distChart.setData(distData);
        distChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        distChart.setDescription("");
        distChart.invalidate();
        fuelChart.setData(fuelData);
        fuelChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        fuelChart.setDescription("");
        fuelChart.invalidate();
        econChart.setData(econData);
        econChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        econChart.setDescription("");
        econChart.invalidate();

        return rootView;
    }
}
