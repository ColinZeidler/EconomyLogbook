package apps.czeidler.economylogboook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Colin on 2016-03-13.
 * Fragment to display the graphs generated from the entry data
 */
public class GraphFragment extends Fragment{

    private Context mContext;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();
        rootView = inflater.inflate(R.layout.fragment_graphs, container, false);
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.hide();

        return rootView;
    }
}
