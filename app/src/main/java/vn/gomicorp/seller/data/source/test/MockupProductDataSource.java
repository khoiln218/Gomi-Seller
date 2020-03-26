package vn.gomicorp.seller.data.source.test;

import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vn.gomicorp.seller.data.ProductDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.IntroduceRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Introduce;
import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class MockupProductDataSource implements ProductDataSource {
    String jsonIntroduce = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": \"OK\",\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"BannerList\": [],\n" +
            "        \"MegaCateList\": [\n" +
            "            {\n" +
            "                \"Id\": 1,\n" +
            "                \"Name\": \"Trang sức - Phụ kiện\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_trang-suc-phu-kien.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 2,\n" +
            "                \"Name\": \"Thời trang - Phụ kiện\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_thoi-trang-phu-kien.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 3,\n" +
            "                \"Name\": \"Làm đẹp - Sức khỏe\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_lam-dep-suc-khoe.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 4,\n" +
            "                \"Name\": \"Nhà cửa đời sống\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_nha-cua-doi-song.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 5,\n" +
            "                \"Name\": \"Dành cho thú cưng\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_danh-cho-thu-cung.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 6,\n" +
            "                \"Name\": \"Laptop - Thiết bị IT\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_laptop-thiet-bi-it.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 7,\n" +
            "                \"Name\": \"Phụ kiện - Thiết bị số\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_phu-kien-thiet-bi-so.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 8,\n" +
            "                \"Name\": \"Thể thao - Dã ngoại\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_the-thao-da-ngoai.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 9,\n" +
            "                \"Name\": \"Điện thoại - Máy tính bảng\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_dien-thoai-may-tinh-bang.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 10,\n" +
            "                \"Name\": \"Đồ chơi - Mẹ & Bé\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_do-choi-me-be.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 11,\n" +
            "                \"Name\": \"Điện gia dụng\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_dien-gia-dung.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 12,\n" +
            "                \"Name\": \"Thực phẩm\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_thuc-pham.png\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 13,\n" +
            "                \"Name\": \"Hỗ trợ tình dục\",\n" +
            "                \"Icon\": \"http://192.168.0.12:2526/Category/Icon/ic_ho-tro-tinh-duc.png\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"CollectionList\": [\n" +
            "            {\n" +
            "                \"Id\": 1,\n" +
            "                \"Name\": \"Sản phẩm mới\",\n" +
            "                \"ProductList\": [                    \n" +
            "                    {\n" +
            "                        \"Id\": \"ff961d03-fa40-4988-967a-3cf4c087d176\",\n" +
            "                        \"Name\": \"Lăn Loại Bỏ Tế Bào Chết Và Dưỡng Ẩm Cho Bàn Chân - Athis BHA+ Foot Stick Softening\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_0fea4887-8869-48b7-a6ae-1ea99aacaa95-637207134087729805.jpg\",\n" +
            "                        \"Url\": \"lan-loai-bo-te-bao-chet-va-duong-am-cho-ban-chan-athis-bha-foot-stick-softening/ff961d03-fa40-4988-967a-3cf4c087d176\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 199000.0000,\n" +
            "                        \"MarketPrice\": 199000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 19900.0000,\n" +
            "                        \"IsSelling\": 1,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "\t\t\t\t\t{\n" +
            "                        \"Id\": \"347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                        \"Name\": \"Xịt Trị Mụn Lưng Aetem Cleanback Mist\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg\",\n" +
            "                        \"Url\": \"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 339000.0000,\n" +
            "                        \"MarketPrice\": 339000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 33900.0000,\n" +
            "                        \"IsSelling\": 1,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"Id\": \"b561609b-7993-472c-a20c-acc3d02b4449\",\n" +
            "                        \"Name\": \"Neckle Ray\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_915402da-f321-4f38-b4c3-21806bc80633-637207143980832383.jpg\",\n" +
            "                        \"Url\": \"neckle-ray/b561609b-7993-472c-a20c-acc3d02b4449\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 19840000.0000,\n" +
            "                        \"MarketPrice\": 19840000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 1984000.0000,\n" +
            "                        \"IsSelling\": 0,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"Id\": \"622f926b-1d54-4567-b761-bd8e24506a54\",\n" +
            "                        \"Name\": \"Xịt Dưỡng Da Căng Bóng Dalra Dramatic Shiny Mist\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_de7d8b22-5339-4899-80af-138f540c9af6-637207158012552442.jpg\",\n" +
            "                        \"Url\": \"xit-duong-da-cang-bong-dalra-dramatic-shiny-mist/622f926b-1d54-4567-b761-bd8e24506a54\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 448000.0000,\n" +
            "                        \"MarketPrice\": 448000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 44800.0000,\n" +
            "                        \"IsSelling\": 0,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            {\n" +
            "                \"Id\": 2,\n" +
            "                \"Name\": \"Sản phẩm tiêu biểu\",\n" +
            "                \"ProductList\": [\n" +
            "                    {\n" +
            "                        \"Id\": \"347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                        \"Name\": \"Xịt Trị Mụn Lưng Aetem Cleanback Mist\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg\",\n" +
            "                        \"Url\": \"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 339000.0000,\n" +
            "                        \"MarketPrice\": 339000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 33900.0000,\n" +
            "                        \"IsSelling\": 1,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"Id\": \"7cb61dd9-96ac-4f5f-a475-33b047b01132\",\n" +
            "                        \"Name\": \"Mặt Nạ Tế Bào Gốc Ayo Premium Cell Essence Mask Pack (1 Box 5 miếng)\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_ecebdec7-24df-4b27-b234-0cfc6b7d4cff-637207137845275605.jpg\",\n" +
            "                        \"Url\": \"mat-na-te-bao-goc-ayo-premium-cell-essence-mask-pack-1-box-5-mieng/7cb61dd9-96ac-4f5f-a475-33b047b01132\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 599000.0000,\n" +
            "                        \"MarketPrice\": 599000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 59900.0000,\n" +
            "                        \"IsSelling\": 0,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"Id\": \"5d29f3cd-727f-480d-94ac-11fd61df3aaa\",\n" +
            "                        \"Name\": \"Platinum White\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_dbe127b1-312c-41c3-81fa-7172fed55e9b-637207145029137661.jpg\",\n" +
            "                        \"Url\": \"platinum-white/5d29f3cd-727f-480d-94ac-11fd61df3aaa\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 42600000.0000,\n" +
            "                        \"MarketPrice\": 42600000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 4260000.0000,\n" +
            "                        \"IsSelling\": 0,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"Id\": \"bc1df52d-5979-46e1-af53-202edaaef480\",\n" +
            "                        \"Name\": \"Bộ 2 Bàn Chải Thành Phần Than Đen Paul Medison Lacha ToothBrush\",\n" +
            "                        \"Description\": null,\n" +
            "                        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_63e6f84f-5752-4bbc-8b9f-2af8f525caff-637207166051545241.jpg\",\n" +
            "                        \"Url\": \"bo-2-ban-chai-thanh-phan-than-den-paul-medison-lacha-toothbrush/bc1df52d-5979-46e1-af53-202edaaef480\",\n" +
            "                        \"BrandId\": 0,\n" +
            "                        \"DetailId\": 0,\n" +
            "                        \"BrandName\": null,\n" +
            "                        \"BrandCountry\": null,\n" +
            "                        \"Price\": 156000.0000,\n" +
            "                        \"MarketPrice\": 156000.0000,\n" +
            "                        \"SaleOff\": 0,\n" +
            "                        \"Profit\": 15600.0000,\n" +
            "                        \"IsSelling\": 0,\n" +
            "                        \"AvgRating\": 0.0,\n" +
            "                        \"Purchases\": 0,\n" +
            "                        \"VideoReviews\": 0,\n" +
            "                        \"Comments\": 0,\n" +
            "                        \"AlsoSell\": 0,\n" +
            "                        \"Reviews\": 0,\n" +
            "                        \"SellerVideo\": null,\n" +
            "                        \"ImageList\": null,\n" +
            "                        \"Type\": null,\n" +
            "                        \"AttributeList\": null,\n" +
            "                        \"VideoReviewList\": null,\n" +
            "                        \"VariantList\": null,\n" +
            "                        \"CommentList\": null,\n" +
            "                        \"TotalPage\": 0.0\n" +
            "                    }\n" +
            "                ]\n" +
            "            }\n" +
            "        ],\n" +
            "        \"ProductSeen\": {\n" +
            "            \"ProductList\": [\n" +
            "                {\n" +
            "                    \"Id\": \"347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                    \"Name\": \"Xịt Trị Mụn Lưng Aetem Cleanback Mist\",\n" +
            "                    \"Description\": null,\n" +
            "                    \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg\",\n" +
            "                    \"Url\": \"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "                    \"BrandId\": 0,\n" +
            "                    \"DetailId\": 0,\n" +
            "                    \"BrandName\": null,\n" +
            "                    \"BrandCountry\": null,\n" +
            "                    \"Price\": 339000.0000,\n" +
            "                    \"MarketPrice\": 339000.0000,\n" +
            "                    \"SaleOff\": 0,\n" +
            "                    \"Profit\": 33900.0000,\n" +
            "                    \"IsSelling\": 1,\n" +
            "                    \"AvgRating\": 0.0,\n" +
            "                    \"Purchases\": 0,\n" +
            "                    \"VideoReviews\": 0,\n" +
            "                    \"Comments\": 0,\n" +
            "                    \"AlsoSell\": 0,\n" +
            "                    \"Reviews\": 0,\n" +
            "                    \"SellerVideo\": null,\n" +
            "                    \"ImageList\": null,\n" +
            "                    \"Type\": null,\n" +
            "                    \"AttributeList\": null,\n" +
            "                    \"VideoReviewList\": null,\n" +
            "                    \"VariantList\": null,\n" +
            "                    \"CommentList\": null,\n" +
            "                    \"TotalPage\": 0.0\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    String jsonSelect = "{\n" +
            "    \"Status\": true,\n" +
            "    \"Message\": \"Thêm sản phẩm thất bại.\",\n" +
            "    \"Code\": 200,\n" +
            "    \"Result\": {\n" +
            "        \"Id\": \"347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "        \"Name\": \"Xịt Trị Mụn Lưng Aetem Cleanback Mist\",\n" +
            "        \"Description\": null,\n" +
            "        \"Thumbnail\": \"http://192.168.0.12:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg\",\n" +
            "        \"Url\": \"xit-tri-mun-lung-aetem-cleanback-mist/347a551a-4744-4799-ba84-3613950362d3\",\n" +
            "        \"BrandId\": 1,\n" +
            "        \"DetailId\": 0,\n" +
            "        \"BrandName\": \"Aetem\",\n" +
            "        \"BrandCountry\": null,\n" +
            "        \"Price\": 339000.0000,\n" +
            "        \"MarketPrice\": 339000.0000,\n" +
            "        \"SaleOff\": 0,\n" +
            "        \"Profit\": 33900.0000,\n" +
            "        \"IsSelling\": 0,\n" +
            "        \"AvgRating\": 0.0,\n" +
            "        \"Purchases\": 0,\n" +
            "        \"VideoReviews\": 0,\n" +
            "        \"Comments\": 0,\n" +
            "        \"AlsoSell\": 1,\n" +
            "        \"Reviews\": 0,\n" +
            "        \"SellerVideo\": null,\n" +
            "        \"ImageList\": null,\n" +
            "        \"Type\": null,\n" +
            "        \"AttributeList\": null,\n" +
            "        \"VideoReviewList\": null,\n" +
            "        \"VariantList\": null,\n" +
            "        \"CommentList\": null,\n" +
            "        \"TotalPage\": 0.0\n" +
            "    },\n" +
            "    \"TotalRows\": 0\n" +
            "}";

    @Override
    public void introduce(IntroduceRequest request, ResultListener<ResponseData<Introduce>> callback) {
        exeIntroduce(jsonIntroduce, callback);
    }

    @Override
    public void select(ToggleProductRequest request, ResultListener<ResponseData<Product>> callback) {
        exeProduct(jsonSelect, request, callback);
    }

    private void exeIntroduce(final String jsonData, final ResultListener<ResponseData<Introduce>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("exeIntroduce", "DATA: " + jsonData);
                ResponseData<Introduce> res = new Gson().fromJson(jsonData, new TypeToken<ResponseData<Introduce>>() {
                }.getType());
                callback.onLoaded(res);
            }
        }, 400);
    }

    private void exeProduct(final String jsonData, final ToggleProductRequest request, final ResultListener<ResponseData<Product>> callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("exeProduct", "DATA: " + jsonData);
                ResponseData<Product> res = new Gson().fromJson(jsonData, new TypeToken<ResponseData<Product>>() {
                }.getType());
                res.getResult().setIsSelling(request.getIsSelling() == 1 ? 0 : 1);
                callback.onLoaded(res);
            }
        }, 400);
    }
}
