/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.master.fragments;

import nl.milanboers.recordcollector.R;
import nl.milanboers.recordcollector.artist.ArtistActivity;
import nl.milanboers.recordcollector.discogs.DiscogsMaster;
import nl.milanboers.recordcollector.discogs.DiscogsSimpleArtist;
import nl.milanboers.recordcollector.image.ImageActivity;
import nl.milanboers.recordcollector.tabs.TabFragment;
import nl.milanboers.recordcollector.tools.ImageLoadTask;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MasterRecordFragment extends TabFragment {
	@SuppressWarnings("unused")
	private static String TAG = "MasterRecordFragment";

	private DiscogsMaster master;

	private TextView titleView;
	private ImageView thumbView;
	private LinearLayout artistsLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_master_record, container, false);

		this.titleView = (TextView) v.findViewById(R.id.master_title);
		this.thumbView = (ImageView) v.findViewById(R.id.master_thumb);
		this.artistsLayout = (LinearLayout) v.findViewById(R.id.master_artists);

		// Make sure clicking on the image opens the popup image
		this.thumbView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Load the normal image if it's there, otherwise don't do anything
				Drawable bmpd = MasterRecordFragment.this.thumbView.getDrawable();
				if(bmpd != null && bmpd instanceof BitmapDrawable) {
					Intent intent = new Intent(getActivity(), ImageActivity.class);
					intent.putExtra("bitmap", ((BitmapDrawable)bmpd).getBitmap());
					startActivity(intent);
				}
			}
		});

		return v;
	}

	public void setMaster(DiscogsMaster master) {
		this.master = master;

		// Title
		this.titleView.setText(this.master.title);

		// Artist buttons
		for(final DiscogsSimpleArtist artist : this.master.artists) {
			Button btn = new Button(getActivity());
			btn.setText(artist.name);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// Start artist activity
					Intent intent = new Intent(getActivity(), ArtistActivity.class);
					intent.putExtra("id", artist.id);
					startActivity(intent);
				}
			});
			this.artistsLayout.addView(btn);
		}

		// Load the image
		if(this.master.images != null && this.master.images.size() > 0) {
			new ImageLoadTask().execute(this.thumbView, this.master.images.get(0).uri);
		}
	}

	@Override
	public int getNameId() {
		return R.string.record;
	}
}
