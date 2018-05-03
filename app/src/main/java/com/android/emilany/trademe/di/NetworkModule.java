package com.android.emilany.trademe.di;

import android.content.Context;

import com.android.emilany.trademe.Constants;
import com.android.emilany.trademe.R;
import com.android.emilany.trademe.api.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Named(Constants.Injection.BASE_URL)
    public String provideBaseUrl(Context context) {
        return context.getString(R.string.base_url);
    }

    @Provides
    @Named(Constants.Injection.API_KEY)
    public String provideTradeMeApiKey(Context context) {
        return context.getString(R.string.api_key);
    }

    @Provides
    @Named(Constants.Injection.API_SECRET)
    public String provideTradeMeApiSecret(Context context) {
        return context.getString(R.string.api_secret);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@Named(Constants.Injection.API_KEY) final String apiKey,
                                            @Named(Constants.Injection.API_SECRET) final String apiSecret) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder()
                        .header("Authorization",
                                "OAuth oauth_consumer_key=\"" + apiKey + "\", oauth_signature_method=\"PLAINTEXT\", oauth_signature=\"" + apiSecret +"&\"");

                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        httpClient.interceptors().add(interceptor);

        return httpClient.build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson,
                                    OkHttpClient okHttpClient,
                                    @Named(Constants.Injection.BASE_URL) final String baseUrl) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public ApiInterface provideApiEndpointInterface(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }
}
