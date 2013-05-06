package nl.milanboers.recordcollector.master;

import java.io.IOException;
import java.util.Arrays;

import com.actionbarsherlock.view.Menu;
import com.google.gson.JsonSyntaxException;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsMaster;
import nl.milanboers.recordcollector.errors.ErrorShower;
import nl.milanboers.recordcollector.errors.ErrorType;
import nl.milanboers.recordcollector.master.fragments.MasterRecordFragment;
import nl.milanboers.recordcollector.master.fragments.MasterTracklistFragment;
import nl.milanboers.recordcollector.tabs.TabsActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class MasterActivity extends TabsActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "RecordActivity";
	
	private int id;
	
	// Fragments
	private MasterRecordFragment recordFragment;
	private MasterTracklistFragment tracklistFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.id = getIntent().getIntExtra("id", -1);
		
		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_master);
		
		setupTabs();
		
		// Load the detailed master
		setProgressBarIndeterminateVisibility(true);
		AsyncTask<Void, Void, DiscogsMaster> masterLoadTask = new MasterLoadTask();
		masterLoadTask.execute();
	}
	
	private void setupTabs() {
		// Setup the tabs on the page
		
		// Record fragment
		this.recordFragment = new MasterRecordFragment();
		// Track listing fragment
		this.tracklistFragment = new MasterTracklistFragment();
		
		setupTabs(Arrays.asList(this.recordFragment, this.tracklistFragment), R.id.master_pager);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.master, menu);
		return true;
	}
	
	private class MasterLoadTask extends AsyncTask<Void, Void, DiscogsMaster> {
		private ErrorType error;
		
		@Override
		protected DiscogsMaster doInBackground(Void... params) {
			try {
				return Discogs.getInstance().master(MasterActivity.this.id);
			} catch (JsonSyntaxException e) {
				this.error = ErrorType.JSON;
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				this.error = ErrorType.IO;
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(DiscogsMaster master) {
			if(master == null) {
				// Error
				ErrorShower.showError(this.error, MasterActivity.this);
				return;
			}
			// Success!
			setProgressBarIndeterminateVisibility(false);
			
			MasterActivity.this.recordFragment.setMaster(master);
			MasterActivity.this.tracklistFragment.setTracklist(master.tracklist);
		}
	}
}
