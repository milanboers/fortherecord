package nl.milanboers.recordcollector.discogs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscogsMaster {
	@SerializedName("results")
	public List<DiscogsMasterResult> results;
}
