package gr.uom.vaccination.model;


import javax.persistence.Entity;

@Entity
public class Center {

    private String code;

    private String address;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
