package vn.brine.spotifymusicchannel.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import vn.brine.spotifymusicchannel.R;
import vn.brine.spotifymusicchannel.fragment.FragmentDrawer;
import vn.brine.spotifymusicchannel.fragment.SettingFragment;
import vn.brine.spotifymusicchannel.fragment.TrackFragment_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    public static final String EXTRA_TOKEN = "EXTRA_TOKEN";

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    @FragmentById(R.id.fragment_navigation_drawer)
    FragmentDrawer drawerFragment;

    @FragmentByTag("Tracks")
    SettingFragment settingFragment;

    @AfterViews
    void viewLayout(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrackFragment_(), "Tracks");
        adapter.addFragment(new SettingFragment(), "Playlists");
        adapter.addFragment(new SettingFragment(), "Groups");
        viewPager.setAdapter(adapter);
    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity_.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Add action view (SearchView) to ActionBar
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Searching" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position){
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position){
            case 0:
                title = getString(R.string.nav_item_search);
                break;
            case 1:
                title = getString(R.string.nav_item_favorite);
                break;
            case 2:
                title = getString(R.string.nav_item_download);
                break;
            case 3:
                fragment = new SettingFragment();
                title = getString(R.string.nav_item_setting);
                break;
            case 4:
                title = getString(R.string.nav_item_exit);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }

        Toast.makeText(getApplicationContext(), "Goto Fragment: " + title, Toast.LENGTH_SHORT).show();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}