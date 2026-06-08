package com.mukeshteckwani.astro.astroapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

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

    public static class Channel {
        public Channel() {
            // Default constructor required for Firebase DataSnapshot.getValue(Channel.class)
        }

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

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Channel channel = (Channel) o;
            return Objects.equals(channelId, channel.channelId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(channelId);
        }
    }
}
