package nl.milanboers.recordcollector.search;

import java.util.List;

import android.os.AsyncTask;

import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.views.LoadingListView;

public class SearchLoader implements LoadingListView.Loader {
	// Event interfaces
	public static interface OnSearchStartedListener {
		public void onSearchStarted();
	}
	public static interface OnSearchCompletedListener {
		public void onSearchCompleted(List<DiscogsSimpleMaster> results);
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
	public void load(final int page) {
		// TODO: Make sure it stops at the right page
		// If there's no query or it's already loading, we ignore this
		if(this.currentQuery == null || this.searching == true)
			return;
		
		// Make Search Task
		AsyncTask<Void, Void, List<DiscogsSimpleMaster>> st = new AsyncTask<Void, Void, List<DiscogsSimpleMaster>>() {
			@Override
			protected List<DiscogsSimpleMaster> doInBackground(Void... params) {
				try {
					return Discogs.getInstance().search(SearchLoader.this.currentQuery, page);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(List<DiscogsSimpleMaster> results) {
				SearchLoader.this.searching = false;
				if(SearchLoader.this.onSearchCompletedListener != null)
					SearchLoader.this.onSearchCompletedListener.onSearchCompleted(results);
			}
		};
		
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
