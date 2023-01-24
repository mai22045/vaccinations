package gr.uom.vaccination.model;

import java.sql.Timestamp;
import java.util.Optional;

import jakarta.persistence.*;

@Entity
public class Timeslot {

	@Id
	@SequenceGenerator(name = "timeslot_seq", sequenceName = "timeslot_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeslot_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	private Timestamp start;
	private Timestamp end;
	private Integer doctorAmka;

	@Transient
	private Optional<Doctor> doctor;

	public Timeslot() {

	}

	public Timeslot(Timestamp start, Timestamp end, Integer doctor) {
		this.start = start;
		this.end = end;
		this.doctorAmka = doctor;
	}

	public Long getId() {
		return id;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public Optional<Doctor> getDoctor() {
		return doctor;
	}

	public void setDoctor(Optional<Doctor> d) {
		this.doctor = d;
	}

	public Integer getDoctorAmka() {
		return doctorAmka;
	}

	public void setDoctorAmka(Integer doctor) {
		this.doctorAmka = doctor;
	}

	@Override
	public String toString() {
		return "Timeslot: [id: " + id + ", start: " + start + ", end: " + end + ", doctorAmka: " + doctorAmka + "]";
	}

}