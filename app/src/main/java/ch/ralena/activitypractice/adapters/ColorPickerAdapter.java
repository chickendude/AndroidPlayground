package ch.ralena.activitypractice.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ColorViewHolder> {

	public interface OnColorClickedListener {
		void onColorClicked(String color);
	}

	List<String> colors;
	OnColorClickedListener listener;

	public ColorPickerAdapter(List<String> colors, OnColorClickedListener listener) {
		this.colors = colors;
		this.listener = listener;
	}

	@Override
	public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = new View(parent.getContext()) {
			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
				int width = getMeasuredWidth();
				setMeasuredDimension(width, width);
			}
		};

		return new ColorViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ColorViewHolder holder, int position) {
		holder.bindView(colors.get(position));
	}

	@Override
	public int getItemCount() {
		return colors.size();
	}

	class ColorViewHolder extends RecyclerView.ViewHolder {

		public ColorViewHolder(View itemView) {
			super(itemView);
		}

		public void bindView(String color) {
			itemView.setOnClickListener(view -> listener.onColorClicked(color));
			try {
				itemView.setBackgroundColor(Color.parseColor(color));
			} catch (IllegalArgumentException iae) {
				itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
			}
		}
	}
}
