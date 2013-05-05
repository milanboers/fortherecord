package nl.milanboers.recordcollector.image;

import com.actionbarsherlock.app.SherlockActivity;

import nl.milanboers.recordcollector.R;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends SherlockActivity {
	@SuppressWarnings("unused")
	private static final String TAG = "ImageActivity";
	
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		
		getSupportActionBar().hide();
		
		this.imageView = (ImageView) findViewById(R.id.image_image);
		
		Bitmap image = getIntent().getParcelableExtra("bitmap");

		this.imageView.setImageBitmap(image);
	}

}
