package com.android.emilany.trademe.views.subcategories;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.emilany.trademe.R;
import com.android.emilany.trademe.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.RecyclerViewHolder> {

    public interface OnSubcategoryItemClickListener {
        void onSubcategoryItemClicked(Category subcategory);
    }

    private Context context;
    private OnSubcategoryItemClickListener listener;
    private List<Category> subcategoryList;

    public SubcategoryAdapter(Context context, OnSubcategoryItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        subcategoryList = new ArrayList<>();
    }

    public void setItems(List<Category> items) {
        this.subcategoryList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return subcategoryList != null ? subcategoryList.size() : 0;
    }

    @Override
    public SubcategoryAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_subcategory, parent, false);
        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Category subcategory = subcategoryList.get(position);
        holder.bind(subcategory, listener);
    }

    public final class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview_card_item)
        CardView ivSubcategoryItem;
        @BindView(R.id.textview_card_subcategorytitle)
        TextView tvSubcategoryTitle;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Category subcategory, final OnSubcategoryItemClickListener listener) {
            tvSubcategoryTitle.setText(subcategory.getName());

            ivSubcategoryItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSubcategoryItemClicked(subcategory);
                }
            });
        }
    }
}
