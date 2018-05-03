package com.android.emilany.trademe.di;

import com.android.emilany.trademe.api.ApiInterface;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class, NetworkModule.class })
public interface AppComponent {

    ApiInterface getApiInterface();
}
