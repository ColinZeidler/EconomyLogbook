package apps.czeidler.economylogboook;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import apps.czeidler.economylogboook.data.DistanceUnits;
import apps.czeidler.economylogboook.data.EconEntry;
import apps.czeidler.economylogboook.data.FuelUnits;

/**
 * Created by Colin on 2016-02-29.
 * adapter to list Economy entries
 */
public class EconListAdapter extends ArrayAdapter<EconEntry> {

    private Context context;
    private int layoutID;
    private List<EconEntry> entries;

    public EconListAdapter(Context context, int resource, List<EconEntry> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.entries = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(layoutID, null);
        }

        //TODO set values of fields in layout
        TextView dist_text = (TextView) v.findViewById(R.id.adapter_dist);
        TextView fuel_text = (TextView) v.findViewById(R.id.adapter_fuel);
        TextView date_text = (TextView) v.findViewById(R.id.adapter_date);

        EconEntry entry = entries.get(position);
        if (entry != null) {

            dist_text.setText(String.format("%.2f %s", entry.getDistanceCount(DistanceUnits.getSystemRatio(v.getContext())),
                    DistanceUnits.getSystemUnit(v.getContext())));
            fuel_text.setText(String.format("%.2f %s", entry.getFuelCount(FuelUnits.getSystemRatio(v.getContext())),
                    FuelUnits.getSystemUnit(v.getContext())));
            date_text.setText(entry.getDateString());
        }

        return v;
    }
}
