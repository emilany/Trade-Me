package com.android.emilany.trademe.views.subcategories;

import com.android.emilany.trademe.models.Category;
import com.android.emilany.trademe.models.ListingItem;
import com.android.emilany.trademe.views.BasePresenter;
import com.android.emilany.trademe.views.BaseView;

import java.util.List;

public interface SubcategoryContract {

    interface View extends BaseView {

        void showProgress(boolean show);

        void showSubcategory();

        void showListings(List<ListingItem> listingList);
    }

    interface Presenter extends BasePresenter<View> {

        void loadListings(Category subcategory);
    }
}
