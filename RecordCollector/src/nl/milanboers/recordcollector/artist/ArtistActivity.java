/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.artist;

import java.io.IOException;
import java.util.Arrays;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.artist.fragments.ArtistProfileFragment;
import nl.milanboers.recordcollector.artist.fragments.ArtistRecordsFragment;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsArtist;
import nl.milanboers.recordcollector.errors.ErrorShower;
import nl.milanboers.recordcollector.errors.ErrorType;
import nl.milanboers.recordcollector.persistence.Persister;
import nl.milanboers.recordcollector.tabs.TabsActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.JsonSyntaxException;

public class ArtistActivity extends TabsActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "RecordActivity";

	private int id;
	private DiscogsArtist artist;

	private ArtistProfileFragment profileFragment;
	private ArtistRecordsFragment recordsFragment;

	private Persister persister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.id = getIntent().getIntExtra("id", -1);
		this.persister = new Persister(this);

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

	/**
	 * Favorites or unfavorites the current artist
	 */
	private void toggleFav(MenuItem item) {
		if(this.persister.isFavArtist(this.id)) {
			// Unfav
			this.persister.unfavArtist(this.id);
			item.setIcon(android.R.drawable.btn_star_big_off);
			//
			Log.v(TAG, "Artist with id " + this.id + " unfavorited");
		}
		else if(this.artist != null)
		{
			// Fav
			this.persister.favArtist(this.artist.id, this.artist.name);
			item.setIcon(android.R.drawable.btn_star_big_on);
			//
			Log.v(TAG, "Artist with id " + this.id + " favorited");
		}
		else
		{
			// Don't do anything if the full artist isn't yet known
			ErrorShower.showError(ErrorType.NOTREADY, this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.artist, menu);

		// Set fav icon
		if(this.persister.isFavArtist(this.id)) {
			menu.findItem(R.id.artist_fav).setIcon(android.R.drawable.btn_star_big_on);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.artist_fav:
				toggleFav(item);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class ArtistLoadTask extends AsyncTask<Void, Void, DiscogsArtist> {
		private ErrorType error;

		@Override
		protected DiscogsArtist doInBackground(Void... params) {
			Log.v(TAG, "Loading artist " + ArtistActivity.this.id);
			try {
				return Discogs.getInstance().artist(ArtistActivity.this.id);
			} catch (JsonSyntaxException e) {
				this.error = ErrorType.JSON;
				e.printStackTrace();
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
			ArtistActivity.this.artist = artist;
			setProgressBarIndeterminateVisibility(false);

			ArtistActivity.this.profileFragment.setProfile(artist);
		}
	}

}
