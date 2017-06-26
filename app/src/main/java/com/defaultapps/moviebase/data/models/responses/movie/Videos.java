
package com.defaultapps.moviebase.data.models.responses.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos {

    @SerializedName("results")
    @Expose
    private List<VideoResult> videoResults = null;

    public List<VideoResult> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<VideoResult> videoResults) {
        this.videoResults = videoResults;
    }

}
