package nl.milanboers.recordcollector.search;

import java.util.List;

import com.google.common.base.Joiner;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiscogsSimpleMasterAdapter extends ArrayAdapter<DiscogsSimpleMaster> {
	private static final String TAG = "DiscogsSimpleMasterAdapter";
	
	private List<DiscogsSimpleMaster> results;
	
	public DiscogsSimpleMasterAdapter(Context context, List<DiscogsSimpleMaster> results) {
		super(context, R.layout.list_record_item, results);
		
		this.results = results;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null)
		{
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.list_record_item, null);
		}
		
		DiscogsSimpleMaster result = this.results.get(position);
		if(result != null) {
			// Fill the widgets
			TextView title = (TextView) v.findViewById(R.id.record_item_title);
			TextView year = (TextView) v.findViewById(R.id.record_item_year);
			TextView genres = (TextView) v.findViewById(R.id.record_item_genres);
			
			// Title
			title.setText(result.title);
			// Year
			year.setText(result.year);
			// Genres
			if(result.genre == null)
				Log.v(TAG, "NULL");
			genres.setText(Joiner.on(", ").join(result.genre));
		}
		return v;
	}
}
