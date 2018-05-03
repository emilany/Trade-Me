package com.android.emilany.trademe.views.categories;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.api.ApiCallback;
import com.android.emilany.trademe.api.ApiInterface;
import com.android.emilany.trademe.models.Category;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View view;

    private ApiInterface apiInterface;

    private String currentCategory;

    @Inject
    public CategoryPresenter(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void takeView(CategoryContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void setCategoryId(String categoryId) {
        this.currentCategory = categoryId;
    }

    @Override
    public void loadCategory() {
        view.showProgress(true);

        Call<Category> call = apiInterface.getCategory(checkCategory(currentCategory));
        call.enqueue(new ApiCallback<Category>() {
            @Override
            protected void onFailedResponse(Throwable t) {
                view.showProgress(false);
                view.showError();
            }

            @Override
            protected void onPositiveResponse(Response<Category> response) {
                Category category = response.body();
                view.showCategory(category);
                view.showProgress(false);
            }

            @Override
            protected void onNegativeResponse(Response<Category> response) {
                view.showProgress(false);
                view.showError();
            }
        });
    }

    @Override
    public void loadCategoryOnBackPressed() {
        Call<Category> call = apiInterface.getCategory(currentCategory);
        call.enqueue(new ApiCallback<Category>() {
            @Override
            protected void onFailedResponse(Throwable t) {
            }

            @Override
            protected void onPositiveResponse(Response<Category> response) {
                Category category = response.body();
                view.showCategoryOnBackPressed(category);
            }

            @Override
            protected void onNegativeResponse(Response<Category> response) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (currentCategory.isEmpty()) {
            view.finish();
        } else {
            loadRootData();
        }
    }

    private void loadRootData() {
        String[] categoryIds = currentCategory.split("-");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < categoryIds.length - 1; i++) {
            builder.append(categoryIds[i] + "-");
        }
        String categoryId = builder.toString();

        currentCategory = checkCategory(categoryId);
    }

    private String checkCategory(String categoryId) {
        return categoryId.isEmpty() ? Constants.Values.ROOT_CATEGORY : categoryId;
    }
}
