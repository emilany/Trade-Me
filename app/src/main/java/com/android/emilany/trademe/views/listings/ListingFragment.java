package com.android.emilany.trademe.views.listings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.R;
import com.android.emilany.trademe.TradeMeApplication;
import com.android.emilany.trademe.models.Listing;
import com.android.emilany.trademe.models.ListingItem;
import com.android.emilany.trademe.models.Photo;
import com.android.emilany.trademe.views.listings.di.DaggerListingComponent;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListingFragment extends Fragment implements ListingContract.View {

    @BindView(R.id.textview_listing_listingName)
    TextView tvListingName;
    @BindView(R.id.textview_listing_listingNumber)
    TextView tvListingNumber;
    @BindView(R.id.textview_listing_listingRegion)
    TextView tvListingRegion;
    @BindView(R.id.textview_listing_listingBody)
    TextView tvListingBody;
    @BindView(R.id.textview_listing_listingPrice)
    TextView tvListingPrice;
    @BindView(R.id.textview_listing_listingPayment)
    TextView tvListingPayment;
    @BindView(R.id.sliderlayout_listing_listingImage)
    SliderLayout slListingImage;
    @BindView(R.id.linearlayout_listing_listingdetail)
    LinearLayout llListingDetails;

    @Inject
    ListingContract.Presenter presenter;

    private ListingItem listingItem;

    private Unbinder unbinder;

    public static ListingFragment newInstance(ListingItem listingItem) {
        ListingFragment fragment = new ListingFragment();
        Bundle bundle = new Bundle();

        if (listingItem != null) {
            bundle.putSerializable(Constants.Named.LISTING_ARGUMENT, listingItem);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing, container, false);
        unbinder = ButterKnife.bind(this, view);

        DaggerListingComponent.builder()
                .appComponent(((TradeMeApplication) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);

        listingItem = (ListingItem) getArguments().getSerializable(Constants.Named.LISTING_ARGUMENT);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.takeView(this);
        presenter.loadListing(listingItem.getId());
    }

    @Override
    public void onPause() {
        presenter.dropView();
        super.onPause();
    }

    @Override
    public void showListing(Listing listing) {
        llListingDetails.setVisibility(View.VISIBLE);

        tvListingName.setText(formatDisplay(listing.getTitle()));
        tvListingNumber.setText(formatDisplay("" + listing.getId()));
        tvListingRegion.setText(formatDisplay(listing.getRegion()));
        tvListingBody.setText(formatDisplay(listing.getBody()));
        tvListingPrice.setText(formatDisplay(listing.getPrice()));
        tvListingPayment.setText(formatDisplay(listing.getPaymentOptions()));

        if (listing.getPhotoList() != null && !listing.getPhotoList().isEmpty()) {
            slListingImage.setVisibility(View.VISIBLE);

            for (Photo photo : listing.getPhotoList()) {
                DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                sliderView.image(photo.getValue().getMedium());
                slListingImage.addSlider(sliderView);
            }
        }
        slListingImage.setPresetTransformer(SliderLayout.Transformer.Default);
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.detail_cannot_load, Toast.LENGTH_SHORT).show();
    }

    private String formatDisplay(String string) {
        return string == null || string.length() == 0 ? "-" : string;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
