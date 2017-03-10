/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
