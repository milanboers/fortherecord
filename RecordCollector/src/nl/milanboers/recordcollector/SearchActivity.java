package nl.milanboers.recordcollector;

import java.util.ArrayList;
import java.util.List;

import nl.milanboers.recordcollector.discogs.DiscogsReleasesResult;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	private static final String TAG = "SearchActivity";
	
	private Button searchButton;
	private EditText searchField;
	private ListView recordList;
	
	private int currentPage = 1;
	private String currentQuery;
	
	private RecordAdapter recordAdapter;
	
	private List<DiscogsReleasesResult> results;
	
	boolean searching = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_search);
        
        this.searchButton = (Button) findViewById(R.id.search_searchButton);
        this.searchField = (EditText) findViewById(R.id.search_searchField);
        this.recordList = (ListView) findViewById(R.id.record_list);
        
        // Empty results list to begin with
        this.results = new ArrayList<DiscogsReleasesResult>();
        // Set the adapter to the list
        this.recordAdapter = new RecordAdapter(SearchActivity.this, this.results);
		this.recordList.setAdapter(this.recordAdapter);
        
		// Make search button clickable
        this.searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SearchActivity.this.currentQuery = SearchActivity.this.searchField.getText().toString();
				SearchActivity.this.currentPage = 1;
				SearchActivity.this.results.clear();
				SearchActivity.this.recordAdapter.notifyDataSetChanged();
				SearchActivity.this.searchNext();
			}
		});
        
        // Make scrolling auto-search
        this.recordList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(!SearchActivity.this.searching && firstVisibleItem + visibleItemCount >= totalItemCount) {
					// One page further
					SearchActivity.this.currentPage++;
					// Search
					SearchActivity.this.searchNext();
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
        });
	}
	
	public void searchNext() {
		// No query = do nothing
		if(this.currentQuery == null)
			return;
		
		// Make Search Task and set completed handler
		SearchTask st = new SearchTask(this.currentQuery, this.currentPage, new SearchTask.OnSearchCompletedHandler() {
			@Override
			public void onSearchCompleted(List<DiscogsReleasesResult> results) {
				// Add the results
				SearchActivity.this.results.addAll(results);
				SearchActivity.this.recordAdapter.notifyDataSetChanged();
				// Unset searching flag
				SearchActivity.this.searching = false;
				setProgressBarIndeterminateVisibility(false);
			}
		});
		
		// Execute the search
		st.execute();
		// Set searching flag
		this.searching = true;
		setProgressBarIndeterminateVisibility(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
