package com.android.emilany.trademe.views.listings.di;

import com.android.emilany.trademe.di.AppComponent;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.listings.ListingFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = ListingModule.class)
public interface ListingComponent {

    void inject(ListingFragment listingFragment);
}
