package com.mukeshteckwani.astro.astroapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.webhelper.RetrofitService;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideViewModel extends AndroidViewModel{

    public TvGuideViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<TvGuideModel> getTvGuide(String periodStart, String periodEnd, String channelIds) {
        MutableLiveData<TvGuideModel> livedata = new MutableLiveData<>();
        RetorfitCallback<TvGuideModel> callback = new RetorfitCallback<>(getApplication(),
                new RetorfitCallback.Listener<TvGuideModel>() {
                    @Override
                    public void onResponse(Call<TvGuideModel> call, TvGuideModel response) {
                        livedata.setValue(response);
                    }

                    @Override
                    public void onFailure(Call<TvGuideModel> call, Throwable t) {
                        livedata.setValue(null);
                    }

                    @Override
                    public void onResponseHeaders(Map<String, List<String>> headers) {

                    }
                });
        RetrofitService.getApiService().getTvGuide(periodStart,periodEnd,channelIds).enqueue(callback);
        return livedata;
    }
}
