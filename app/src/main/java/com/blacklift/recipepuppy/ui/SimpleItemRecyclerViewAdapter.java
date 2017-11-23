package com.blacklift.recipepuppy.ui;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blacklift.recipepuppy.R;
import com.blacklift.recipepuppy.domain.model.Search;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rogergarzon on 22/11/17.
 */

public  class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final List<Search> mValues;
    private final boolean mTwoPane;
    private ItemClickListener mListener;

    SimpleItemRecyclerViewAdapter(List<Search> items, boolean twoPane, ItemClickListener listener) {
        mValues = items;
        mTwoPane = twoPane;
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    void renderImage(AppCompatImageView imageView, String url){
        Glide.with(imageView.getContext())
                .applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder))
                .load(url)
                .into(imageView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Search search = mValues.get(position);
        renderImage(holder.image, search.getThumbnail());
        holder.title.setText(search.getTitle());
        holder.content.setText(search.getIngredients());
        holder.url.setText(search.getHref());
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(view -> mListener.onItemClick(search));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        AppCompatImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.url)
        TextView url;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}