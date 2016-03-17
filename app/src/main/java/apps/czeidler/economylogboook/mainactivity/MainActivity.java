package apps.czeidler.economylogboook.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import apps.czeidler.economylogboook.EntryCreate;
import apps.czeidler.economylogboook.R;
import apps.czeidler.economylogboook.SettingsActivity;
import apps.czeidler.economylogboook.data.DataManager;

/**
 * Created by Colin on 2016-03-15.
 * depending on display size, either manage a PagerAdapter or Two Fragments
 */
public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mPagerAdapter;

    boolean pages;
    private ViewPager mViewPager;

    private EntryListFragment entryListFragment;
    private GraphFragment graphFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        pages = mViewPager != null;
        if (savedInstanceState == null) {
            if (pages) {
                Log.d("Main", "using tabs");
                //act like a view pager
                mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mPagerAdapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
                tabLayout.setupWithViewPager(mViewPager);
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            } else {
                //both views visible
                Log.d("Main", "using multiple fragments");
                if (graphFragment == null)
                    graphFragment = new GraphFragment();
                if (entryListFragment == null)
                    entryListFragment = new EntryListFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_econ_list,
                        entryListFragment, "entryList").add(R.id.fragment_graphs,
                        graphFragment, "graphs").commit();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), EntryCreate.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entry_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_delete_all:
                DataManager.getInstance(getBaseContext()).deleteEntries();
                entryListFragment.update();
                graphFragment.update();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (entryListFragment == null)
                        entryListFragment = new EntryListFragment();
                    return entryListFragment;
                case 1:
                    if (graphFragment == null)
                        graphFragment = new GraphFragment();
                    return graphFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            //Total of 2 pages, economy list and graphs
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.title_section0);
                case 1:
                    return getString(R.string.title_section1);
            }
            return null;
        }
    }

    private void update() {

    }
}
