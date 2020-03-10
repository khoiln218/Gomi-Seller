package vn.gomicorp.seller.signin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInRequest {

    /**
     * UserName : khoiln218@gmail.com
     * Password : 123456
     * DeviceVersion : Android 27
     * DeviceToken : 123456
     */

    private String UserName;
    private String Password;
    private String DeviceVersion;
    private String DeviceToken;

    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    @JsonProperty("Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @JsonProperty("DeviceVersion")
    public String getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String DeviceVersion) {
        this.DeviceVersion = DeviceVersion;
    }

    @JsonProperty("DeviceToken")
    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }
}
