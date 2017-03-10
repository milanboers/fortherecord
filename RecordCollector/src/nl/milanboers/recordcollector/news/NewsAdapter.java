/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.news;

import java.util.List;

import nl.milanboers.recordcollector.R;

import org.mcsoxford.rss.RSSItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<RSSItem> {
	List<RSSItem> items;

	public NewsAdapter(Context context, List<RSSItem> items) {
		super(context, R.layout.list_news_item, items);

		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v == null)
		{
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.list_news_item, null);
		}

		RSSItem item = this.items.get(position);
		if(item != null) {
			// Fill the widgets
			TextView title = (TextView) v.findViewById(R.id.news_item_title);
			title.setText(item.getTitle());
		}
		return v;
	}
}
