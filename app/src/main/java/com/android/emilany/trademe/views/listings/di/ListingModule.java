package com.android.emilany.trademe.views.listings.di;

import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.listings.ListingContract;
import com.android.emilany.trademe.views.listings.ListingPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ListingModule {

    @Provides
    @FragmentScope
    static ListingPresenter provideListingPresenter(ApiInterface apiInterface) {
        return new ListingPresenter(apiInterface);
    }

    @FragmentScope
    @Binds
    abstract ListingContract.Presenter listingPresenter(ListingPresenter presenter);
}
