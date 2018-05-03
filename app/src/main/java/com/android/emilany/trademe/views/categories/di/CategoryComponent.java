package com.android.emilany.trademe.views.categories.di;

import com.android.emilany.trademe.di.AppComponent;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.categories.CategoryFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = CategoryModule.class)
public interface CategoryComponent {

    void inject(CategoryFragment categoryFragment);
}
