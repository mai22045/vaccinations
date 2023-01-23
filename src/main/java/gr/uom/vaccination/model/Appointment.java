package gr.uom.vaccination.model;

import java.util.Optional;

import jakarta.persistence.*;

@Entity
public class Appointment {

	@Id
	@SequenceGenerator(name = "appointment_seq", sequenceName = "appointment_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
	@Column(name = "id", nullable = false)
	private Long id;
	private Integer doctorAmka;
    private Long timeslotId;
    private Integer citizenAmka;
    
	@Transient
	private Optional<Citizen> citizen;
	@Transient
	private Optional<Timeslot> timeslot;
	@Transient
	private Optional<Doctor> doctor;
    
    public Appointment() {
    	
    }
    
	public Appointment(Long timeslot, Integer citizen, Integer doctor) {
		this.timeslotId = timeslot;
		this.citizenAmka = citizen;
		this.doctorAmka = doctor;
	}

	public Long getId() {
		return id;
	}

	public Long getTimeslot() {
		return timeslotId;
	}

	public void setTimeslot(Long timeslot) {
		this.timeslotId = timeslot;
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

	public Optional<Citizen> getAppointmentCitizen() {
		return citizen;
	}

	public void setAppointmentCitizen(Optional<Citizen> c) {
		this.citizen = c;
	}

	public Optional<Timeslot> getAppointmentTimeslot() {
		return timeslot;
	}

	public void setAppointmentTimeslot(Optional<Timeslot> t) {
		this.timeslot = t;
	}

	public Optional<Doctor> getAppointmentDoctor() {
		return doctor;
	}

	public void setAppointmentDoctor(Optional<Doctor> d) {
		this.doctor = d;
	}
	
	@Override
	public String toString() {
		return "Appointment [ doctor: " + doctorAmka +  ", citizen: " + citizenAmka + ", timeslot: " + timeslotId + "]";
	}
	
}
