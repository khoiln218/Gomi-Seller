package vn.gomicorp.seller.utils;

public interface GomiConstants {
    boolean TEST = false;

    String SIMPLE_DATE_FORMAT = "yyyy.MM.dd";
    int MAX_CHAR = 500;

    // REQUEST
    int REQUEST_PERMISSION_SETTING = 100;
    int REQUEST_CAMERA = 102;
    int REQUEST_GALLERY = 103;
    int REQUEST_APP_UPDATE = 104;

    //REQUEST CODE
    int RC_SELLER_BANK = 201;
    int RC_PRODUCT = 202;

    //EXTRA
    String EXTRA_PARCELABLE = "EXTRA_PARCELABLE";
    String EXTRA_TYPE = "EXTRA_TYPE";
    String EXTRA_ID = "EXTRA_ID";
    String EXTRA_TITLE = "EXTRA_TITLE";
}