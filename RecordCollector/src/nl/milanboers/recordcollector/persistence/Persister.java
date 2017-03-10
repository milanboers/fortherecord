/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Persister {
	private PersisterSQLiteHelper helper;
	private SQLiteDatabase db;

	public Persister(Context context) {
		this.helper = new PersisterSQLiteHelper(context);
		this.db = this.helper.getWritableDatabase();
	}

	public void favArtist(int id, String name) {
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("name", name);

		this.db.insert(PersisterSQLiteHelper.TABLE_MYARTISTS, null, values);
	}

	public void unfavArtist(int id) {
		this.db.delete(PersisterSQLiteHelper.TABLE_MYARTISTS, "id = " + id, null);
	}

	public boolean isFavArtist(int id) {
		Cursor cursor = this.db.query(
				PersisterSQLiteHelper.TABLE_MYARTISTS,
				new String[] { PersisterSQLiteHelper.COLUMN_ID, PersisterSQLiteHelper.COLUMN_NAME },
				"id = " + id,
				null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			cursor.close();
			return true;
		}
		return false;
	}
}
