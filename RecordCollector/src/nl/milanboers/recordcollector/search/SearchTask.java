package nl.milanboers.recordcollector.search;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import nl.milanboers.recordcollector.discogs.Discogs;
import nl.milanboers.recordcollector.discogs.DiscogsMasterResult;
import android.os.AsyncTask;

public class SearchTask extends AsyncTask<Void, Void, List<DiscogsMasterResult>> {
	@SuppressWarnings("unused")
	private static final String TAG = "SearchTask";
	
	public interface OnSearchCompletedListener {
		public void onSearchCompleted(List<DiscogsMasterResult> results);
	}
	
	private OnSearchCompletedListener onReadyHandler;
	
	private Discogs discogs;
	private String query;
	private int page;
	
	public SearchTask(String query, int page, OnSearchCompletedListener onReadyHandler) {
		this.onReadyHandler = onReadyHandler;
		this.query = query;
		this.page = page;
		
        this.discogs = Discogs.getInstance();
	}

	@Override
	protected List<DiscogsMasterResult> doInBackground(Void... params) {
		try {
			List<DiscogsMasterResult> results = this.discogs.search(this.query, this.page);
			return results;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<DiscogsMasterResult> results) {
		this.onReadyHandler.onSearchCompleted(results);
	}
}
