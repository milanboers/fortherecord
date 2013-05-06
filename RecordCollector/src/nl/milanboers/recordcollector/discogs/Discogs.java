package nl.milanboers.recordcollector.discogs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
	
	private Reader get(String resource) throws IOException {
		InputStream input = new URL("http://api.discogs.com/" + resource).openStream();
		Reader r = new InputStreamReader(input, "UTF-8");
		return r;
	}
	
	public DiscogsSimpleMasterResponse search(String query) throws JsonSyntaxException, IOException {
		return this.search(query, 1);
	}
	
	public DiscogsSimpleMasterResponse search(String query, int page) throws JsonSyntaxException, IOException {
		// Encode so spaces become +
		query = URLEncoder.encode(query, "UTF-8");
		Reader r = this.get("database/search?type=master&page=" + page + "&q=" + query);
		DiscogsSimpleMasterResponse response = gson.fromJson(r, DiscogsSimpleMasterResponse.class);
		return response;
	}
	
	public DiscogsMaster master(int id) throws JsonSyntaxException, IOException {
		Reader r = this.get("masters/" + id);
		DiscogsMaster master = gson.fromJson(r, DiscogsMaster.class);
		return master;
	}
	
	public DiscogsArtist artist(int id) throws JsonSyntaxException, IOException {
		Reader r = this.get("artists/" + id);
		DiscogsArtist artist = gson.fromJson(r, DiscogsArtist.class);
		return artist;
	}
}
