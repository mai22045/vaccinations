package gr.uom.vaccination.model;

import jakarta.persistence.*;

@Entity
public class Doctor {

	@Id
	private Integer amka;
	private String name;
	private String surname;

	public Doctor() {

	}

	public Doctor(Integer amka, String name, String surname) {
		this.amka = amka;
		this.name = name;
		this.surname = surname;
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
}
