package com.mukeshteckwani.astro.astroapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.webhelper.RetrofitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public class ChannelsListViewModel extends AndroidViewModel {
    private DatabaseReference mFavDatabase;
    private DatabaseReference mSortDatabase;

    public ChannelsListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ChannelsListModel> getChannelList() {
        String url = "http://ams-api.astro.com.my/ams/v3/getChannelList";
        MutableLiveData<ChannelsListModel> liveData = new MutableLiveData<>();
        mFavDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");
        RetorfitCallback<ChannelsListModel> callback = new RetorfitCallback<>(getApplication(), new RetorfitCallback.Listener<ChannelsListModel>() {
            @Override
            public void onResponse(Call<ChannelsListModel> call, ChannelsListModel response) {
                liveData.setValue(response);
            }

            @Override
            public void onFailure(Call<ChannelsListModel> call, Throwable t) {
                liveData.setValue(null);
            }

            @Override
            public void onResponseHeaders(Map<String, List<String>> headers) {

            }
        });

        RetrofitService.getApiService().getChannelsList(url).enqueue(callback);
        return liveData;

    }

    public LiveData<List<ChannelsListModel.Channel>> getFavouriteChannels() {
        MutableLiveData<List<ChannelsListModel.Channel>> liveData = new MutableLiveData<>();
        mFavDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChannelsListModel.Channel> channelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    channelList.add(snapshot.getValue(ChannelsListModel.Channel.class));
                }
                liveData.setValue(channelList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                liveData.setValue(null);
            }
        });

        return liveData;
    }

    public void writeOrRemoveChannelsData(ChannelsListModel.Channel channel) {
        if (channel.isChecked())
            mFavDatabase.child(String.valueOf(channel.getChannelId())).setValue(channel);
        else
            mFavDatabase.child(String.valueOf(channel.getChannelId())).removeValue();
    }

    public LiveData<Integer> getSortOrder(){
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        mSortDatabase = FirebaseDatabase.getInstance().getReference().child("sort_order");
        mSortDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                liveData.setValue(dataSnapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                liveData.setValue(0);
            }
        });
        return liveData;
    }

    public void setSortOrder(int sortOrder){
        mSortDatabase.setValue(sortOrder);
    }

}
