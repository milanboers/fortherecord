package nl.milanboers.recordcollector;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import nl.milanboers.recordcollector.news.NewsActivity;
import nl.milanboers.recordcollector.search.SearchActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "MainActivity";
	
	// Components
	private Button searchButton;
	private Button newsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.searchButton = (Button) findViewById(R.id.main_searchButton);
		this.searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});
		
		this.newsButton = (Button) findViewById(R.id.main_newsButton);
		this.newsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, NewsActivity.class);
				startActivity(intent);
			}
		});
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
}
