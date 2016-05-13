package vn.brine.spotifymusicchannel.search.track;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import vn.brine.spotifymusicchannel.spotifyapi.models.Track;

/**
 * Created by HaiLeader on 13-May-16.
 */
@EBean
public class TrackAdapter extends RecyclerViewAdapterBase<Track, TrackItemView> {

    @RootContext
    Context context;

    public TrackAdapter() {
    }

    @Override
    protected TrackItemView onCreateItemView(ViewGroup parent, int viewType) {
        return TrackItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<TrackItemView> holder, int position) {
        TrackItemView view = holder.getView();
        Track track = items.get(position);

        view.bind(track);
    }

    public void clearData(){
        items.clear();
    }

    public void addData(List<Track> data){
        items.addAll(data);
        notifyDataSetChanged();
    }

}
