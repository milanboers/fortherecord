/* This Source Code Form is subject to the terms of the Mozilla Public
 *  * License, v. 2.0. If a copy of the MPL was not distributed with this
 *   * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package nl.milanboers.recordcollector.errors;

import java.util.HashMap;
import java.util.Map;

import nl.milanboers.recordcollector.R;

import android.content.Context;
import android.widget.Toast;

public class ErrorShower {
	public static void showError(ErrorType error, final Context ctx) {
		final Map<ErrorType, String> errorMessages = new HashMap<ErrorType, String>() {
			private static final long serialVersionUID = 515056387042943770L;
			{
				put(ErrorType.IO, ctx.getString(R.string.error_io));
				put(ErrorType.JSON, ctx.getString(R.string.error_json));
				put(ErrorType.NOTREADY, ctx.getString(R.string.error_notready));
			}
		};

		String errorStr;
		if(error != null)
			errorStr = errorMessages.get(error);
		else
			errorStr = ctx.getString(R.string.error_inerror);

		Toast.makeText(ctx, errorStr, Toast.LENGTH_LONG).show();
	}
}
