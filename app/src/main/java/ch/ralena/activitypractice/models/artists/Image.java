package ch.ralena.activitypractice.models.artists;

import com.google.gson.annotations.SerializedName;

public class Image {
	@SerializedName("#text")
	String url;
	String size;

	public String getUrl() {
		return url;
	}

	public String getSize() {
		return size;
	}
}
