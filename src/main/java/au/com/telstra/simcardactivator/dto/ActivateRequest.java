package au.com.telstra.simcardactivator.dto;

public class ActivateRequest {
    private String iccid;
    private String customerEmail;

    public ActivateRequest(){

    }

    public ActivateRequest(String iccid, String customerEmail){
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
}
