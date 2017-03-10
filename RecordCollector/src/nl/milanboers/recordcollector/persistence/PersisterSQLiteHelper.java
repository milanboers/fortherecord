/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersisterSQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_MYARTISTS = "myartists";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";

	private static final String DATABASE_NAME = "ftr";
	private static final int DATABASE_VERSION = 1;

	// Database creation statement
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_MYARTISTS
			+ "("
			+ COLUMN_ID   + " INTEGER PRIMARY KEY, "
			+ COLUMN_NAME + " TEXT NOT NULL"
			+ ")";

	public PersisterSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		// Remove all old data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYARTISTS);
		// Create new db
		onCreate(db);
	}

}
