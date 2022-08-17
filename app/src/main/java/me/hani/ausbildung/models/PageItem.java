package me.hani.ausbildung.models;

import com.google.gson.annotations.SerializedName;

import static me.hani.ausbildung.extras.Constants.IMAGES_BASE_URL;

public class PageItem {

    @SerializedName("id")
    private int pageId;

    @SerializedName("title")
    private String pageTitle;

    @SerializedName("article_summary")
    private String articleSummary;

    @SerializedName("article")
    private String pageArticle;

    @SerializedName("img_url")
    private String pageImgUrl;

    @SerializedName("video_id")
    private String videoId;

    @SerializedName("updated_at")
    private String pageLastUpdate;

    public int getPageId() {
        return pageId;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getArticleSummary() {
        return articleSummary;
    }

    public String getPageArticle() {
        return pageArticle;
    }

    public String getPageImgUrl() {
        return IMAGES_BASE_URL +"pages/" +pageImgUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getPageLastUpdate() {
        return pageLastUpdate;
    }
}
