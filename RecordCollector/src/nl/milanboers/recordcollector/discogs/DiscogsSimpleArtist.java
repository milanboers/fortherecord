package nl.milanboers.recordcollector.discogs;

import com.google.gson.annotations.SerializedName;

public class DiscogsSimpleArtist {
	@SerializedName("id")
	public int id;
	
	@SerializedName("name")
	public String name;
}
