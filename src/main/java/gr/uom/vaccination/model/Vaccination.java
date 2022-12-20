package gr.uom.vaccination.model;

import java.sql.Timestamp;

public class Vaccination {

    private Citizen citizen;
    private Doctor doctor;
    private Timestamp vaccinationDate;
    private Timestamp expiryDate;

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
