package nl.milanboers.recordcollector.search;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMasterAdapter;
import nl.milanboers.recordcollector.master.MasterActivity;
import nl.milanboers.recordcollector.views.LoadingListView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

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
		this.searchLoader = new SearchLoader();
		this.searchLoader.setOnSearchStartedListener(new SearchLoader.OnSearchStartedListener() {
			@Override
			public void onSearchStarted() {
				// Set UI
				setProgressBarIndeterminateVisibility(true);
			}
		});
		this.searchLoader.setOnSearchCompletedListener(new SearchLoader.OnSearchCompletedListener() {
			@Override
			public void onSearchCompleted(List<DiscogsSimpleMaster> results) {
				if(results != null) {
					// Add the results
					SearchActivity.this.results.addAll(results);
					SearchActivity.this.resultsAdapter.notifyDataSetChanged();
					// Set UI
					setProgressBarIndeterminateVisibility(false);
				}
				else
				{
					// Error occurred
					Toast.makeText(SearchActivity.this, R.string.error1, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		// Setup ListView of the search results
		this.recordList = (LoadingListView) findViewById(R.id.record_list);
		this.recordList.setLoader(this.searchLoader);
		this.recordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos,
					long id) {
				// Start the RecordActivity
				Intent intent = new Intent(SearchActivity.this, MasterActivity.class);
				intent.putExtra("master", SearchActivity.this.results.get(pos));
				startActivity(intent);
			}
		});
		this.resultsAdapter = new DiscogsSimpleMasterAdapter(SearchActivity.this, this.results);
		this.recordList.setAdapter(this.resultsAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.search, menu);
		
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
