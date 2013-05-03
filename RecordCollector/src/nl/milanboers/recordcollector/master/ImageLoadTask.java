package nl.milanboers.recordcollector.master;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView view;
	
	public ImageLoadTask(ImageView view) {
		this.view = view;
	}
	
	@Override
	protected Bitmap doInBackground(String... urlStr) {
		URL url;
		try {
			url = new URL(urlStr[0]);
			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			return bmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(Bitmap bmp) {
		if(bmp != null)
			this.view.setImageBitmap(bmp);
	}
}
