package nl.milanboers.recordcollector.tools;

import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoadTask extends AsyncTask<Object, Void, Bitmap> {
	private ImageView iv;
	
	@Override
	protected Bitmap doInBackground(Object... args) {
		this.iv = (ImageView) args[0];
		String urlStr = (String) args[1];
		try {
			URL url = new URL(urlStr);
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
			this.iv.setImageBitmap(bmp);
	}
}
