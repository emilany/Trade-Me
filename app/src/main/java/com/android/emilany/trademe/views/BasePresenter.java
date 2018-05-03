package com.android.emilany.trademe.views;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}