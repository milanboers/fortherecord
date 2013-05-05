package nl.milanboers.recordcollector;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.Toast;

public class ErrorShower {
	public static void showError(ErrorType error, final Context ctx) {
		final Map<ErrorType, String> errorMessages = new HashMap<ErrorType, String>() {
			private static final long serialVersionUID = 515056387042943770L;
			{
				put(ErrorType.IO, ctx.getString(R.string.error_io));
				put(ErrorType.PROTOCOL, ctx.getString(R.string.error_protocol));
			}
		};
		
		Toast.makeText(ctx, errorMessages.get(error), Toast.LENGTH_LONG).show();
	}
}