package nl.milanboers.recordcollector.discogs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscogsRelease {
	@SerializedName("results")
	public List<DiscogsReleasesResult> results;
}
