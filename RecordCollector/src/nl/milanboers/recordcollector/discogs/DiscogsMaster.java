package nl.milanboers.recordcollector.discogs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;

public class DiscogsMaster extends DiscogsSimpleMaster {
	@SerializedName("artists")
	public List<DiscogsSimpleArtist> artists;
	
	@SerializedName("tracklist")
	public List<DiscogsTrack> tracklist;
	
	@SerializedName("images")
	public List<DiscogsImage> images;
	
	public DiscogsMaster(Parcel in) {
		super(in);
	}
}
