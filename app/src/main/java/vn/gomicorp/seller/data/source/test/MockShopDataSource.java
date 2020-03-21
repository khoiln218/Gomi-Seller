package vn.gomicorp.seller.data.source.test;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopDataSource;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Shop;

/**
 * Created by KHOI LE on 3/21/2020.
 */
public class MockShopDataSource implements ShopDataSource {
    String verifyJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": null,\n" +
            "    \"TotalRows\": 0\n" +
            "}";
    String createJson = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": \"OK\",\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"Id\": \"f3bcb9c7-e7e2-44c0-84a3-d9473afcf18e\",\n" +
            "        \"UserId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"Cover\": \"\",\n" +
            "        \"Avatar\": \"\",\n" +
            "        \"ShopName\": \"Khoi Le\",\n" +
            "        \"WebAddress\": \"khoile000\",\n" +
            "        \"Description\": \"fashion\",\n" +
            "        \"CountryId\": 1,\n" +
            "        \"ProvinceId\": 2,\n" +
            "        \"DistrictId\": 215,\n" +
            "        \"CreatedDate\": \"2020-03-20T12:22:42.63\",\n" +
            "        \"ProductId\": \"00000000-0000-0000-0000-000000000000\",\n" +
            "        \"CountryName\": \"Việt Nam\",\n" +
            "        \"ProvinceName\": \"Hồ Chí Minh\",\n" +
            "        \"DistrictName\": \"Quận Tân Bình\",\n" +
            "        \"PointBalance\": 0.0,\n" +
            "        \"Visits\": 0,\n" +
            "        \"Follows\": 0,\n" +
            "        \"Followed\": false\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    @Override
    public void verifySellerUrl(VerifyUrlRequest request, final ResultListener<ResponseData> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onLoaded(new Gson().fromJson(verifyJson, ResponseData.class));
            }
        }, 2000);
    }

    @Override
    public void create(CreateShopRequest request, ResultListener<ResponseData<Shop>> callback) {
        excute(createJson, callback);
    }

    private void excute(final String jsonData, final ResultListener<ResponseData<Shop>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("excute", "DATA: " + jsonData);
                Gson gson = new GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
                callback.onLoaded(gson.<ResponseData<Shop>>fromJson(jsonData, new TypeToken<ResponseData<Shop>>() {
                }.getType()));
            }
        }, 400);
    }
}
