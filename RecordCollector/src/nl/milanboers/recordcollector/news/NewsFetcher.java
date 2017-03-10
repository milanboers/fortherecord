/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.news;

import java.util.List;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import android.os.AsyncTask;
import android.util.Log;

public class NewsFetcher {
	private static final String TAG = "NewsFetcher";

	// Events
	public static interface OnNewItemsListener {
		public void onNewItems(List<RSSItem> items);
	}
	private OnNewItemsListener onNewItemsListener;
	public void setOnNewItemsListener(OnNewItemsListener onNewItemsListener) {
		this.onNewItemsListener = onNewItemsListener;
	}

	public NewsFetcher() {

	}

	public void refresh() {
		new RSSTask().execute(new String[] { "http://pitchfork.com/rss/reviews/albums/" });
	}

	private class RSSTask extends AsyncTask<String, Void, List<RSSItem>> {
		@Override
		protected List<RSSItem> doInBackground(String... args) {
			Log.v(TAG, "task started");

			RSSReader reader = new RSSReader();
			String uri = args[0];
			try {
				RSSFeed feed = reader.load(uri);
				List<RSSItem> items = feed.getItems();
				return items;
			} catch (RSSReaderException e) {
				// Just ignore this one
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<RSSItem> items) {
			if(NewsFetcher.this.onNewItemsListener != null)
				NewsFetcher.this.onNewItemsListener.onNewItems(items);
		}
	};
}
