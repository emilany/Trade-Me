package com.android.emilany.trademe.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.R;
import com.android.emilany.trademe.models.ListingItem;
import com.android.emilany.trademe.views.categories.CategoryFragment;
import com.android.emilany.trademe.views.listings.ListingFragment;
import com.android.emilany.trademe.views.subcategories.SubcategoryFragment;
import com.android.emilany.trademe.models.Category;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        CategoryFragment.OnCategorySelectedListener, SubcategoryFragment.SubcategoryFragmentListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (isTwoPane()) {
            if (getSupportFragmentManager().findFragmentByTag(Constants.Named.CATEGORIES_TAG) == null) {
                loadFragment(R.id.framelayout_main_categorylayout,
                            CategoryFragment.newInstance(Constants.Values.ROOT_CATEGORY),
                            Constants.Named.CATEGORIES_TAG);
            }

            if (getSupportFragmentManager().findFragmentByTag(Constants.Named.SUBCATEGORIES_TAG) == null) {
                loadFragment(R.id.framelayout_main_listinglayout,
                            SubcategoryFragment.newInstance(null),
                            Constants.Named.SUBCATEGORIES_TAG);
            }
        } else {
            if (getSupportFragmentManager().findFragmentByTag(Constants.Named.CATEGORIES_TAG) == null) {
                loadFragment(R.id.framelayout_main_layout,
                            CategoryFragment.newInstance(Constants.Values.ROOT_CATEGORY),
                            Constants.Named.CATEGORIES_TAG);
            }
        }
    }

    @Override
    public void onCategorySelected(Category category) {
        if (isTwoPane()) {
            if (!category.isLeaf()) {
                loadFragment(R.id.framelayout_main_categorylayout,
                            CategoryFragment.newInstance(category.getNumber()),
                            Constants.Named.CATEGORIES_TAG);
            }

            loadFragment(R.id.framelayout_main_listinglayout,
                        SubcategoryFragment.newInstance(category),
                        Constants.Named.SUBCATEGORIES_TAG);
        } else {
            loadFragment(R.id.framelayout_main_layout,
                                SubcategoryFragment.newInstance(category),
                                Constants.Named.SUBCATEGORIES_TAG);
        }
    }

    @Override
    public void onSubcategorySelected(Category subcategory) {
        if (!isTwoPane()) {
            loadFragment(R.id.framelayout_main_layout,
                        SubcategoryFragment.newInstance(subcategory),
                        Constants.Named.SUBCATEGORIES_TAG);
        }
    }

    @Override
    public void onListingSelected(ListingItem listingItem) {
        if (isTwoPane()) {
            loadFragment(R.id.framelayout_main_listinglayout,
                        ListingFragment.newInstance(listingItem),
                        Constants.Named.LISTING_TAG);
        } else {
            loadFragment(R.id.framelayout_main_layout,
                        ListingFragment.newInstance(listingItem),
                        Constants.Named.LISTING_TAG);
        }
    }

    private void loadFragment(int fragmentContainer, Fragment fragment, String fragmentTag) {
        getSupportFragmentManager().beginTransaction()
                .replace(fragmentContainer, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    public boolean isTwoPane() {
        return getResources().getBoolean(R.bool.isTwoPane);
    }

    @Override
    public void onBackPressed() {
        CategoryFragment categoryFragment =
                (CategoryFragment) getSupportFragmentManager().findFragmentByTag(Constants.Named.CATEGORIES_TAG);

        if (isTwoPane()) {
            ListingFragment listingFragment =
                    (ListingFragment) getSupportFragmentManager().findFragmentByTag(Constants.Named.LISTING_TAG);

            SubcategoryFragment subcategoryFragment =
                    (SubcategoryFragment) getSupportFragmentManager().findFragmentByTag(Constants.Named.SUBCATEGORIES_TAG);

            if (listingFragment != null && listingFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
            }

            if (subcategoryFragment != null && subcategoryFragment.isVisible()) {
                categoryFragment.onBackPressed();
            }

        } else {
            if (!categoryFragment.isVisible()) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }
}
