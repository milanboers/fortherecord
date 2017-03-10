/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.artist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.discogs.DiscogsArtist;
import nl.milanboers.recordcollector.tabs.TabFragment;

public class ArtistProfileFragment extends TabFragment {

	private TextView nameView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_artist_profile, container, false);

		this.nameView = (TextView) v.findViewById(R.id.artist_name);

		return v;
	}

	public void setProfile(DiscogsArtist artist) {
		this.nameView.setText(artist.name);
	}

	@Override
	public int getNameId() {
		return R.string.profile;
	}

}
