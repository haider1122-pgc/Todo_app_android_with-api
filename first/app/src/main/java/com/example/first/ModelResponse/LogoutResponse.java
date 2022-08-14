package com.example.first.ModelResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LogoutResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;

    /**
     * No args constructor for use in serialization
     *
     */
    public LogoutResponse() {
    }

    /**
     *
     * @param success
     */
    public LogoutResponse(Boolean success) {
        super();
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
