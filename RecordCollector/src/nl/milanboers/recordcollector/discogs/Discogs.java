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

import android.util.Log;

import com.google.gson.Gson;

public class Discogs {
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
	
	public List<DiscogsReleasesResult> search(String query) throws ClientProtocolException, IOException {
		return this.search(query, 1);
	}
	
	public List<DiscogsReleasesResult> search(String query, int page) throws ClientProtocolException, IOException {
		// Encode so spaces become +
		query = URLEncoder.encode(query, "UTF-8");
		Reader r = this.get("database/search?type=master&page=" + page + "&q=" + query);
		DiscogsRelease response = gson.fromJson(r, DiscogsRelease.class);
		return response.results;
	}
}