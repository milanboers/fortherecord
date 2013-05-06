package nl.milanboers.recordcollector.discogs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;

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
	
	public DiscogsSimpleMasterResponse search(String query) throws ClientProtocolException, IOException {
		return this.search(query, 1);
	}
	
	public DiscogsSimpleMasterResponse search(String query, int page) throws ClientProtocolException, IOException {
		// Encode so spaces become +
		query = URLEncoder.encode(query, "UTF-8");
		Reader r = this.get("database/search?type=master&page=" + page + "&q=" + query);
		DiscogsSimpleMasterResponse response = gson.fromJson(r, DiscogsSimpleMasterResponse.class);
		return response;
	}
	
	public DiscogsMaster master(int id) throws ClientProtocolException, IOException {
		Reader r = this.get("masters/" + id);
		DiscogsMaster master = gson.fromJson(r, DiscogsMaster.class);
		return master;
	}
	
	public DiscogsArtist artist(int id) throws ClientProtocolException, IOException {
		Reader r = this.get("artists/" + id);
		DiscogsArtist artist = gson.fromJson(r, DiscogsArtist.class);
		return artist;
	}
}
