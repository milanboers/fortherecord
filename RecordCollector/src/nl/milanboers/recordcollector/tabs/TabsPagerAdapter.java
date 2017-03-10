/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.tabs;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	private List<TabFragment> fragments;

	public TabsPagerAdapter(FragmentManager fm, List<TabFragment> fragments) {
		super(fm);

		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int pos) {
		return this.fragments.get(pos);
	}

	@Override
	public int getCount() {
		return this.fragments.size();
	}


}
