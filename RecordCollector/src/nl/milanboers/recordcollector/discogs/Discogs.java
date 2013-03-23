package nl.milanboers.recordcollector.discogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

/**
 * This is a singleton so no new object (and Gson object with it) has to be made
 * every time you load a next page or search something.
 * 
 * @author Milan Boers
 * 
 */
public class Discogs {
	@SuppressWarnings("unused")
	private static final String TAG = "Discogs";
	
	public interface OnReadyListener {
		public void onReady();
	};
	
	private static Discogs instance;
	private Gson gson;
	
	private Discogs() {
		this.gson = new Gson();
	}
	
	public static Discogs getInstance() {
		if(instance == null)
			instance = new Discogs();
		return instance;
	}
	
	private BufferedReader get(String resource) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://api.discogs.com/" + resource);
		HttpResponse response = client.execute(request);
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		return rd;
	}
	
	public List<DiscogsMasterResult> search(String query) throws ClientProtocolException, IOException {
		return this.search(query, 1);
	}
	
	public List<DiscogsMasterResult> search(String query, int page) throws ClientProtocolException, IOException {
		// Encode so spaces become +
		query = URLEncoder.encode(query, "UTF-8");
		Reader r = this.get("database/search?type=master&page=" + page + "&q=" + query);
		DiscogsMaster response = gson.fromJson(r, DiscogsMaster.class);
		return response.results;
	}
}
