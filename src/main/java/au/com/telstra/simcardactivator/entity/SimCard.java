package au.com.telstra.simcardactivator.entity;


import javax.persistence.*;

@Entity
public class SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String iccid;
    private String customerEmail;
    private boolean active;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
