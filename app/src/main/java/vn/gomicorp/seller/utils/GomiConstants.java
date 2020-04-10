package vn.gomicorp.seller.utils;

public interface GomiConstants {
    public final boolean TEST = false;

    public static final String SIMPLE_DATE_FORMAT = "yyyy.MM.dd";

    // REQUEST
    public static final int REQUEST_PERMISSION_SETTING = 100;
    public static final int REQUEST_CAMERA = 102;
    public static final int REQUEST_GALLERY = 103;
    public static final int REQUEST_APP_UPDATE = 104;

    //REQUEST CODE
    int RC_SELLER_BANK = 201;
    int RC_PRODUCT = 202;

    //EXTRA
    String EXTRA_PARCELABLE = "EXTRA_PARCELABLE";
    String EXTRA_TYPE = "EXTRA_TYPE";
    String EXTRA_ID = "EXTRA_ID";
    String EXTRA_TITLE = "EXTRA_TITLE";
}