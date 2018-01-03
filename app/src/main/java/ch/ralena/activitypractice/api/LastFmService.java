package ch.ralena.activitypractice.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LastFmService {
	private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
	public static LastFmApi getLastFmService() {
		return new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(LastFmApi.class);

	}
}
