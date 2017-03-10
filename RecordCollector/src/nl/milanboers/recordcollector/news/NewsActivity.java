/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.news;

import java.util.ArrayList;
import java.util.List;

import org.mcsoxford.rss.RSSItem;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.news.NewsFetcher.OnNewItemsListener;

public class NewsActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "NewsActivity";

	private ListView newsList;

	private List<RSSItem> news = new ArrayList<RSSItem>();
	private NewsAdapter newsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		this.newsAdapter = new NewsAdapter(this, this.news);

		this.newsList = (ListView) findViewById(R.id.news_list);
		this.newsList.setAdapter(this.newsAdapter);

		NewsFetcher f = new NewsFetcher();
		f.setOnNewItemsListener(new OnNewItemsListener() {
			@Override
			public void onNewItems(List<RSSItem> items) {
				NewsActivity.this.news.addAll(items);
				NewsActivity.this.newsAdapter.notifyDataSetChanged();
			}
		});
		f.refresh();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

}
