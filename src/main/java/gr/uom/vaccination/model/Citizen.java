package gr.uom.vaccination.model;

import jakarta.persistence.*;

@Entity
public class Citizen {
	
	@Id
    private Integer amka;
    private Integer afm;
    private String name;
    private String surname;
    private String email;
    
    public Citizen() {
    	
    }

    public Citizen(Integer amka, Integer afm, String name, String surname, String email) {
		this.amka = amka;
		this.afm = afm;
		this.name = name;
		this.surname = surname;
		this.email = email;
	}

	public Integer getAfm() {
        return afm;
    }

    public void setAfm(Integer afm) {
        this.afm = afm;
    }

    public Integer getAmka() {
        return amka;
    }

    public void setAmka(Integer amka) {
        this.amka = amka;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
