package nl.milanboers.recordcollector.discogs;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DiscogsSimpleMaster implements Parcelable {
	@SerializedName("id")
	public int id;
	
	@SerializedName("title")
	public String title;
	
	@SerializedName("year")
	public String year;
	
	@SerializedName("genre")
	public List<String> genre;
	
	@SerializedName("thumb")
	public String thumb;
	
	@SerializedName("type")
	public String type;
	
	public DiscogsSimpleMaster(Parcel in) {
		this.id = in.readInt();
		this.title = in.readString();
		this.year = in.readString();
		this.genre = new ArrayList<String>();
		in.readList(this.genre, null);
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
		dest.writeString(year);
		dest.writeList(genre);
		dest.writeString(thumb);
		dest.writeString(type);
	}
	
	public static final Parcelable.Creator<DiscogsSimpleMaster> CREATOR
		= new Parcelable.Creator<DiscogsSimpleMaster>() {
		public DiscogsSimpleMaster createFromParcel(Parcel in) {
			return new DiscogsSimpleMaster(in);
		}
		
		public DiscogsSimpleMaster[] newArray(int size) {
			return new DiscogsSimpleMaster[size];
		}
	};

}
