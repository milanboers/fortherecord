package nl.milanboers.recordcollector.discogs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscogsSimpleMasterResponse {
	@SerializedName("results")
	public List<DiscogsSimpleMaster> results;
}
