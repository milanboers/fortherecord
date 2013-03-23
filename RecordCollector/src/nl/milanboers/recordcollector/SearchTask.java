package nl.milanboers.recordcollector;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsReleasesResult;
import android.os.AsyncTask;

public class SearchTask extends AsyncTask<Void, Void, List<DiscogsReleasesResult>> {
	@SuppressWarnings("unused")
	private static final String TAG = "SearchTask";
	
	public interface OnSearchCompletedHandler {
		public void onSearchCompleted(List<DiscogsReleasesResult> results);
	}

	private OnSearchCompletedHandler onReadyHandler;
	private Discogs discogs;
	private String query;
	private int page;
	
	public SearchTask(String query, int page, OnSearchCompletedHandler onReadyHandler) {
		this.onReadyHandler = onReadyHandler;
		this.query = query;
		this.page = page;
		
        this.discogs = Discogs.getInstance();
	}

	@Override
	protected List<DiscogsReleasesResult> doInBackground(Void... params) {
		try {
			List<DiscogsReleasesResult> results = this.discogs.search(this.query, this.page);
			return results;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(List<DiscogsReleasesResult> results) {
		this.onReadyHandler.onSearchCompleted(results);
	}
}
