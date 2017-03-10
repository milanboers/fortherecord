/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.discogs;

import com.google.gson.annotations.SerializedName;

public class DiscogsSimpleArtist {
	@SerializedName("id")
	public int id;

	@SerializedName("name")
	public String name;
}
