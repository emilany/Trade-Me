package com.android.emilany.trademe.api;

import com.android.emilany.trademe.models.Category;
import com.android.emilany.trademe.models.GeneralListing;
import com.android.emilany.trademe.models.Listing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("Categories/{number}.json")
    Call<Category> getCategory(@Path("number") String number);

    @GET("Listings/{listingId}.json")
    Call<Listing> getListing(@Path("listingId") Integer listingId);

    @GET("Search/General.json")
    Call<GeneralListing> searchListing(@Query("category") String category,
                                       @Query("rows") Integer listingCount);

}
