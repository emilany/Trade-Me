package com.android.emilany.trademe.views.subcategories.di;

import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.subcategories.SubcategoryContract;
import com.android.emilany.trademe.views.subcategories.SubcategoryPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SubcategoryModule {

    @Provides
    @FragmentScope
    static SubcategoryPresenter provideSubcategoryPresenter(ApiInterface apiInterface) {
        return new SubcategoryPresenter(apiInterface);
    }

    @FragmentScope
    @Binds
    abstract SubcategoryContract.Presenter subcategoryPresenter(SubcategoryPresenter presenter);
}
