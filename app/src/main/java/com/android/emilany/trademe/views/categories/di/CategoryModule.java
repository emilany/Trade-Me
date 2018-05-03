package com.android.emilany.trademe.views.categories.di;

import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.categories.CategoryContract;
import com.android.emilany.trademe.views.categories.CategoryPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class CategoryModule {

    @Provides
    @FragmentScope
    static CategoryPresenter provideCategoryPresenter(ApiInterface apiInterface) {
        return new CategoryPresenter(apiInterface);
    }

    @FragmentScope
    @Binds
    abstract CategoryContract.Presenter categoryPresenter(CategoryPresenter presenter);
}
