package gr.uom.vaccination.model;

public class Citizen {

    private Integer afm;
    private Integer amka;
    private String name;
    private String surname;
    private String email;

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
