package com.blacklift.recipepuppy.data.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rogergarzon on 22/11/17.
 */

public class SearchResponse {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private Double version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("results")
    @Expose
    private List<ApiSearch> results = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<ApiSearch> getResults() {
        return results;
    }

    public void setResults(List<ApiSearch> results) {
        this.results = results;
    }

}