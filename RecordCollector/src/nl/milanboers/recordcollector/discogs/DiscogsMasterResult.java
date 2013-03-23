package nl.milanboers.recordcollector.discogs;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DiscogsMasterResult implements Parcelable {
	@SerializedName("id")
	public int id;
	
	@SerializedName("title")
	public String title;
	
	@SerializedName("thumb")
	public String thumb;
	
	@SerializedName("type")
	public String type;
	
	public DiscogsMasterResult(Parcel in) {
		this.id = in.readInt();
		this.title = in.readString();
		this.thumb = in.readString();
		this.type = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flag) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(thumb);
		dest.writeString(type);
	}
	
	public static final Parcelable.Creator<DiscogsMasterResult> CREATOR
		= new Parcelable.Creator<DiscogsMasterResult>() {
		public DiscogsMasterResult createFromParcel(Parcel in) {
			return new DiscogsMasterResult(in);
		}
		
		public DiscogsMasterResult[] newArray(int size) {
			return new DiscogsMasterResult[size];
		}
	};

}
