package com.mukeshteckwani.astro.astroapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mukeshteckwani on 21/12/17.
 */


public class TvGuideModel {

    @SerializedName("responseCode")
    @Expose
    private String responseCode;
    @SerializedName("responseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("getevent")
    @Expose
    private List<Getevent> getevent = null;

    public class VernacularDatum {

        @SerializedName("vernacularLanguage")
        @Expose
        private String vernacularLanguage;
        @SerializedName("vernacularProgrammeTitle")
        @Expose
        private String vernacularProgrammeTitle;
        @SerializedName("vernacularShortSynopsis")
        @Expose
        private String vernacularShortSynopsis;
        @SerializedName("vernacularLongSynopsis")
        @Expose
        private String vernacularLongSynopsis;
        @SerializedName("actors")
        @Expose
        private String actors;
        @SerializedName("directors")
        @Expose
        private String directors;
        @SerializedName("producers")
        @Expose
        private String producers;

        public String getVernacularLanguage() {
            return vernacularLanguage;
        }

        public String getVernacularProgrammeTitle() {
            return vernacularProgrammeTitle;
        }

        public String getVernacularShortSynopsis() {
            return vernacularShortSynopsis;
        }

        public String getVernacularLongSynopsis() {
            return vernacularLongSynopsis;
        }

        public String getActors() {
            return actors;
        }

        public String getDirectors() {
            return directors;
        }

        public String getProducers() {
            return producers;
        }
    }

    public class Getevent {

        @SerializedName("eventID")
        @Expose
        private String eventID;
        @SerializedName("channelId")
        @Expose
        private Integer channelId;
        @SerializedName("channelStbNumber")
        @Expose
        private String channelStbNumber;
        @SerializedName("channelHD")
        @Expose
        private String channelHD;
        @SerializedName("channelTitle")
        @Expose
        private String channelTitle;
        @SerializedName("epgEventImage")
        @Expose
        private Object epgEventImage;
        @SerializedName("certification")
        @Expose
        private String certification;
        @SerializedName("displayDateTimeUtc")
        @Expose
        private String displayDateTimeUtc;
        @SerializedName("displayDateTime")
        @Expose
        private String displayDateTime;
        @SerializedName("displayDuration")
        @Expose
        private String displayDuration;
        @SerializedName("siTrafficKey")
        @Expose
        private String siTrafficKey;
        @SerializedName("programmeTitle")
        @Expose
        private String programmeTitle;
        @SerializedName("programmeId")
        @Expose
        private String programmeId;
        @SerializedName("episodeId")
        @Expose
        private String episodeId;
        @SerializedName("shortSynopsis")
        @Expose
        private String shortSynopsis;
        @SerializedName("longSynopsis")
        @Expose
        private Object longSynopsis;
        @SerializedName("actors")
        @Expose
        private String actors;
        @SerializedName("directors")
        @Expose
        private String directors;
        @SerializedName("producers")
        @Expose
        private String producers;
        @SerializedName("genre")
        @Expose
        private String genre;
        @SerializedName("subGenre")
        @Expose
        private String subGenre;
        @SerializedName("live")
        @Expose
        private Boolean live;
        @SerializedName("premier")
        @Expose
        private Boolean premier;
        @SerializedName("ottBlackout")
        @Expose
        private Boolean ottBlackout;
        @SerializedName("highlight")
        @Expose
        private Object highlight;
        @SerializedName("contentId")
        @Expose
        private Object contentId;
        @SerializedName("groupKey")
        @Expose
        private Integer groupKey;
        @SerializedName("vernacularData")
        @Expose
        private List<VernacularDatum> vernacularData = null;

        public String getEventID() {
            return eventID;
        }

        public Integer getChannelId() {
            return channelId;
        }

        public String getChannelStbNumber() {
            return channelStbNumber;
        }

        public String getChannelHD() {
            return channelHD;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public Object getEpgEventImage() {
            return epgEventImage;
        }

        public String getCertification() {
            return certification;
        }

        public String getDisplayDateTimeUtc() {
            return displayDateTimeUtc;
        }

        public String getDisplayDateTime() {
            return displayDateTime;
        }

        public String getDisplayDuration() {
            return displayDuration;
        }

        public String getSiTrafficKey() {
            return siTrafficKey;
        }

        public String getProgrammeTitle() {
            return programmeTitle;
        }

        public String getProgrammeId() {
            return programmeId;
        }

        public String getEpisodeId() {
            return episodeId;
        }

        public String getShortSynopsis() {
            return shortSynopsis;
        }

        public Object getLongSynopsis() {
            return longSynopsis;
        }

        public String getActors() {
            return actors;
        }

        public String getDirectors() {
            return directors;
        }

        public String getProducers() {
            return producers;
        }

        public String getGenre() {
            return genre;
        }

        public String getSubGenre() {
            return subGenre;
        }

        public Boolean getLive() {
            return live;
        }

        public Boolean getPremier() {
            return premier;
        }

        public Boolean getOttBlackout() {
            return ottBlackout;
        }

        public Object getHighlight() {
            return highlight;
        }

        public Object getContentId() {
            return contentId;
        }

        public Integer getGroupKey() {
            return groupKey;
        }

        public List<VernacularDatum> getVernacularData() {
            return vernacularData;
        }
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Getevent> getGetevent() {
        return getevent;
    }
}