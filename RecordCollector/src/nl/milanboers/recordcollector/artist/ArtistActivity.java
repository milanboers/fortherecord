package nl.milanboers.recordcollector.artist;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.ClientProtocolException;

import nl.milanboers.recordcollector.ErrorShower;
import nl.milanboers.recordcollector.ErrorType;
import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.artist.fragments.ArtistProfileFragment;
import nl.milanboers.recordcollector.artist.fragments.ArtistRecordsFragment;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsArtist;
import nl.milanboers.recordcollector.tabs.TabsActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

import com.actionbarsherlock.view.Menu;

public class ArtistActivity extends TabsActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "RecordActivity";
	
	private int id;
	
	private ArtistProfileFragment profileFragment;
	private ArtistRecordsFragment recordsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.id = getIntent().getIntExtra("id", -1);
		
		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_artist);
		
		setupTabs();
		
		// Load the detailed artist
		setProgressBarIndeterminateVisibility(true);
		AsyncTask<Void, Void, DiscogsArtist> artistLoadTask = new ArtistLoadTask();
		artistLoadTask.execute();
	}
	
	private void setupTabs() {
		// Setup the tabs on the page
		this.profileFragment = new ArtistProfileFragment();
		this.recordsFragment = new ArtistRecordsFragment();
		
		setupTabs(Arrays.asList(this.profileFragment, this.recordsFragment), R.id.artist_pager);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.artist, menu);
		return true;
	}
	
	private class ArtistLoadTask extends AsyncTask<Void, Void, DiscogsArtist> {
		private ErrorType error;
		
		@Override
		protected DiscogsArtist doInBackground(Void... params) {
			try {
				return Discogs.getInstance().artist(ArtistActivity.this.id);
			} catch (ClientProtocolException e) {
				this.error = ErrorType.PROTOCOL;
				return null;
			} catch (IOException e) {
				this.error = ErrorType.IO;
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(DiscogsArtist artist) {
			if(artist == null) {
				// Error
				ErrorShower.showError(this.error, ArtistActivity.this);
				return;
			}
			// Success!
			setProgressBarIndeterminateVisibility(false);
			
			ArtistActivity.this.profileFragment.setProfile(artist);
		}
	}

}
