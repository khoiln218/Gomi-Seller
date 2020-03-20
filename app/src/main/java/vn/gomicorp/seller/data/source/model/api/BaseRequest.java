package vn.gomicorp.seller.data.source.model.api;

import vn.gomicorp.seller.EappsApplication;

/**
 * Created by KHOI LE on 3/20/2020.
 */
public class BaseRequest {

    /**
     * UserId : a4c1dd3d-c271-47af-ba14-85e115c61719
     */

    private String UserId;

    public BaseRequest() {
        UserId = EappsApplication.getPreferences().getUserId();
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }
}
