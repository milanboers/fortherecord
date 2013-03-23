package nl.milanboers.recordcollector.image;

import com.actionbarsherlock.app.SherlockActivity;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.R.layout;
import nl.milanboers.recordcollector.R.menu;
import nl.milanboers.recordcollector.record.ImageLoadTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;

public class ImageActivity extends SherlockActivity {
	
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		getSupportActionBar().hide();
		
		this.imageView = (ImageView) findViewById(R.id.image_image);
		
		String imageURL = getIntent().getStringExtra("image");
		
		ImageLoadTask task = new ImageLoadTask(this.imageView);
		task.execute(imageURL);
	}

}
