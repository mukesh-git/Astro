package com.mukeshteckwani.astro.astroapp.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mukeshteckwani.astro.astroapp.BR;

import java.util.List;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public class ChannelsListModel {

    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public class Channel extends BaseObservable{

        @SerializedName("channelId")
        @Expose
        private Integer channelId;
        @SerializedName("channelTitle")
        @Expose
        private String channelTitle;
        @SerializedName("channelStbNumber")
        @Expose
        private Integer channelStbNumber;

        private boolean isChecked;

        public Integer getChannelId() {
            return channelId;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public Integer getChannelStbNumber() {
            return channelStbNumber;
        }

        @Bindable
        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
            notifyPropertyChanged(BR.checked);
        }
    }
}
