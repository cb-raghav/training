package phonedirectory;

public class ContactDetails {
    private String address;
    private String mobileNum, homeNum, workNum;

    public ContactDetails(String address, String mobileNum, String homeNum, String workNum) {
        this.address = address;
        this.mobileNum = mobileNum;
        this.homeNum = homeNum;
        this.workNum = workNum;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getHomeNum() {
        return homeNum;
    }

    public void setHomeNum(String homeNum) {
        this.homeNum = homeNum;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }
    
    
}
