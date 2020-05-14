package vn.gomisellers.apps.data.source.test;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.ShopDataSource;
import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.CreateShopRequest;
import vn.gomisellers.apps.data.source.model.api.MegaCategoryRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.UpdateShopRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyUrlRequest;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.Shop;

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

    String jsonCategory = "{\n" +
            "   \"Status\":true,\n" +
            "   \"Message\":\"OK\",\n" +
            "   \"Code\":200,\n" +
            "   \"Result\":[\n" +
            "      {\n" +
            "         \"Id\":1,\n" +
            "         \"Name\":\"Trang sức - Phụ kiện\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_trang-suc-phu-kien.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":2,\n" +
            "         \"Name\":\"Thời trang - Phụ kiện\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_thoi-trang-phu-kien.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":3,\n" +
            "         \"Name\":\"Làm đẹp - Sức khỏe\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_lam-dep-suc-khoe.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":4,\n" +
            "         \"Name\":\"Nhà cửa đời sống\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_nha-cua-doi-song.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":5,\n" +
            "         \"Name\":\"Dành cho thú cưng\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_danh-cho-thu-cung.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":6,\n" +
            "         \"Name\":\"Laptop - Thiết bị IT\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_laptop-thiet-bi-it.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":7,\n" +
            "         \"Name\":\"Phụ kiện - Thiết bị số\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_phu-kien-thiet-bi-so.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":8,\n" +
            "         \"Name\":\"Thể thao - Dã ngoại\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_the-thao-da-ngoai.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":9,\n" +
            "         \"Name\":\"Điện thoại - Máy tính bảng\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_dien-thoai-may-tinh-bang.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":10,\n" +
            "         \"Name\":\"Đồ chơi - Mẹ & Bé\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_do-choi-me-be.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":11,\n" +
            "         \"Name\":\"Điện gia dụng\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_dien-gia-dung.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":12,\n" +
            "         \"Name\":\"Thực phẩm\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_thuc-pham.png\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"Id\":13,\n" +
            "         \"Name\":\"Hỗ trợ tình dục\",\n" +
            "         \"Icon\":\"http://192.168.0.12:2526/Category/Icon/ic_ho-tro-tinh-duc.png\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"TotalRows\":0\n" +
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

    @Override
    public void findcatebytype(CategoryByIdRequest request, final ResultListener<ResponseData<List<Category>>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("excute", "DATA: " + jsonCategory);
                Gson gson = new GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
                callback.onLoaded(gson.<ResponseData<List<Category>>>fromJson(jsonCategory, new TypeToken<ResponseData<List<Category>>>() {
                }.getType()));
            }
        }, 400);
    }

    @Override
    public void megacategory(MegaCategoryRequest request, ResultListener<ResponseData<List<Category>>> callback) {

    }

    @Override
    public void findbyid(ShopRequest request, ResultListener<ResponseData<Shop>> callback) {

    }

    @Override
    public void updateinfo(UpdateShopRequest request, ResultListener<ResponseData<Shop>> callback) {

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
