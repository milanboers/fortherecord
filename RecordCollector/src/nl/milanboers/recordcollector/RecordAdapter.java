package nl.milanboers.recordcollector;

import java.util.List;

import nl.milanboers.recordcollector.discogs.DiscogsReleasesResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecordAdapter extends ArrayAdapter<DiscogsReleasesResult> {
	private List<DiscogsReleasesResult> results;
	
	public RecordAdapter(Context context, List<DiscogsReleasesResult> results) {
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
		
		DiscogsReleasesResult result = this.results.get(position);
		if(result != null) {
			TextView title = (TextView) v.findViewById(R.id.record_item_title);
			if(title != null) title.setText(result.title);
		}
		return v;
	}

}
