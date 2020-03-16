package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ForgetPwdRequest {

    /**
     * UserName : khoiln218@gmail.com
     */

    private String UserName;

    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
