package vn.gomisellers.apps.data.source.model.api;

public class SignInRequest {

    /**
     * UserName : khoiln218@gmail.com
     * Password : 123456
     * DeviceVersion : Android 10, API 29
     * DeviceToken : e9WgXLin6fg:APA91bF582iyWWS0d4st83JspciRk2BSnP9YGGVdi2iUZcpzZNET4yx2Y0vyJySSMsH4T6xcteBeGTwZ2YHfLlD8yy4qhVc6YJ93Q2NsyC1Np11CsBXIDf6XLwrd_tgRbJrrhwfZY55l
     */

    private String UserName;
    private String Password;
    private String DeviceVersion;
    private String DeviceToken;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String DeviceVersion) {
        this.DeviceVersion = DeviceVersion;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }
}
