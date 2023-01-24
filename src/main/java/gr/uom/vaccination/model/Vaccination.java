package gr.uom.vaccination.model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
public class Vaccination {

	@Id
	@Column(name = "citizen_amka", nullable = false)
	private Integer citizenAmka;
	private Timestamp vaccinationDate;
    private Integer doctorAmka;
    private Timestamp expiryDate;
    
    public Vaccination() {
    	
    }
    
    public Vaccination(Timestamp vaccinationDate, Integer citizenAmka, Integer doctorAmka, Timestamp expiryDate) {
		this.vaccinationDate = vaccinationDate;
		this.citizenAmka = citizenAmka;
		this.doctorAmka = doctorAmka;
		this.expiryDate = expiryDate;
	}

	public Integer getCitizen() {
        return citizenAmka;
    }

    public void setCitizen(Integer citizen) {
        this.citizenAmka = citizen;
    }

    public Integer getDoctor() {
        return doctorAmka;
    }

    public void setDoctor(Integer doctor) {
        this.doctorAmka = doctor;
    }

    public Timestamp getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Timestamp vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}
