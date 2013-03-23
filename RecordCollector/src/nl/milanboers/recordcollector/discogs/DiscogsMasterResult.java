package nl.milanboers.recordcollector.discogs;

import com.google.gson.annotations.SerializedName;

public class DiscogsMasterResult {
	@SerializedName("title")
	public String title;
	
	@SerializedName("type")
	public String release;
}
