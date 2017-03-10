/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.search;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.errors.ErrorShower;
import nl.milanboers.recordcollector.errors.ErrorType;
import nl.milanboers.recordcollector.master.MasterActivity;
import nl.milanboers.recordcollector.views.LoadingListView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

public class SearchActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "SearchActivity";

	// Components
	private SearchView menuSearch;
	private LoadingListView recordList;
	private SearchLoader searchLoader;

	private DiscogsSimpleMasterAdapter resultsAdapter;
	private List<DiscogsSimpleMaster> results = new ArrayList<DiscogsSimpleMaster>();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_search);

		// Setup the SearchLoader that manages the search work
		setupSearchLoader();

		// Setup ListView of the search results
		setupUI();
	}

	private void setupUI() {
		this.recordList = (LoadingListView) findViewById(R.id.record_list);
		this.recordList.setLoader(this.searchLoader);
		this.recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
				// Start the MasterActivity when an item is clicked
				Intent intent = new Intent(SearchActivity.this, MasterActivity.class);
				intent.putExtra("id", SearchActivity.this.results.get(pos).id);
				startActivity(intent);
			}
		});
		this.resultsAdapter = new DiscogsSimpleMasterAdapter(this, this.results);
		this.recordList.setAdapter(this.resultsAdapter);
	}

	private void setupSearchLoader() {
		this.searchLoader = new SearchLoader();
		this.searchLoader.setOnSearchStartedListener(new SearchLoader.OnSearchStartedListener() {
			@Override
			public void onSearchStarted() {
				// Turn progress icon on
				setProgressBarIndeterminateVisibility(true);
			}
		});
		this.searchLoader.setOnSearchCompletedListener(new SearchLoader.OnSearchCompletedListener() {
			@Override
			public void onSearchCompleted(List<DiscogsSimpleMaster> results) {
				// Add the results
				SearchActivity.this.results.addAll(results);
				SearchActivity.this.resultsAdapter.notifyDataSetChanged();
				// Turn progress icon off
				setProgressBarIndeterminateVisibility(false);
			}

			@Override
			public void onError(ErrorType error) {
				ErrorShower.showError(error, SearchActivity.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.search, menu);

		// Search item in the action bar
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		this.menuSearch = (SearchView) searchItem.getActionView();

		this.menuSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {

				// Clear the results
				SearchActivity.this.results.clear();
				SearchActivity.this.resultsAdapter.notifyDataSetChanged();

				// Change the query
				SearchActivity.this.searchLoader.setQuery(query);

				// And go
				SearchActivity.this.recordList.reset();
				SearchActivity.this.recordList.loadNext();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		return true;
	}

}
