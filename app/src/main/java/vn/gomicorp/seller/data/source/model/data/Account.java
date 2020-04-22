package vn.gomicorp.seller.data.source.model.data;

import java.io.Serializable;

public class Account implements Serializable {

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
     * BirthDayLong: 1586883600
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
    private long BirthDayLong;

    public String getVerificationCode() {
        return VerificationCode;
    }

    public void setVerificationCode(String VerificationCode) {
        this.VerificationCode = VerificationCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }

    public Object getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String DeviceVersion) {
        this.DeviceVersion = DeviceVersion;
    }

    public boolean isPushNotify() {
        return PushNotify;
    }

    public void setPushNotify(boolean PushNotify) {
        this.PushNotify = PushNotify;
    }

    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String ReferralCode) {
        this.ReferralCode = ReferralCode;
    }

    public int getSellerLevel() {
        return SellerLevel;
    }

    public void setSellerLevel(int SellerLevel) {
        this.SellerLevel = SellerLevel;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    public String getReferralId() {
        return ReferralId;
    }

    public void setReferralId(String ReferralId) {
        this.ReferralId = ReferralId;
    }

    public String getSellerUrl() {
        return SellerUrl;
    }

    public void setSellerUrl(String SellerUrl) {
        this.SellerUrl = SellerUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int Gender) {
        this.Gender = Gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int CountryId) {
        this.CountryId = CountryId;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public int getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(int CountryCode) {
        this.CountryCode = CountryCode;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int AccountType) {
        this.AccountType = AccountType;
    }

    public long getBirthDayLong() {
        return BirthDayLong;
    }

    public void setBirthDayLong(long BirthDayLong) {
        this.BirthDayLong = BirthDayLong;
    }
}
