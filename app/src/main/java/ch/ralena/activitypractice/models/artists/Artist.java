package ch.ralena.activitypractice.models.artists;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artist {
	String name;
	String playcount;
	String listeners;
	String url;
	@SerializedName("image")
	List<Image> images;

	public String getName() {
		return name;
	}

	public String getPlaycount() {
		return playcount;
	}

	public String getListeners() {
		return listeners;
	}

	public String getUrl() {
		return url;
	}

	public List<Image> getImages() {
		return images;
	}
}
