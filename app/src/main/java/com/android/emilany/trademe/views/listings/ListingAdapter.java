package com.android.emilany.trademe.views.listings;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.emilany.trademe.R;
import com.android.emilany.trademe.models.ListingItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.RecyclerViewHolder> {

    public interface OnListingItemClickListener {
        void onItemListingClicked(ListingItem listing);
    }

    private Context context;
    private OnListingItemClickListener listener;
    private List<ListingItem> listingList;

    public ListingAdapter(Context context, OnListingItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        listingList = new ArrayList<>();
    }

    public void setItems(List<ListingItem> items) {
        this.listingList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listingList.size();
    }

    @Override
    public ListingAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_listing, parent, false);
        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ListingItem listing = listingList.get(position);
        holder.bind(listing, listener);
    }

    public final class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview_listing)
        CardView cvListing;
        @BindView(R.id.imageview_listing_listingimage)
        ImageView ivListingImage;
        @BindView(R.id.textview_listing_listingTitle)
        TextView tvListingTitle;
        @BindView(R.id.textview_listing_listingPrice)
        TextView tvListingPrice;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ListingItem listing, final OnListingItemClickListener listener) {
            tvListingTitle.setText(listing.getTitle());
            tvListingPrice.setText(listing.getPrice());

            if (listing.getThumbnail() != null) {
                Picasso.with(context).load(listing.getThumbnail()).into(ivListingImage);
            } else {
                ivListingImage.setImageResource(R.drawable.ic_no_pictures);
                ivListingImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            cvListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemListingClicked(listing);
                }
            });
        }
    }
}
