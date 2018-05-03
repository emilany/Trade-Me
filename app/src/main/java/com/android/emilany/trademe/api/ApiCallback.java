package com.android.emilany.trademe.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onPositiveResponse(response);
        } else {
            onNegativeResponse(response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailedResponse(t);
    }

    protected abstract void onFailedResponse(Throwable t);

    protected abstract void onPositiveResponse(Response<T> response);

    protected abstract void onNegativeResponse(Response<T> response);

}
