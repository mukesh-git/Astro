package com.mukeshteckwani.astro.astroapp.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mukeshteckwani.astro.astroapp.R;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public class RetorfitCallback<T> implements Callback<T> {

    private final Listener<T> listener;

    public interface Listener<T> {

        void onResponse(Call<T> call, T response);

        void onFailure(Call<T> call, Throwable t);

        void onResponseHeaders(Map<String, List<String>> headers);
    }

    private Context context;

    public RetorfitCallback(Context context, Listener<T> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> retrofitResponse) {
        if (retrofitResponse.isSuccessful()) {
            listener.onResponse(call,retrofitResponse.body());
        } else if (listener != null) {
            listener.onFailure(call, new Throwable(context.getString(R.string.error)));
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        listener.onFailure(call, new Throwable(context.getString(R.string.error)));
    }
}
