package hacker.l.emergency_help.utility;

import android.os.Environment;

/**
 * Created by lalit on 7/25/2017.
 */
public class Contants {
    public static final Boolean IS_DEBUG_LOG = true;

    public static final String LOG_TAG = "emergency";
    public static final String APP_NAME = "appName"; // Do NOT change this value; meant for user preference

    public static final String DEFAULT_APPLICATION_NAME = "emergency";

    public static final String APP_DIRECTORY = "/E" + DEFAULT_APPLICATION_NAME + "Directory/";
    public static final String DATABASE_NAME = "emergency.db";// Environment.getExternalStorageDirectory() +  APP_DIRECTORY + "evergreen.db";

    public static String SERVICE_BASE_URL = "http://lalitsingh2.esy.es/emergency-help/";

    public static String outputBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String outputDirectoryLocation = outputBasePath + "/evergreenUnzipped/";


    public static final int LIST_PAGE_SIZE = 50;
    public static String InternetMessage = " You are Online.";
    public static final String BAD_NETWORK_MESSAGE = "We are trying hard to get latest content but the network seems to be slow. " +
            "You may continue to use app and get latest with in the app itself. ";


    public static final String OFFLINE_MESSAGE = "Oops! You are Offline. Please check your Internet Connection.";
    public static final String SEND_MESSAGE = "OTP Send to Your Phone Number Successfully";
    public static final String ADD_NEW_ADDRESS = "Add New Address Successfully";
    public static final String MESSAGE_FOR_UNREGISTRED_USER = "You Are Not a Registered User!Please Login First..";
    public static final String DoNot_NEW_ADDRESS = "Your Address Do Not Add Successfully";
    public static final String SEND_OTP_MESSAGE = "Your Registration Successfully";
    public static final String DoNot_SEND_OTP_MESSAGE = "OTP NOT Correct.Please Enter Valid OTP ";
    public static final String Dont_SEND_MESSAGE = "OTP Do Not Send Successfully";
    public static final String Dont_GetAddress_MESSAGE = "Some Problem For Geting Address";
    public static final String No_DATA_FOUND_MESSAGE = "No Record Found";

    public static final String usersingup = "usersingup.php";
    public static final String login = "login.php";
    public static final String adminLogin = "adminLogin.php";
    public static final String forgetpassword = "forgetpassword.php";
    public static final String getsurakshacavach = "getsurakshacavach.php";
    public static final String surakshacavach = "surakshacavach.php";


    public static final String addCategory = "addCategory.php";
    public static final String getAllCategory = "getAllCategory.php";
    public static final String getAllSubCategory = "getAllSubCategory.php";
    public static final String getAllSocialOrgCategory = "getAllSocialOrgCategory.php";
    public static final String getPoliceCategory = "getPoliceCategory.php";
    public static final String getAllBusinessCategory = "getAllBusinessCategory.php";
    public static final String getAllBusinessSubCat = "getAllBusinessSubCat.php";
    public static final String getAllSubSocialOrgCate = "getAllSubSocialOrgCate.php";
    public static final String getPoliceSubCategory = "getPoliceSubCategory.php";
    public static final String updateCategory = "updateCategory.php";
    public static final String deleteCategory = "deleteCategory.php";

    public static final String addDistrict = "addDistrict.php";
    public static final String getAllDistrict = "getAllDistrict.php";
    public static final String updateDistrict = "updateDistrict.php";
    public static final String deleteDistrict = "deleteDistrict.php";

    public static final String addPhone = "addPhone.php";
    public static final String getAllPhone = "getAllPhone.php";
    public static final String getAllSubCategoryPhone = "getAllSubCategoryPhone.php";
    public static final String updatePhone = "updatePhone.php";
    public static final String deletePhone = "deletePhone.php";

    public static final String addAdvise = "addAdvise.php";
    public static final String getAdvise = "getAdvise.php";
    public static final String updateAdvise = "updateAdvise.php";
    public static final String deleteAdvise = "deleteAdvise.php";

    public static final String getAllLogin = "getAllLogin.php";
    public static final String uploadimage = "uploadimage.php";

    public static final String getAllComplents = "getAllComplents.php";
    public static final String AddComplents = "AddComplents.php";


    public static final String SearchsurakshaNo = "SearchsurakshaNo.php";
    public static final String getState = "getState.php";
}
