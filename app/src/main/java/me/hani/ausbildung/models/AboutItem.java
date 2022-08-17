package me.hani.ausbildung.models;

import com.google.gson.annotations.SerializedName;

public class AboutItem {
    @SerializedName("app_name")
    private String appName;
    @SerializedName("app_description")
    private String appDescription;
    @SerializedName("app_version")
    private String appVersion;
    @SerializedName("email")
    private String email;
    @SerializedName("website")
    private String website;
    @SerializedName("app_privacy_policy")
    private String appPrivacyPolicy;


    public String getAppName() {
        return appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getAppPrivacyPolicy() {
        return appPrivacyPolicy;
    }
}
