package nl.milanboers.recordcollector.record;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsMasterResult;
import nl.milanboers.recordcollector.image.ImageActivity;
import nl.milanboers.recordcollector.search.SearchActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordActivity extends Activity {
	private static final String TAG = "RecordActivity";
	
	private DiscogsMasterResult master;
	
	private ImageView thumbView;
	private TextView titleView;
	//private TextView artistView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		this.master = getIntent().getParcelableExtra("master");
		
		this.thumbView = (ImageView) findViewById(R.id.record_thumb);
		this.titleView = (TextView) findViewById(R.id.record_title);
		//this.artistView = (TextView) findViewById(R.id.record_artist);
		
		this.titleView.setText(this.master.title);
		
		ImageLoadTask task = new ImageLoadTask(this.thumbView);
		task.execute(this.master.thumb);
		
		this.thumbView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RecordActivity.this, ImageActivity.class);
				intent.putExtra("image", RecordActivity.this.master.thumb);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record, menu);
		return true;
	}

}
