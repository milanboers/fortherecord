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
