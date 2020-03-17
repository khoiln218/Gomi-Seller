package vn.gomicorp.seller.data.source.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequest {
    /**
     * CountryId : 1
     * DeviceToken : e9WgXLin6fg:APA91bF582iyWWS0d4st83JspciRk2BSnP9YGGVdi2iUZcpzZNET4yx2Y0vyJySSMsH4T6xcteBeGTwZ2YHfLlD8yy4qhVc6YJ93Q2NsyC1Np11CsBXIDf6XLwrd_tgRbJrrhwfZY55l
     * DeviceVersion : Android 10, API 29
     * Email : khoiln218@gmail.com
     * FullName : Khoi Le
     * Password : 123456
     * PhoneNumber : 0937001038
     * CertificationCode : 333411
     */

    private String Email;
    private String FullName;
    private byte Gender;
    private String BirthDay;
    private String PhoneNumber;
    private int CountryId;
    private String Password;

    private String DeviceToken;
    private String DeviceVersion;
    private String CertificationCode;

    @JsonProperty("Email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @JsonProperty("FullName")
    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    @JsonProperty("Gender")
    public byte getGender() {
        return Gender;
    }

    public void setGender(byte gender) {
        Gender = gender;
    }

    @JsonProperty("BirthDay")
    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    @JsonProperty("PhoneNumber")
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @JsonProperty("CountryId")
    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    @JsonProperty("Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @JsonProperty("DeviceToken")
    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    @JsonProperty("DeviceVersion")
    public String getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        DeviceVersion = deviceVersion;
    }

    @JsonProperty("CertificationCode")
    public String getCertificationCode() {
        return CertificationCode;
    }

    public void setCertificationCode(String CertificationCode) {
        this.CertificationCode = CertificationCode;
    }
}
