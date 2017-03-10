/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.search;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import android.os.AsyncTask;

import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleMasterResponse;
import nl.milanboers.recordcollector.errors.ErrorType;
import nl.milanboers.recordcollector.views.LoadingListView;

public class SearchLoader implements LoadingListView.Loader {
	// Events
	public static interface OnSearchStartedListener {
		public void onSearchStarted();
	}
	private OnSearchStartedListener onSearchStartedListener;
	public void setOnSearchStartedListener(OnSearchStartedListener onSearchStartedListener) {
		this.onSearchStartedListener = onSearchStartedListener;
	}
	public static interface OnSearchCompletedListener {
		public void onSearchCompleted(List<DiscogsSimpleMaster> results);
		public void onError(ErrorType error);
	}
	private OnSearchCompletedListener onSearchCompletedListener;
	public void setOnSearchCompletedListener(OnSearchCompletedListener onSearchCompletedListener) {
		this.onSearchCompletedListener = onSearchCompletedListener;
	}

	// Is it currently searching?
	private boolean searching = false;
	// The query that's being searched for
	private String currentQuery;
	// The amount of pages of the query that's being searched for
	private int pages;

	public SearchLoader() { }

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
		AsyncTask<Void, Void, DiscogsSimpleMasterResponse> st = new SearchTask(page);
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

	private class SearchTask extends AsyncTask<Void, Void, DiscogsSimpleMasterResponse> {
		private ErrorType error;
		private int page;

		public SearchTask(int page) {
			this.page = page;
		}

		@Override
		protected DiscogsSimpleMasterResponse doInBackground(Void... params) {
			try {
				return Discogs.getInstance().search(SearchLoader.this.currentQuery, this.page);
			} catch (JsonSyntaxException e) {
				this.error = ErrorType.JSON;
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
}
