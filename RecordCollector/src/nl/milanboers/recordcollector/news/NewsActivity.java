package nl.milanboers.recordcollector.news;

import java.util.ArrayList;
import java.util.List;

import org.mcsoxford.rss.RSSItem;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.news.NewsFetcher.OnNewItemsListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class NewsActivity extends Activity {
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
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

}
