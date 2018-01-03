package ch.ralena.activitypractice.api;

import ch.ralena.activitypractice.models.artists.ArtistResults;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LastFmApi {
	@GET("?method=chart.gettopartists&api_key=" + LastFmKey.API_KEY + "&format=json")
	Call<ArtistResults> getArtists();
}
