package ch.ralena.activitypractice.models.artists;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artists {
	@SerializedName("artist")
	List<Artist> artists;

	public List<Artist> getArtists() {
		return artists;
	}
}
