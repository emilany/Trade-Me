package com.android.emilany.trademe;

public final class Constants {
    private Constants() {
    }

    public class Injection {
        private Injection() {
        }

        public static final String BASE_URL = "base_url";
        public static final String API_KEY = "trademe_api_key";
        public static final String API_SECRET = "trademe_api_secret";
    }

    public class Named {
        private Named() {
        }

        public static final String CATEGORY_ARGUMENT = "category_argument";
        public static final String SUBCATEGORY_ARGUMENT = "subcategory_argument";
        public static final String LISTING_ARGUMENT = "listing_argument";
        public static final String CATEGORIES_TAG = "categories_tag";
        public static final String SUBCATEGORIES_TAG = "subcategories_tag";
        public static final String LISTING_TAG = "listing_tag";
    }

    public class Values {
        private Values() {
        }

        public static final int LISTING_COUNT = 20;
        public static final String ROOT_CATEGORY = "0";
    }
}
