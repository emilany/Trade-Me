package com.android.emilany.trademe;

import android.app.Application;

import com.android.emilany.trademe.di.AppComponent;
import com.android.emilany.trademe.di.AppModule;
import com.android.emilany.trademe.di.DaggerAppComponent;
import com.android.emilany.trademe.di.NetworkModule;

public class TradeMeApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                        .appModule(new AppModule(this))
                        .networkModule(new NetworkModule())
                        .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
