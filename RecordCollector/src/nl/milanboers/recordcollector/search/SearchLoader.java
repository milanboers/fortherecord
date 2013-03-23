package nl.milanboers.recordcollector.search;

import java.util.List;

import nl.milanboers.recordcollector.discogs.DiscogsMasterResult;
import nl.milanboers.recordcollector.views.LoadingListView;

public class SearchLoader implements LoadingListView.Loader {
	// Event interfaces
	public static interface OnSearchStartedListener {
		public void onSearchStarted();
	}
	public static interface OnSearchCompletedListener {
		public void onSearchCompleted(List<DiscogsMasterResult> results);
	}
	
	// Is it currently searching?
	private boolean searching = false;
	// The query that's being searched for
	private String currentQuery;
	
	public SearchLoader() { }
	
	// Events
	private OnSearchStartedListener onSearchStartedListener;
	public void setOnSearchStartedListener(OnSearchStartedListener onSearchStartedListener) {
		this.onSearchStartedListener = onSearchStartedListener;
	}
	private OnSearchCompletedListener onSearchCompletedListener;
	public void setOnSearchCompletedListener(OnSearchCompletedListener onSearchCompletedListener) {
		this.onSearchCompletedListener = onSearchCompletedListener;
	}
	
	// Changes the search query for the next operation
	public void setQuery(String query) {
		this.currentQuery = query;
	}
	
	@Override
	public void load(int page) {
		// TODO: Make sure it stops at the right page
		// If there's no query or it's already loading, we ignore this
		if(this.currentQuery == null || this.searching == true)
			return;
		
		// Make Search Task and set completed handler
		SearchTask st = new SearchTask(this.currentQuery, page, new SearchTask.OnSearchCompletedListener() {
			@Override
			public void onSearchCompleted(List<DiscogsMasterResult> results) {
				SearchLoader.this.searching = false;
				if(SearchLoader.this.onSearchCompletedListener != null)
					SearchLoader.this.onSearchCompletedListener.onSearchCompleted(results);
			}
		});
		
		// Execute the search
		st.execute();
		// Set loading flag
		this.searching = true;
		if(SearchLoader.this.onSearchStartedListener != null)
			this.onSearchStartedListener.onSearchStarted();
	}

	@Override
	public boolean isLoading() {
		return this.searching;
	}
	
}
