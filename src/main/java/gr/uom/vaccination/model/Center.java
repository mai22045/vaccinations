package gr.uom.vaccination.model;

import jakarta.persistence.*;

@Entity
public class Center {

	@Id
    private String code;
    private String address;
    
    public Center() {
    	
    }

    public Center(String code, String address) {
		this.code = code;
		this.address = address;
	}

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
