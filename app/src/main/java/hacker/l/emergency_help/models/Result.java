package hacker.l.emergency_help.models;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class Result {
    private String name;
    private String phone;
    private String offName;
    private String offNumber;

    public String getNameOff() {
        return offName;
    }

    public void setNameOff(String offName) {
        this.offName = offName;
    }

    public String getOffPhone() {
        return offNumber;
    }

    public void setOffPhone(String offNumber) {
        this.offNumber = offNumber;
    }


    private String Username;
    private String UserPhone;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    private String EmailId;
    private String Address;
    private String City;
    private String PinCode;
    private String EmergencyOne;
    private String EmergencyTwo;
    private String EmergencyThree;

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    private String locality;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getEmergencyOne() {
        return EmergencyOne;
    }

    public void setEmergencyOne(String emergencyOne) {
        EmergencyOne = emergencyOne;
    }

    public String getEmergencyTwo() {
        return EmergencyTwo;
    }

    public void setEmergencyTwo(String emergencyTwo) {
        EmergencyTwo = emergencyTwo;
    }

    public String getEmergencyThree() {
        return EmergencyThree;
    }

    public void setEmergencyThree(String emergencyThree) {
        EmergencyThree = emergencyThree;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private String barCode;
    private String Password;

    public String getSocialUs() {
        return socialUs;
    }

    public void setSocialUs(String socialUs) {
        this.socialUs = socialUs;
    }

    private String socialUs;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    private String district;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    private int districtId;

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public int getSocialNoId() {
        return socialNoId;
    }

    public void setSocialNoId(int socialNoId) {
        this.socialNoId = socialNoId;
    }

    private String socialName;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    private int loginId;
    private int phoneId;

    public int getAdviseId() {
        return adviseId;
    }

    public void setAdviseId(int adviseId) {
        this.adviseId = adviseId;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    private int adviseId;
    private String advise;

    public String getComplent() {
        return complent;
    }

    public void setComplent(String complent) {
        this.complent = complent;
    }

    private String complent;

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    private int scid;
    private int socialNoId;

    public Result(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Result(String name, String phone, String offName, String offNumber) {
        this.name = name;
        this.phone = phone;
        this.offNumber = offNumber;
        this.offName = offName;
    }

    public Result() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(int phoneId) {
        this.phoneId = phoneId;
    }
}
