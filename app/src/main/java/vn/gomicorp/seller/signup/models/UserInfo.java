package vn.gomicorp.seller.signup.models;

public class UserInfo {
    private String Email;
    private String FullName;
    private byte Gender;
    private String BirthDay;
    private String PhoneNumber;
    private int CountryId;
    private String Password;

    private String DeviceToken;
    private String DeviceVersion;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public byte getGender() {
        return Gender;
    }

    public void setGender(byte gender) {
        Gender = gender;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        DeviceVersion = deviceVersion;
    }
}
