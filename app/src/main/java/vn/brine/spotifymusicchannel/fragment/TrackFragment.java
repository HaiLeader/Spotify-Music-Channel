package vn.brine.spotifymusicchannel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import vn.brine.spotifymusicchannel.R;
import vn.brine.spotifymusicchannel.activity.MainActivity_;
import vn.brine.spotifymusicchannel.search.ResultListScrollListener;
import vn.brine.spotifymusicchannel.search.Search;
import vn.brine.spotifymusicchannel.search.SearchPresenter;
import vn.brine.spotifymusicchannel.search.SearchResultsAdapter;
import vn.brine.spotifymusicchannel.spotifyapi.models.Track;

@EFragment(R.layout.fragment_track)
public class TrackFragment extends Fragment implements Search.View{

    private static final String KEY_CURRENT_QUERY = "CURRENT_QUERY";

    @ViewById(R.id.search_track_results)
    RecyclerView resultsList;

    private Search.ActionListener mActionListener;

    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
    private ScrollListener mScrollListener = new ScrollListener(mLayoutManager);
    private SearchResultsAdapter mAdapter;

    private class ScrollListener extends ResultListScrollListener{
        public ScrollListener(LinearLayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        public void onLoadMore() {
            mActionListener.loadMoreResults();
        }
    }

    @AfterViews
    void searchTracks(){
        Toast.makeText(getContext(), "Lay du lieu", Toast.LENGTH_SHORT).show();

        String token = getActivity().getIntent().getStringExtra(MainActivity_.EXTRA_TOKEN);

        mActionListener = new SearchPresenter(getActivity(), this);
        mActionListener.init(token);

        //TODO: Search tracks
        mActionListener.search("Kiss");

        mAdapter = new SearchResultsAdapter(getContext(), new SearchResultsAdapter.ItemSelectedListener() {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            String currentQuery = savedInstanceState.getString(KEY_CURRENT_QUERY);
            mActionListener.search(currentQuery);
        }
    }

    @Override
    public void reset() {
        mScrollListener.reset();
        if(mAdapter != null)
            mAdapter.clearData();
    }

    @Override
    public void addData(List<Track> items) {
        mAdapter.addData(items);
    }

    @Override
    public void onPause() {
        super.onPause();
        mActionListener.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mActionListener.resume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mActionListener.getCurrentQuery() != null){
            outState.putString(KEY_CURRENT_QUERY, mActionListener.getCurrentQuery());
        }
    }

    @Override
    public void onDetach() {
        mActionListener.destroy();
        super.onDetach();
    }
}
