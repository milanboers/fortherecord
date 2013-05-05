package nl.milanboers.recordcollector.master.fragments;

import java.util.List;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsTrack;
import nl.milanboers.recordcollector.tabs.TabFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MasterTracklistFragment extends TabFragment {
	private LinearLayout tracklistLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_master_tracklist, container, false);
		
		this.tracklistLayout = (LinearLayout) v.findViewById(R.id.master_tracklist);
		
		return v;
	}
	
	public void setTracklist(List<DiscogsTrack> tracklist) {
		for(DiscogsTrack track : tracklist) {
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.row_track, null);
			
			TextView positionView = (TextView) row.findViewById(R.id.row_track_pos);
			TextView titleView = (TextView) row.findViewById(R.id.row_track_title);
			TextView durationView = (TextView) row.findViewById(R.id.row_track_duration);
			
			positionView.setText(track.position);
			titleView.setText(track.title);
			durationView.setText(track.duration);
			
			this.tracklistLayout.addView(row);
		}
	}

	@Override
	public int getNameId() {
		return R.string.track_listing;
	}
}
