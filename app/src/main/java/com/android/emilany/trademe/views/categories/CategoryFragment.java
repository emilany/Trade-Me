package com.android.emilany.trademe.views.categories;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.R;
import com.android.emilany.trademe.TradeMeApplication;
import com.android.emilany.trademe.models.Category;
import com.android.emilany.trademe.views.categories.di.DaggerCategoryComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryFragment extends Fragment implements CategoryContract.View {

    @BindView(R.id.recyclerview_category_categorylist)
    RecyclerView rvCategoryList;
    @BindView(R.id.swiperefreshlayout_category_list)
    SwipeRefreshLayout srlCategoryLayout;

    @Inject
    CategoryContract.Presenter presenter;

    private String categoryId;

    private Unbinder unbinder;

    private OnCategorySelectedListener listener;

    public interface OnCategorySelectedListener {
        void onCategorySelected(Category category);
    }

    public static CategoryFragment newInstance(String categoryId) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle bundle = new Bundle();

        if (categoryId != null) {
            bundle.putString(Constants.Named.CATEGORY_ARGUMENT, categoryId);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnCategorySelectedListener) {
            listener = (OnCategorySelectedListener) context;
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);

        categoryId = getArguments().getString(Constants.Named.CATEGORY_ARGUMENT);

        DaggerCategoryComponent.builder()
                .appComponent(((TradeMeApplication) getActivity().getApplication()).getAppComponent())
                .build()
                .inject(this);

        srlCategoryLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadCategory();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.takeView(this);
        presenter.setCategoryId(categoryId);
        presenter.loadCategory();
    }

    @Override
    public void onPause() {
        presenter.dropView();
        super.onPause();
    }

    @Override
    public void showCategory(Category category) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), new CategoryAdapter.OnCategoryItemClickListener() {
            @Override
            public void onCategoryItemClicked(Category category) {
                presenter.setCategoryId(category.getNumber());
                listener.onCategorySelected(category);
            }
        });

        rvCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategoryList.setAdapter(categoryAdapter);
        categoryAdapter.setItems(category.getSubcategories());
    }

    @Override
    public void showCategoryOnBackPressed(Category category) {
        listener.onCategorySelected(category);
    }

    @Override
    public void showProgress(final boolean show) {
        srlCategoryLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent)
        );
        srlCategoryLayout.post(new Runnable() {
            @Override
            public void run() {
                srlCategoryLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), R.string.detail_cannot_load, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
        presenter.loadCategoryOnBackPressed();
    }

    @Override
    public void finish() {
        getActivity().finish();
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
