package vn.gomicorp.seller.data.source.test;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import vn.gomicorp.seller.data.LocationDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Location;

/**
 * Created by KHOI LE on 3/21/2020.
 */
public class MockLocationDataSource implements LocationDataSource {
    private final String jsonCountry = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": [\n" +
            "        {\n" +
            "            \"Code\": \"+84\",\n" +
            "            \"Flag\": \"http://192.168.0.149:2526/CountryFlag/vietnam.jpg\",\n" +
            "            \"Id\": 1,\n" +
            "            \"Name\": \"Việt Nam\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Code\": \"+82\",\n" +
            "            \"Flag\": \"http://192.168.0.149:2526/CountryFlag/korea.jpg\",\n" +
            "            \"Id\": 2,\n" +
            "            \"Name\": \"Hàn Quốc\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Code\": \"+86\",\n" +
            "            \"Flag\": \"http://192.168.0.149:2526/CountryFlag/china.jpg\",\n" +
            "            \"Id\": 3,\n" +
            "            \"Name\": \"Trung Quốc\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Code\": \"+81\",\n" +
            "            \"Flag\": \"http://192.168.0.149:2526/CountryFlag/japan.jpg\",\n" +
            "            \"Id\": 4,\n" +
            "            \"Name\": \"Nhật Bản\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    private final String jsonProvince = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": [\n" +
            "        {\n" +
            "            \"Id\": 1,\n" +
            "            \"Name\": \"Hà Nội\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 2,\n" +
            "            \"Name\": \"Hồ Chí Minh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 3,\n" +
            "            \"Name\": \"Hải Phòng\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 4,\n" +
            "            \"Name\": \"Đà Nẵng\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 5,\n" +
            "            \"Name\": \"Cần Thơ\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 6,\n" +
            "            \"Name\": \"An Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 7,\n" +
            "            \"Name\": \"Bà Rịa Vũng Tàu\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 8,\n" +
            "            \"Name\": \"Bắc Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 9,\n" +
            "            \"Name\": \"Bắc Kạn\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 10,\n" +
            "            \"Name\": \"Bạc Liêu\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 11,\n" +
            "            \"Name\": \"Bắc Ninh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 12,\n" +
            "            \"Name\": \"Bến Tre\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 13,\n" +
            "            \"Name\": \"Bình Định\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 14,\n" +
            "            \"Name\": \"Bình Dương\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 15,\n" +
            "            \"Name\": \"Bình Phước\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 16,\n" +
            "            \"Name\": \"Bình Thuận\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 17,\n" +
            "            \"Name\": \"Cà Mau\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 18,\n" +
            "            \"Name\": \"Cao Bằng\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 19,\n" +
            "            \"Name\": \"Đăk Lăk\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 20,\n" +
            "            \"Name\": \"Đăk Nông\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 21,\n" +
            "            \"Name\": \"Điện Biên\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 22,\n" +
            "            \"Name\": \"Đồng Nai\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 23,\n" +
            "            \"Name\": \"Đồng Tháp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 24,\n" +
            "            \"Name\": \"Gia Lai\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 25,\n" +
            "            \"Name\": \"Hà Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 26,\n" +
            "            \"Name\": \"Hà Nam\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 27,\n" +
            "            \"Name\": \"Hà Tĩnh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 28,\n" +
            "            \"Name\": \"Hải Dương\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 29,\n" +
            "            \"Name\": \"Hậu Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 30,\n" +
            "            \"Name\": \"Hòa Bình\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 31,\n" +
            "            \"Name\": \"Hưng Yên\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 32,\n" +
            "            \"Name\": \"Khánh Hòa\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 33,\n" +
            "            \"Name\": \"Kiên Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 34,\n" +
            "            \"Name\": \"Kon Tum\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 35,\n" +
            "            \"Name\": \"Lai Châu\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 36,\n" +
            "            \"Name\": \"Lâm Đồng\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 37,\n" +
            "            \"Name\": \"Lạng Sơn\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 38,\n" +
            "            \"Name\": \"Lào Cai\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 39,\n" +
            "            \"Name\": \"Long An\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 40,\n" +
            "            \"Name\": \"Nam Định\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 41,\n" +
            "            \"Name\": \"Nghệ An\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 42,\n" +
            "            \"Name\": \"Ninh Bình\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 43,\n" +
            "            \"Name\": \"Ninh Thuận\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 44,\n" +
            "            \"Name\": \"Phú Thọ\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 45,\n" +
            "            \"Name\": \"Phú Yên\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 46,\n" +
            "            \"Name\": \"Quảng Bình\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 47,\n" +
            "            \"Name\": \"Quảng Nam\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 48,\n" +
            "            \"Name\": \"Quảng Ngãi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 49,\n" +
            "            \"Name\": \"Quảng Ninh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 50,\n" +
            "            \"Name\": \"Quảng Trị\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 51,\n" +
            "            \"Name\": \"Sóc Trăng\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 52,\n" +
            "            \"Name\": \"Sơn La\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 53,\n" +
            "            \"Name\": \"Tây Ninh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 54,\n" +
            "            \"Name\": \"Thái Bình\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 55,\n" +
            "            \"Name\": \"Thái Nguyên\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 56,\n" +
            "            \"Name\": \"Thanh Hóa\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 57,\n" +
            "            \"Name\": \"Thừa Thiên Huế\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 58,\n" +
            "            \"Name\": \"Tiền Giang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 59,\n" +
            "            \"Name\": \"Trà Vinh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 60,\n" +
            "            \"Name\": \"Tuyên Quang\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 61,\n" +
            "            \"Name\": \"Vĩnh Long\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 62,\n" +
            "            \"Name\": \"Vĩnh Phúc\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 63,\n" +
            "            \"Name\": \"Yên Bái\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    private final String jsonDistrict = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": null,\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": [\n" +
            "        {\n" +
            "            \"Id\": 201,\n" +
            "            \"Name\": \"Quận 1\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 202,\n" +
            "            \"Name\": \"Quận 2\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 203,\n" +
            "            \"Name\": \"Quận 3\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 204,\n" +
            "            \"Name\": \"Quận 4\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 205,\n" +
            "            \"Name\": \"Quận 5\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 206,\n" +
            "            \"Name\": \"Quận 6\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 207,\n" +
            "            \"Name\": \"Quận 7\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 208,\n" +
            "            \"Name\": \"Quận 8\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 209,\n" +
            "            \"Name\": \"Quận 9\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 210,\n" +
            "            \"Name\": \"Quận 10\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 211,\n" +
            "            \"Name\": \"Quận 11\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 212,\n" +
            "            \"Name\": \"Quận 12\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 213,\n" +
            "            \"Name\": \"Quận Thủ Đức\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 214,\n" +
            "            \"Name\": \"Quận Tân Phú\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 215,\n" +
            "            \"Name\": \"Quận Tân Bình\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 216,\n" +
            "            \"Name\": \"Quận Phú Nhuận\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 217,\n" +
            "            \"Name\": \"Quận Gò Vấp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 218,\n" +
            "            \"Name\": \"Quận Bình Thạnh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 219,\n" +
            "            \"Name\": \"Quận Bình Tân\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 220,\n" +
            "            \"Name\": \"Huyện Nhà Bè\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 221,\n" +
            "            \"Name\": \"Huyện Hóc Môn\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 222,\n" +
            "            \"Name\": \"Huyện Củ Chi\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 223,\n" +
            "            \"Name\": \"Huyện Cần Giờ\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"Id\": 224,\n" +
            "            \"Name\": \"Huyện Bình Chánh\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    @Override
    public void getLocationCountry(final ResultListener<ResponseData<List<Location>>> callback) {
        excute(jsonCountry, callback);
    }

    @Override
    public void getLocationProvince(int id, final ResultListener<ResponseData<List<Location>>> callback) {
        excute(jsonProvince, callback);
    }

    @Override
    public void getLocationDistrict(int id, final ResultListener<ResponseData<List<Location>>> callback) {
        excute(jsonDistrict, callback);
    }

    private void excute(final String jsonData, final ResultListener<ResponseData<List<Location>>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("excute", "DATA: " + jsonData);
                Gson gson = new GsonBuilder()
                        .enableComplexMapKeySerialization()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
                callback.onLoaded(gson.<ResponseData<List<Location>>>fromJson(jsonData, new TypeToken<ResponseData<List<Location>>>() {
                }.getType()));
            }
        }, 400);
    }
}
