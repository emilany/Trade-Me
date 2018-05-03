package com.android.emilany.trademe.views.listings;

import com.android.emilany.trademe.api.ApiCallback;
import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.models.Listing;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class ListingPresenter implements ListingContract.Presenter {

    private ListingContract.View view;

    private ApiInterface apiInterface;

    @Inject
    public ListingPresenter(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void takeView(ListingContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void loadListing(int listingId) {
        Call<Listing> call = apiInterface.getListing(listingId);
        call.enqueue(new ApiCallback<Listing>() {
            @Override
            protected void onFailedResponse(Throwable t) {
                view.showError();
            }

            @Override
            protected void onPositiveResponse(Response<Listing> response) {
                Listing listing = response.body();
                view.showListing(listing);
            }

            @Override
            protected void onNegativeResponse(Response<Listing> response) {
                view.showError();
            }
        });
    }
}
