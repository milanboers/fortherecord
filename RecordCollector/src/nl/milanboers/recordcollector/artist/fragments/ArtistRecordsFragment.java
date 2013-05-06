package nl.milanboers.recordcollector.artist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.tabs.TabFragment;

public class ArtistRecordsFragment extends TabFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_artist_records, container, false);
		return v;
	}
	
	@Override
	public int getNameId() {
		return R.string.records;
	}

}
