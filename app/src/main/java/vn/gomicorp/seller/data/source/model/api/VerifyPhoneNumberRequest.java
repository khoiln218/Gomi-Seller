package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by KHOI LE on 3/17/2020.
 */
public class VerifyPhoneNumberRequest {

    /**
     * PhoneNumber : 0937001033
     */

    private String PhoneNumber;

    @JsonProperty("PhoneNumber")
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
}
