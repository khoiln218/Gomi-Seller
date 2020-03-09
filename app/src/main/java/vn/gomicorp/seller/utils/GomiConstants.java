package vn.gomicorp.seller.utils;

public interface GomiConstants {
    public static String BASE_URL = "http://192.168.0.161:2525/api/seller/";

    public static int HTTP_CONNECTION_TIME_OUT = 10; // 10 seconds
    public static int HTTP_READ_TIME_OUT = 60; // 60 seconds, 0 -> no time out
    public static int HTTP_WRITE_TIME_OUT = 60; // 60 seconds, 0 - > no time mout
}