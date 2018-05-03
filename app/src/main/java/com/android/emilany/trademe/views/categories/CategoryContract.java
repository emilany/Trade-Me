package com.android.emilany.trademe.views.categories;

import com.android.emilany.trademe.views.BasePresenter;
import com.android.emilany.trademe.views.BaseView;
import com.android.emilany.trademe.models.Category;

public interface CategoryContract {

    interface View extends BaseView {

        void showCategory(Category category);

        void showCategoryOnBackPressed(Category category);

        void showProgress(boolean show);

        void onBackPressed();

        void finish();
    }

    interface Presenter extends BasePresenter<View> {

        void setCategoryId(String categoryId);

        void loadCategory();

        void loadCategoryOnBackPressed();

        void onBackPressed();
    }
}
