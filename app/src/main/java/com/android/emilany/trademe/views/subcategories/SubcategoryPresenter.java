package com.android.emilany.trademe.views.subcategories;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.api.ApiCallback;
import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.models.Category;
import com.android.emilany.trademe.models.GeneralListing;
import com.android.emilany.trademe.models.ListingItem;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class SubcategoryPresenter implements SubcategoryContract.Presenter {

    private SubcategoryContract.View view;

    private ApiInterface apiInterface;

    @Inject
    public SubcategoryPresenter(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void takeView(SubcategoryContract.View view) {
        this.view = view;
        this.view.showSubcategory();
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void loadListings(Category subcategory) {
        view.showProgress(true);

        Call<GeneralListing> call = apiInterface.searchListing(
                checkSubcategory(subcategory), Constants.Values.LISTING_COUNT);
        call.enqueue(new ApiCallback<GeneralListing>() {
            @Override
            protected void onFailedResponse(Throwable t) {
                view.showProgress(false);
                view.showError();
            }

            @Override
            protected void onPositiveResponse(Response<GeneralListing> response) {
                List<ListingItem> listingList = response.body().getListingItems();
                view.showListings(listingList);
                view.showProgress(false);
            }

            @Override
            protected void onNegativeResponse(Response<GeneralListing> response) {
                view.showProgress(false);
                view.showError();
            }
        });
    }

    private String checkSubcategory(Category subcategory) {
        return subcategory == null ? "" : subcategory.getNumber();
    }
}
