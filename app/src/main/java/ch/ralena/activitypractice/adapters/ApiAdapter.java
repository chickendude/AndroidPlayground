package ch.ralena.activitypractice.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ch.ralena.activitypractice.R;
import ch.ralena.activitypractice.models.artists.Artist;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ApiViewHolder> {
	List<Artist> artists;

	public ApiAdapter(List<Artist> artists) {
		this.artists = artists;
	}

	@Override
	public ApiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lastfm_artist, parent, false);
		return new ApiViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ApiViewHolder holder, int position) {
		holder.bindView(artists.get(position));
	}

	@Override
	public int getItemCount() {
		return artists.size();
	}

	class ApiViewHolder extends RecyclerView.ViewHolder {
		ImageView artistImage;
		TextView listenersValue;
		TextView artistNameValue;

		public ApiViewHolder(View itemView) {
			super(itemView);
			artistImage = itemView.findViewById(R.id.artistImage);
			listenersValue = itemView.findViewById(R.id.listenersValue);
			artistNameValue = itemView.findViewById(R.id.artistNameValue);
		}

		public void bindView(Artist artist) {
			listenersValue.setText(String.format("%s listeners", artist.getListeners()));
			artistNameValue.setText(artist.getName());
			Picasso.with(itemView.getContext())
					.load(artist.getImages().get(1).getUrl())
					.fit()
					.into(artistImage);
		}
	}

}
