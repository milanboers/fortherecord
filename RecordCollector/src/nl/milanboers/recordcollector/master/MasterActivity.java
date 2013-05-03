package nl.milanboers.recordcollector.master;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleArtist;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.discogs.DiscogsTrack;
import nl.milanboers.recordcollector.image.ImageActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MasterActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "RecordActivity";
	
	private DiscogsSimpleMaster simpleMaster;
	private DiscogsMaster master;
	
	private ImageView thumbView;
	private TextView titleView;
	private TextView artistView;
	private TableLayout tracklistLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_master);
		
		this.simpleMaster = getIntent().getParcelableExtra("master");
		
		this.thumbView = (ImageView) findViewById(R.id.master_thumb);
		this.titleView = (TextView) findViewById(R.id.master_title);
		this.artistView = (TextView) findViewById(R.id.master_artist);
		this.tracklistLayout = (TableLayout) findViewById(R.id.master_tracklist);
		
		this.titleView.setText(this.simpleMaster.title);
		
		ImageLoadTask task = new ImageLoadTask(this.thumbView);
		task.execute(this.simpleMaster.thumb);
		
		// Make sure clicking on the image opens the popup image
		this.thumbView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MasterActivity.this, ImageActivity.class);
				// Load the normal image if it's there, otherwise load the thumb.
				if(MasterActivity.this.master.images != null && MasterActivity.this.master.images.size() > 0)
					intent.putExtra("image", MasterActivity.this.master.images.get(0).uri);
				else
					intent.putExtra("image", MasterActivity.this.simpleMaster.thumb);
				startActivity(intent);
			}
		});
		
		// Load the detailed master
		setProgressBarIndeterminateVisibility(true);
		AsyncTask<Void, Void, DiscogsMaster> masterLoadTask = new AsyncTask<Void, Void, DiscogsMaster>() {
			@Override
			protected DiscogsMaster doInBackground(Void... params) {
				try {
					return Discogs.getInstance().master(MasterActivity.this.simpleMaster.id);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(DiscogsMaster master) {
				setProgressBarIndeterminateVisibility(false);
				
				MasterActivity.this.master = master;
				MasterActivity.this.loadMaster();
			}
		};
		masterLoadTask.execute();
	}
	
	private void loadMaster() {
		if(this.master == null)
			return;
		
		// Title
		this.titleView.setText(this.master.title);
		
		// Artist(s)
		List<String> artistNames = new ArrayList<String>();
		for(DiscogsSimpleArtist artist : this.master.artists) {
			artistNames.add(artist.name);
		}
		this.artistView.setText(TextUtils.join(", ", artistNames));
		
		// Tracklist
		for(DiscogsTrack track : this.master.tracklist) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.record, menu);
		return true;
	}

}
