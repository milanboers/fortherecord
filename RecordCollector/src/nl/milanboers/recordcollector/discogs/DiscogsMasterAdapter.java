package nl.milanboers.recordcollector.discogs;

import java.util.List;

import nl.milanboers.recordcollector.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiscogsMasterAdapter extends ArrayAdapter<DiscogsMasterResult> {
	private List<DiscogsMasterResult> results;
	
	public DiscogsMasterAdapter(Context context, List<DiscogsMasterResult> results) {
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
		
		DiscogsMasterResult result = this.results.get(position);
		if(result != null) {
			// Fill the widgets
			TextView title = (TextView) v.findViewById(R.id.record_item_title);
			title.setText(result.title);
		}
		return v;
	}

}
