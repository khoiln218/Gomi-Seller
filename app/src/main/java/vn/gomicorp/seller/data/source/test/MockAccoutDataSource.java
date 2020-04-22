package vn.gomicorp.seller.data.source.test;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import vn.gomicorp.seller.data.AccountDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.AccountChangePasswordRequest;
import vn.gomicorp.seller.data.source.model.api.AccountRequest;
import vn.gomicorp.seller.data.source.model.api.AccountUpdateRequest;
import vn.gomicorp.seller.data.source.model.api.ChangeAvatarRequest;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignOutRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/21/2020.
 */
public class MockAccoutDataSource implements AccountDataSource {
    String signinandShopJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"VerificationCode\": null,\n" +
            "        \"Password\": null,\n" +
            "        \"NewPassword\": null,\n" +
            "        \"DeviceToken\": null,\n" +
            "        \"DeviceVersion\": null,\n" +
            "        \"PushNotify\": false,\n" +
            "        \"ReferralCode\": \"EAAAAP6OtsaVrQNUt0Iow64Rel6oPDiRqC5pIGYXhOd1M3rxpMN4O9e5frPj2sTzrSareXJM+965fXdCUt9YyR09uGA=\",\n" +
            "        \"SellerLevel\": 0,\n" +
            "        \"ShopId\": \"f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e\",\n" +
            "        \"ReferralId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"SellerUrl\": \"https://gomisellers.vn/\",\n" +
            "        \"UserId\": \"a4c1dd3d-c271-47af-ba14-85e115c61719\",\n" +
            "        \"UserName\": \"khoiln000@gmail.com\",\n" +
            "        \"FullName\": \"Khoi Le 000\",\n" +
            "        \"Email\": \"khoiln000@gmail.com\",\n" +
            "        \"PhoneNumber\": \"0937001000\",\n" +
            "        \"Gender\": 254,\n" +
            "        \"BirthDay\": \"\",\n" +
            "        \"CountryId\": 0,\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"CountryCode\": null,\n" +
            "        \"AccountType\": 0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String signinJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"VerificationCode\": null,\n" +
            "        \"Password\": null,\n" +
            "        \"NewPassword\": null,\n" +
            "        \"DeviceToken\": null,\n" +
            "        \"DeviceVersion\": null,\n" +
            "        \"PushNotify\": false,\n" +
            "        \"ReferralCode\": \"EAAAAP6OtsaVrQNUt0Iow64Rel6oPDiRqC5pIGYXhOd1M3rxpMN4O9e5frPj2sTzrSareXJM+965fXdCUt9YyR09uGA=\",\n" +
            "        \"SellerLevel\": 0,\n" +
            "        \"ShopId\": null,\n" +
            "        \"ReferralId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"SellerUrl\": \"https://gomisellers.vn/\",\n" +
            "        \"UserId\": \"a4c1dd3d-c271-47af-ba14-85e115c61719\",\n" +
            "        \"UserName\": \"khoiln000@gmail.com\",\n" +
            "        \"FullName\": \"Khoi Le 000\",\n" +
            "        \"Email\": \"khoiln000@gmail.com\",\n" +
            "        \"PhoneNumber\": \"0937001000\",\n" +
            "        \"Gender\": 254,\n" +
            "        \"BirthDay\": \"\",\n" +
            "        \"CountryId\": 0,\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"CountryCode\": null,\n" +
            "        \"AccountType\": 0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String signupJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"VerificationCode\": null,\n" +
            "        \"Password\": null,\n" +
            "        \"NewPassword\": null,\n" +
            "        \"DeviceToken\": null,\n" +
            "        \"DeviceVersion\": null,\n" +
            "        \"PushNotify\": false,\n" +
            "        \"ReferralCode\": \"EAAAACVVU6TRX46MvfjxpWYo0azEHEWwYaY70ju25FdZvA/FhTOdOAvroEIAca6/3W3EZkvOZUv9RX+oIvRdykt1J0E=\",\n" +
            "        \"SellerLevel\": 0,\n" +
            "        \"ShopId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"ReferralId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"SellerUrl\": \"https://gomisellers.vn/\",\n" +
            "        \"UserId\": \"b6e9e707-dcfe-4e76-9510-be653e32880d\",\n" +
            "        \"UserName\": \"khoiln001@gmail.com\",\n" +
            "        \"FullName\": \"Khoi Le\",\n" +
            "        \"Email\": \"khoiln001@gmail.com\",\n" +
            "        \"PhoneNumber\": \"0937001001\",\n" +
            "        \"Gender\": 254,\n" +
            "        \"BirthDay\": \"\",\n" +
            "        \"CountryId\": 0,\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"CountryCode\": null,\n" +
            "        \"AccountType\": 0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String forgetJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"VerificationCode\": null,\n" +
            "        \"Password\": null,\n" +
            "        \"NewPassword\": null,\n" +
            "        \"DeviceToken\": null,\n" +
            "        \"DeviceVersion\": null,\n" +
            "        \"PushNotify\": false,\n" +
            "        \"ReferralCode\": \"EAAAACVVU6TRX46MvfjxpWYo0azEHEWwYaY70ju25FdZvA/FhTOdOAvroEIAca6/3W3EZkvOZUv9RX+oIvRdykt1J0E=\",\n" +
            "        \"SellerLevel\": 0,\n" +
            "        \"ShopId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"ReferralId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"SellerUrl\": \"https://gomisellers.vn/\",\n" +
            "        \"UserId\": \"b6e9e707-dcfe-4e76-9510-be653e32880d\",\n" +
            "        \"UserName\": \"khoiln001@gmail.com\",\n" +
            "        \"FullName\": \"Khoi Le\",\n" +
            "        \"Email\": \"khoiln001@gmail.com\",\n" +
            "        \"PhoneNumber\": \"0937001001\",\n" +
            "        \"Gender\": 254,\n" +
            "        \"BirthDay\": \"\",\n" +
            "        \"CountryId\": 0,\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"CountryCode\": null,\n" +
            "        \"AccountType\": 0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String resetJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"VerificationCode\": null,\n" +
            "        \"Password\": null,\n" +
            "        \"NewPassword\": null,\n" +
            "        \"DeviceToken\": null,\n" +
            "        \"DeviceVersion\": null,\n" +
            "        \"PushNotify\": false,\n" +
            "        \"ReferralCode\": \"EAAAACVVU6TRX46MvfjxpWYo0azEHEWwYaY70ju25FdZvA/FhTOdOAvroEIAca6/3W3EZkvOZUv9RX+oIvRdykt1J0E=\",\n" +
            "        \"SellerLevel\": 0,\n" +
            "        \"ShopId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"ReferralId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"SellerUrl\": \"https://gomisellers.vn/\",\n" +
            "        \"UserId\": \"b6e9e707-dcfe-4e76-9510-be653e32880d\",\n" +
            "        \"UserName\": \"khoiln001@gmail.com\",\n" +
            "        \"FullName\": \"Khoi Le\",\n" +
            "        \"Email\": \"khoiln001@gmail.com\",\n" +
            "        \"PhoneNumber\": \"0937001001\",\n" +
            "        \"Gender\": 254,\n" +
            "        \"BirthDay\": \"\",\n" +
            "        \"CountryId\": 0,\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"CountryCode\": null,\n" +
            "        \"AccountType\": 0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String verifyJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": null,\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    @Override
    public void signin(SignInRequest request, ResultListener<ResponseData<Account>> callback) {
        excute(signinJson, callback);
    }

    @Override
    public void signup(SignUpRequest request, ResultListener<ResponseData<Account>> callback) {
        excute(signupJson, callback);
    }

    @Override
    public void forgetPwd(ForgetPwdRequest request, ResultListener<ResponseData<Account>> callback) {
        excute(forgetJson, callback);
    }

    @Override
    public void resetPwd(ResetPwdRequest request, ResultListener<ResponseData<Account>> callback) {
        excute(resetJson, callback);
    }

    @Override
    public void verifyPhoneNumber(VerifyPhoneNumberRequest request, ResultListener<ResponseData<Account>> callback) {
        excute(verifyJson, callback);
    }

    @Override
    public void findbyid(AccountRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void updateinfo(AccountUpdateRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void changepassword(AccountChangePasswordRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void changeavatar(ChangeAvatarRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    @Override
    public void logout(SignOutRequest request, ResultListener<ResponseData<Account>> callback) {

    }

    private void excute(final String jsonData, final ResultListener<ResponseData<Account>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("excute", "DATA: " + jsonData);
                Gson gson = new GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
                callback.onLoaded(gson.<ResponseData<Account>>fromJson(jsonData, new TypeToken<ResponseData<Account>>() {
                }.getType()));
            }
        }, 400);
    }
}
