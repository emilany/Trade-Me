package com.android.emilany.trademe.views.subcategories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.R;
import com.android.emilany.trademe.TradeMeApplication;
import com.android.emilany.trademe.models.ListingItem;
import com.android.emilany.trademe.views.MainActivity;
import com.android.emilany.trademe.views.listings.ListingAdapter;
import com.android.emilany.trademe.models.Category;
import com.android.emilany.trademe.views.subcategories.di.DaggerSubcategoryComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubcategoryFragment extends Fragment implements SubcategoryContract.View {

    @BindView(R.id.textview_subcategory_listingprompt)
    TextView tvListingPrompt;
    @BindView(R.id.textview_subcategory_heading)
    TextView tvSubcategoryHeading;
    @BindView(R.id.recyclerview_subcategory_subcategorylist)
    RecyclerView rvSubcategoryList;
    @BindView(R.id.recyclerview_listings_listingslist)
    RecyclerView rvListingsList;
    @BindView(R.id.linearlayout_subcategory_subcategorylist)
    LinearLayout llSubcategoryLayout;
    @BindView(R.id.swiperefreshlayout_listings_layout)
    SwipeRefreshLayout srlListingsLayout;

    @Inject
    SubcategoryContract.Presenter presenter;

    private Category subcategory;

    private Unbinder unbinder;

    private SubcategoryFragmentListener listener;

    public interface SubcategoryFragmentListener {
        void onSubcategorySelected(Category subcategory);
        void onListingSelected(ListingItem listingItem);
    }

    public static SubcategoryFragment newInstance(@Nullable Category category) {
        SubcategoryFragment fragment = new SubcategoryFragment();
        Bundle bundle = new Bundle();

        if (category != null) {
            bundle.putSerializable(Constants.Named.SUBCATEGORY_ARGUMENT, category);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SubcategoryFragmentListener) {
            listener = (SubcategoryFragmentListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory, container, false);
        unbinder = ButterKnife.bind(this, view);

        DaggerSubcategoryComponent.builder()
                .appComponent(((TradeMeApplication) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);

        subcategory = (Category) getArguments().getSerializable(Constants.Named.SUBCATEGORY_ARGUMENT);

        srlListingsLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadListings(subcategory);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.takeView(this);
        presenter.loadListings(subcategory);
    }

    @Override
    public void onPause() {
        presenter.dropView();
        super.onPause();
    }

    @Override
    public void showSubcategory() {
        if (!((MainActivity) getActivity()).isTwoPane() && subcategory != null && !subcategory.isLeaf()) {
            tvSubcategoryHeading.setText(subcategory.getName());

            SubcategoryAdapter subcategoryAdapter = new SubcategoryAdapter(getActivity(),
                    new SubcategoryAdapter.OnSubcategoryItemClickListener() {

                @Override
                public void onSubcategoryItemClicked(Category subcategory) {
                    listener.onSubcategorySelected(subcategory);
                }
            });

            rvSubcategoryList.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
            rvSubcategoryList.setAdapter(subcategoryAdapter);
            subcategoryAdapter.setItems(subcategory.getSubcategories());
        } else {
            llSubcategoryLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showListings(List<ListingItem> listingList) {
        if (listingList != null && listingList.size() > 0) {
            srlListingsLayout.setVisibility(View.VISIBLE);
            tvListingPrompt.setVisibility(View.GONE);

            ListingAdapter listingAdapter = new ListingAdapter(getActivity(),
                    new ListingAdapter.OnListingItemClickListener() {

                @Override
                public void onItemListingClicked(ListingItem listing) {
                    listener.onListingSelected(listing);
                }
            });

            rvListingsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            rvListingsList.setAdapter(listingAdapter);
            listingAdapter.setItems(listingList);
        } else {
            srlListingsLayout.setVisibility(View.GONE);
            tvListingPrompt.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgress(final boolean show) {
        srlListingsLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        srlListingsLayout.post(new Runnable() {
            @Override
            public void run() {
                srlListingsLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.detail_cannot_load, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}
