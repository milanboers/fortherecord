package nl.milanboers.recordcollector.discogs;

import com.google.gson.annotations.SerializedName;

public class DiscogsTrack {
	@SerializedName("duration")
	public String duration;
	
	@SerializedName("position")
	public String position;
	
	@SerializedName("title")
	public String title;
}
