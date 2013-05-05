package nl.milanboers.recordcollector.master;

import java.util.Arrays;

import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.master.fragments.MasterRecordFragment;
import nl.milanboers.recordcollector.master.fragments.MasterTracklistFragment;
import nl.milanboers.recordcollector.tabs.TabsActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class MasterActivity extends TabsActivity implements TabListener {
	@SuppressWarnings("unused")
	private static final String TAG = "RecordActivity";
	
	private DiscogsSimpleMaster simpleMaster;
	
	// Fragments
	private MasterRecordFragment recordFragment;
	private MasterTracklistFragment tracklistFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.simpleMaster = getIntent().getParcelableExtra("master");
		
		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_master);
		
		setupTabs();
		
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
				
				MasterActivity.this.recordFragment.setMaster(master);
				MasterActivity.this.tracklistFragment.setTracklist(master.tracklist);
			}
		};
		masterLoadTask.execute();
	}
	
	private void setupTabs() {
		// Setup the tabs on the page
		
		// Record fragment
		this.recordFragment = new MasterRecordFragment();
		// Pass the simpleMaster to the recordFragment
		Bundle args = new Bundle();
		args.putParcelable("simpleMaster", this.simpleMaster);
		recordFragment.setArguments(args);
		
		// Track listing fragment
		this.tracklistFragment = new MasterTracklistFragment();
		
		setupTabs(Arrays.asList(this.recordFragment, this.tracklistFragment), R.id.master_pager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.record, menu);
		return true;
	}
}
