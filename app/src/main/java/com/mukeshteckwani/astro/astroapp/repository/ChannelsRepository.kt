package com.mukeshteckwani.astro.astroapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.webhelper.AstroAPi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ChannelsRepository {

    private final AstroAPi astroApi;
    private final DatabaseReference mFavDatabase;
    private final DatabaseReference mSortDatabase;

    @Inject
    public ChannelsRepository(AstroAPi astroApi) {
        this.astroApi = astroApi;
        this.mFavDatabase = FirebaseDatabase.getInstance().getReference().child("favourites");
        this.mSortDatabase = FirebaseDatabase.getInstance().getReference().child("sort_order");
    }

    public LiveData<ChannelsListModel> getChannelList() {
        String url = "http://ams-api.astro.com.my/ams/v3/getChannelList";
        MutableLiveData<ChannelsListModel> liveData = new MutableLiveData<>();
        
        astroApi.getChannelsList(url).enqueue(new Callback<ChannelsListModel>() {
            @Override
            public void onResponse(Call<ChannelsListModel> call, Response<ChannelsListModel> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChannelsListModel> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        
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
        if (channel.isChecked()) {
            mFavDatabase.child(String.valueOf(channel.getChannelId())).setValue(channel);
        } else {
            mFavDatabase.child(String.valueOf(channel.getChannelId())).removeValue();
        }
    }

    public LiveData<Integer> getSortOrder() {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        
        mSortDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Integer.class) != null) {
                    liveData.setValue(dataSnapshot.getValue(Integer.class));
                } else {
                    liveData.setValue(0);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                liveData.setValue(0);
            }
        });
        
        return liveData;
    }

    public void setSortOrder(int sortOrder) {
        mSortDatabase.setValue(sortOrder);
    }
}
