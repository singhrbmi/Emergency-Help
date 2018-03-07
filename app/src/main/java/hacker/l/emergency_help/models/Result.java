package hacker.l.emergency_help.models;

/**
 * Created by lalitsingh on 05/03/18.
 */

public class Result {
    private String name;
    private String phone;

    public  Result(String name, String phone) {
        this.name = name;
        this.phone = phone;
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


}
