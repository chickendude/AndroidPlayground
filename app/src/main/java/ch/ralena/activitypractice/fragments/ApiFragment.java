package ch.ralena.activitypractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.adapters.ApiAdapter;
import ch.ralena.activitypractice.api.LastFmApi;
import ch.ralena.activitypractice.api.LastFmService;
import ch.ralena.activitypractice.models.artists.Artist;
import ch.ralena.activitypractice.models.artists.ArtistResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiFragment extends BaseFragment {
	List<Artist> artists = new ArrayList<>();
	ApiAdapter adapter;

	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// inflate layout
		rootView = inflater.inflate(R.layout.fragment_api, container, false);

		LastFmApi lastFmApi = LastFmService.getLastFmService();
		lastFmApi.getArtists().enqueue(new Callback<ArtistResults>() {
			@Override
			public void onResponse(Call<ArtistResults> call, Response<ArtistResults> response) {
				if (response.isSuccessful()) {
					artists.clear();
					if (response.body() != null) {
						ArtistResults results = response.body();
						artists.addAll(results.getArtists().getArtists());
						adapter.notifyDataSetChanged();
					}
				}
			}

			@Override
			public void onFailure(Call<ArtistResults> call, Throwable t) {
				Log.d("TAG", "failed");

			}
		});

		RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
		adapter = new ApiAdapter(artists);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

		return rootView;
	}
}
