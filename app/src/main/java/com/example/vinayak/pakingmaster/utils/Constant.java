package com.example.vinayak.pakingmaster.utils;

public class Constant {

    public static final String BASE_URL = "http://packing.vishwanet.in/";
   //public static final String BASE_URL = "http://satvyk.vishwanet.in/";
   //public static final String BASE_URL = "https://packagetracker.000webhostapp.com/";
   // public static final String BASE_URL = "http://192.168.0.14/pack/";
    public static final String ADD_NEW_USER = BASE_URL.concat("adduser.php");
    public static final String CHECK_LOGIN_USER = BASE_URL.concat("authuser.php");
    public static final String GET_CUSTOMER_LIST = BASE_URL.concat("getcustomers.php");
    public static final String GET_ITEM_LIST = BASE_URL.concat("getStockItems.php");
    public static final String UPDATE_ITEM = "";
    public static final String DELETE_ITEM = BASE_URL.concat("deleteOrderItem.php");
    public static final String GET_ITEM_BY_BARCODE = BASE_URL.concat("getStockItemByBarcode.php");
    public static final String ADD_ORDER = BASE_URL.concat("addorder.php");
    public static final String UPDATE_ORDER = BASE_URL.concat("updateOrder.php");
    public static final String GET_CUSTOMER_PENDING_ORDER_LIST = BASE_URL.concat("getPendingOrderlist.php");
    public static final String GET_SLIP_DETAILS = BASE_URL.concat("getSlipDetails.php");
    public static final String SUBMIT_SLIP = BASE_URL.concat("submitOrder.php");
    public static final String DELETE_SLIP = BASE_URL.concat("deleteOrder.php");
    public static final String LOGOUT = BASE_URL.concat("userLogout.php");
    public static final String CHANGE_PASSWORD = BASE_URL.concat("userChangePassword.php");


 public static String userId = "";
    public static String userName = "";
    public static String slipNumberFromList = "";

    public static boolean isAllItemBoxNoFilled = true;
    public static boolean isAllItemQtyFilled = true;

}
