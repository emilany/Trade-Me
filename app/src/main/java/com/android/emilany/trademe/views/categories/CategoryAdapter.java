package com.android.emilany.trademe.views.categories;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.emilany.trademe.R;
import com.android.emilany.trademe.models.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder> {

    public interface OnCategoryItemClickListener {
        void onCategoryItemClicked(Category category);
    }

    private Context context;
    private OnCategoryItemClickListener listener;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, OnCategoryItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        categoryList = new ArrayList<>();
    }

    public void setItems(List<Category> items) {
        this.categoryList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    @Override
    public CategoryAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.layout_item_category, parent, false);
        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category, listener);
    }

    public final class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_category_categorytitle)
        TextView tvCategoryTitle;
        @BindView(R.id.relativelayout_category_cardcontent)
        RelativeLayout rlCardContent;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Category category, final OnCategoryItemClickListener listener) {
            tvCategoryTitle.setText(category.getName());

            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
            rlCardContent.startAnimation(animation);

            rlCardContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategoryItemClicked(category);
                }
            });
        }
    }
}
