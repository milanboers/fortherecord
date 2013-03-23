package nl.milanboers.recordcollector.search;

import java.util.ArrayList;
import java.util.List;


import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsMasterResult;
import nl.milanboers.recordcollector.discogs.DiscogsMasterAdapter;
import nl.milanboers.recordcollector.views.LoadingListView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends Activity {
	@SuppressWarnings("unused")
	private static final String TAG = "SearchActivity";
	
	private Button searchButton;
	private EditText searchField;
	private LoadingListView recordList;
	private SearchLoader searchLoader;
	
	private DiscogsMasterAdapter recordAdapter;
	
	private List<DiscogsMasterResult> results = new ArrayList<DiscogsMasterResult>();;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Setup loading icon in ActionBar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_search);
        
		// Setup the SearchLoader that manager the search work
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
			public void onSearchCompleted(List<DiscogsMasterResult> results) {
				if(results != null) {
					// Add the results
					SearchActivity.this.results.addAll(results);
					SearchActivity.this.recordAdapter.notifyDataSetChanged();
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
        this.recordAdapter = new DiscogsMasterAdapter(SearchActivity.this, this.results);
		this.recordList.setAdapter(this.recordAdapter);

		// Search field and button
        this.searchField = (EditText) findViewById(R.id.search_searchField);
        this.searchButton = (Button) findViewById(R.id.search_searchButton);
        this.searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clear the results
				SearchActivity.this.results.clear();
				SearchActivity.this.recordAdapter.notifyDataSetChanged();
				
				// Change the query
				SearchActivity.this.searchLoader.setQuery(SearchActivity.this.searchField.getText().toString());
				
				// And go
				SearchActivity.this.recordList.reset();
				SearchActivity.this.recordList.loadNext();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
