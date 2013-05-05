package nl.milanboers.recordcollector.master.fragments;

import java.util.ArrayList;
import java.util.List;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleArtist;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.image.ImageActivity;
import nl.milanboers.recordcollector.master.ImageLoadTask;
import nl.milanboers.recordcollector.tabs.TabFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MasterRecordFragment extends TabFragment {
	@SuppressWarnings("unused")
	private static String TAG = "MasterAFragment";
	
	private DiscogsSimpleMaster simpleMaster;
	private DiscogsMaster master;
	
	private TextView titleView;
	private ImageView thumbView;
	private TextView artistView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_master_record, container, false);
		
		this.simpleMaster = getArguments().getParcelable("simpleMaster");
		
		this.titleView = (TextView) v.findViewById(R.id.master_title);
		this.thumbView = (ImageView) v.findViewById(R.id.master_thumb);
		this.artistView = (TextView) v.findViewById(R.id.master_artist);
		
		this.titleView.setText(this.simpleMaster.title);
		
		// Load the image
		ImageLoadTask task = new ImageLoadTask(this.thumbView);
		task.execute(this.simpleMaster.thumb);
		
		// Make sure clicking on the image opens the popup image
		this.thumbView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ImageActivity.class);
				// Load the normal image if it's there, otherwise load the thumb.
				if(MasterRecordFragment.this.master != null && 
				   MasterRecordFragment.this.master.images != null &&
				   MasterRecordFragment.this.master.images.size() > 0)
					intent.putExtra("image", MasterRecordFragment.this.master.images.get(0).uri);
				else
					intent.putExtra("image", MasterRecordFragment.this.simpleMaster.thumb);
				startActivity(intent);
			}
		});
		
		return v;
	}
	
	public void setMaster(DiscogsMaster master) {
		this.master = master;
		
		// Title
		this.titleView.setText(this.master.title);
		
		// Artist(s)
		List<String> artistNames = new ArrayList<String>();
		for(DiscogsSimpleArtist artist : this.master.artists) {
			artistNames.add(artist.name);
		}
		this.artistView.setText(TextUtils.join(", ", artistNames));
	}

	@Override
	public int getNameId() {
		return R.string.record;
	}
}
