package com.android.emilany.trademe.views.subcategories.di;

import com.android.emilany.trademe.di.AppComponent;
import com.android.emilany.trademe.di.FragmentScope;
import com.android.emilany.trademe.views.subcategories.SubcategoryFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = SubcategoryModule.class)
public interface SubcategoryComponent {

    void inject(SubcategoryFragment subcategoryFragment);
}
