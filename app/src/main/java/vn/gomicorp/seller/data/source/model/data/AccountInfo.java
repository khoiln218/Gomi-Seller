package vn.gomicorp.seller.data.source.model.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfo implements Serializable {

    /**
     * VerificationCode : null
     * Password : null
     * NewPassword : null
     * DeviceToken : null
     * DeviceVersion : null
     * PushNotify : false
     * ReferralCode : EAAAAHPyPoTdzSeWxJDckO8Gek9WcZqVcu6KIQbyL7XnkOqVXTtXfApd9hLKKamsLzzOyqaNGGr56ko7SgFkcCANo5A=
     * SellerLevel : 0
     * ShopId : 00000000-0000-0000-0000-000000000000
     * ReferralId : 00000000-0000-0000-0000-000000000000
     * SellerUrl : https://gomisellers.vn/
     * UserId : 6e5967d3-7021-465d-a1fe-d0d6b1e61fb6
     * UserName : khoiln2186
     * FullName : khoi le
     * Email : khoiln2186
     * PhoneNumber : 1234566
     * Gender : 1
     * BirthDay : 27/11/1988
     * CountryId : 0
     * Avatar :
     * CountryCode : null
     * AccountType : 0
     */

    private String VerificationCode;
    private String Password;
    private String NewPassword;
    private String DeviceToken;
    private String DeviceVersion;
    private boolean PushNotify;
    private String ReferralCode;
    private int SellerLevel;
    private String ShopId;
    private String ReferralId;
    private String SellerUrl;
    private String UserId;
    private String UserName;
    private String FullName;
    private String Email;
    private String PhoneNumber;
    private int Gender;
    private String BirthDay;
    private int CountryId;
    private String Avatar;
    private int CountryCode;
    private int AccountType;

    @JsonProperty("VerificationCode")
    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }

    @JsonProperty("Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    @JsonProperty("NewPassword")
    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    @JsonProperty("DeviceToken")
    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }

    @JsonProperty("DeviceVersion")
    public Object getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String DeviceVersion) {
        this.DeviceVersion = DeviceVersion;
    }

    @JsonProperty("PushNotify")
    public boolean isPushNotify() {
        return PushNotify;
    }

    public void setPushNotify(boolean PushNotify) {
        this.PushNotify = PushNotify;
    }

    @JsonProperty("ReferralCode")
    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String ReferralCode) {
        this.ReferralCode = ReferralCode;
    }

    @JsonProperty("SellerLevel")
    public int getSellerLevel() {
        return SellerLevel;
    }

    public void setSellerLevel(int SellerLevel) {
        this.SellerLevel = SellerLevel;
    }

    @JsonProperty("ShopId")
    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    @JsonProperty("ReferralId")
    public String getReferralId() {
        return ReferralId;
    }

    public void setReferralId(String ReferralId) {
        this.ReferralId = ReferralId;
    }

    @JsonProperty("SellerUrl")
    public String getSellerUrl() {
        return SellerUrl;
    }

    public void setSellerUrl(String SellerUrl) {
        this.SellerUrl = SellerUrl;
    }

    @JsonProperty("UserId")
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @JsonProperty("UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    @JsonProperty("FullName")
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @JsonProperty("PhoneNumber")
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    @JsonProperty("Gender")
    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    @JsonProperty("BirthDay")
    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }

    @JsonProperty("CountryId")
    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int CountryId) {
        this.CountryId = CountryId;
    }

    @JsonProperty("Avatar")
    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    @JsonProperty("CountryCode")
    public int getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(int CountryCode) {
        this.CountryCode = CountryCode;
    }

    @JsonProperty("AccountType")
    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int AccountType) {
        this.AccountType = AccountType;
    }
}
