package com.android.emilany.trademe.views.listings;

import com.android.emilany.trademe.models.Listing;
import com.android.emilany.trademe.views.BasePresenter;
import com.android.emilany.trademe.views.BaseView;

public interface ListingContract {

    interface View extends BaseView {

        void showListing(Listing listing);
    }

    interface Presenter extends BasePresenter<View> {

        void loadListing(int listingId);
    }
}
