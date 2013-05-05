package nl.milanboers.recordcollector.search;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;

import nl.milanboers.recordcollector.ErrorType;
import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMasterResponse;
import nl.milanboers.recordcollector.views.LoadingListView;

public class SearchLoader implements LoadingListView.Loader {
	// Event interfaces
	public static interface OnSearchStartedListener {
		public void onSearchStarted();
	}
	public static interface OnSearchCompletedListener {
		public void onSearchCompleted(List<DiscogsSimpleMaster> results);
		public void onError(ErrorType error);
	}
	
	// Is it currently searching?
	private boolean searching = false;
	// The query that's being searched for
	private String currentQuery;
	// The amount of pages of the query that's being searched for
	private int pages;
	
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
		this.pages = Integer.MAX_VALUE;
	}
	
	@Override
	public void load(final int page) {
		// If there's no query, it's already loading, or we're past the final page, we ignore this
		if(this.currentQuery == null || this.searching == true || page > this.pages)
			return;
		
		// Make Search Task
		AsyncTask<Void, Void, DiscogsSimpleMasterResponse> st = new AsyncTask<Void, Void, DiscogsSimpleMasterResponse>() {
			private ErrorType error;
			
			@Override
			protected DiscogsSimpleMasterResponse doInBackground(Void... params) {
				try {
					return Discogs.getInstance().search(SearchLoader.this.currentQuery, page);
				} catch (ClientProtocolException e) {
					this.error = ErrorType.PROTOCOL;
					return null;
				} catch (IOException e) {
					this.error = ErrorType.IO;
					return null;
				}
			}
			
			@Override
			protected void onPostExecute(DiscogsSimpleMasterResponse response) {
				if(response == null) {
					// Error
					SearchLoader.this.onSearchCompletedListener.onError(this.error);
					return;
				}
				// Success!
				SearchLoader.this.searching = false;
				SearchLoader.this.pages = response.pagination.pages;
				if(SearchLoader.this.onSearchCompletedListener != null)
					SearchLoader.this.onSearchCompletedListener.onSearchCompleted(response.results);
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
