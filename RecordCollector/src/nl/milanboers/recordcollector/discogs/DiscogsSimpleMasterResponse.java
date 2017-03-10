/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.discogs;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscogsSimpleMasterResponse {
	@SerializedName("results")
	public List<DiscogsSimpleMaster> results;

	@SerializedName("pagination")
	public DiscogsPagination pagination;
}
