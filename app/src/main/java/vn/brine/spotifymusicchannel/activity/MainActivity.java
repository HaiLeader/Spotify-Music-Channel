package vn.brine.spotifymusicchannel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import vn.brine.spotifymusicchannel.R;
import vn.brine.spotifymusicchannel.search.ResultListScrollListener;
import vn.brine.spotifymusicchannel.search.Search;
import vn.brine.spotifymusicchannel.search.SearchPresenter;
import vn.brine.spotifymusicchannel.search.SearchResultsAdapter;
import vn.brine.spotifymusicchannel.spotifyapi.models.Track;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements Search.View {

    static final String EXTRA_TOKEN = "EXTRA_TOKEN";
    private static final String KEY_CURRENT_QUERY = "CURRENT_QUERY";

    @ViewById(R.id.search_view)
    SearchView searchView;

    @ViewById(R.id.search_results)
    RecyclerView resultsList;

    private Search.ActionListener mActionListener;

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    private ScrollListener mScrollListener = new ScrollListener(mLayoutManager);
    private SearchResultsAdapter mAdapter;

    private class ScrollListener extends ResultListScrollListener {

        public ScrollListener(LinearLayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        public void onLoadMore() {
            mActionListener.loadMoreResults();
        }
    }

    @AfterViews
    void Search(){
        Intent intent = getIntent();
        String token = intent.getStringExtra(EXTRA_TOKEN);

        mActionListener = new SearchPresenter(this, this);
        mActionListener.init(token);

        // Setup search field
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mActionListener.search(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Setup search results list
        mAdapter = new SearchResultsAdapter(this, new SearchResultsAdapter.ItemSelectedListener() {
            @Override
            public void onItemSelected(View itemView, Track item) {
                mActionListener.selectTrack(item);
            }
        });

        resultsList.setHasFixedSize(true);
        resultsList.setLayoutManager(mLayoutManager);
        resultsList.setAdapter(mAdapter);
        resultsList.addOnScrollListener(mScrollListener);

    }

    public static Intent createIntent(Context context) {
        return new Intent(context, MainActivity_.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // If Activity was recreated wit active search restore it
        if (savedInstanceState != null) {
            String currentQuery = savedInstanceState.getString(KEY_CURRENT_QUERY);
            mActionListener.search(currentQuery);
        }
    }

    @Override
    public void reset() {
        mScrollListener.reset();
        mAdapter.clearData();
    }

    @Override
    public void addData(List<Track> items) {
        mAdapter.addData(items);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActionListener.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionListener.resume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActionListener.getCurrentQuery() != null) {
            outState.putString(KEY_CURRENT_QUERY, mActionListener.getCurrentQuery());
        }
    }

    @Override
    protected void onDestroy() {
        mActionListener.destroy();
        super.onDestroy();
    }

}