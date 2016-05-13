package vn.brine.spotifymusicchannel.search.track;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Joiner;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import vn.brine.spotifymusicchannel.R;
import vn.brine.spotifymusicchannel.spotifyapi.models.ArtistSimple;
import vn.brine.spotifymusicchannel.spotifyapi.models.Image;
import vn.brine.spotifymusicchannel.spotifyapi.models.Track;

/**
 * Created by HaiLeader on 13-May-16.
 */
@EViewGroup(R.layout.list_track_item)
public class TrackItemView extends LinearLayout{

    @ViewById(R.id.entity_image)
    ImageView image;

    @ViewById(R.id.entity_title)
    TextView title;

    @ViewById(R.id.entity_subtitle)
    TextView subtitle;

    public TrackItemView(Context context){
        super(context);
    }

    public void bind(Track track){
        title.setText(track.name);

        List<String> names = new ArrayList<>();
        for (ArtistSimple i : track.artists) {
            names.add(i.name);
        }
        Joiner joiner = Joiner.on(", ");
        subtitle.setText(joiner.join(names));

        Image imageView = track.album.images.get(0);
        if (imageView != null) {
            Picasso.with(getContext()).load(imageView.url).into(image);
        }
    }
}
