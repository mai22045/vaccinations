package gr.uom.vaccination;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.uom.vaccination.model.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VaccinationController {

	@Autowired
	private VaccinationService service;

	@GetMapping(path = "/test")
	public String getTest() {
		return service.test();
	}

	@GetMapping(path = "/doctors")
	public List<Doctor> getDoctors() throws Exception {
		return service.getAllDoctors();
	}

	@GetMapping(path = "/citizens")
	public List<Citizen> getCitizens() throws Exception {
		return service.getAllCitizens();
	}

	@GetMapping(path = "/centers")
	public List<Center> getCenters() throws Exception {
		return service.getAllCenters();
	}

	@GetMapping(path = "/timeslots/{day}/{month}/{year}")
	public List<Timeslot> getFreeTimeslots(@PathVariable int day, @PathVariable int month, @PathVariable int year)
			throws Exception {
		return service.getAllFreeTimeslots(day, month, year);
	}

	@PostMapping(path = "/timeslots")
	public ResponseEntity<Timeslot> createTimeslot(@RequestBody HashMap<String, Object> dataBody) throws Exception {
		Object doctorAmka = dataBody.get("doctorAmka");
		Object doctorName = dataBody.get("doctorFirstName");
		Object doctorSurname = dataBody.get("doctorLastName");
		Object start = dataBody.get("start");
		Object end = dataBody.get("end");
		/* Check if doctor with given amka exists */
		boolean doctorExists = service.doctorExists((int) doctorAmka);
		Timeslot t;
		if (doctorExists) {
			t = new Timeslot(new Timestamp((long) start), new Timestamp((long) end), (int) doctorAmka);
			service.addTimeslot(t);
		} else {
			Doctor d = new Doctor((int) doctorAmka, (String) doctorName, (String) doctorSurname);
			service.addDoctor(d);
			t = new Timeslot(new Timestamp((long) start), new Timestamp((long) end), (int) doctorAmka);
			service.addTimeslot(t);
		}
		return new ResponseEntity<>(t, HttpStatus.CREATED);
	}

	@PostMapping(path = "/appointment")
	public ResponseEntity<Appointment> createAppointment(@RequestBody HashMap<String, Object> dataBody)
			throws Exception {
		Object doctorAmka = dataBody.get("doctor");
		Object citizenAmka = dataBody.get("citizen");
		Object timeslot = dataBody.get("timeslot");
		Object firstName = dataBody.get("firstName");
		Object lastName = dataBody.get("lastName");
		/* Check if citizen with given amka exists */
		boolean citizenExists = service.citizenExists((int) citizenAmka);
		Appointment a;
		if (citizenExists) {
			Long timeslotId = service.findTimeslotByStartDate(new Timestamp((long) timeslot), (int) doctorAmka);
			if (timeslotId == 0) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			} else {
				a = new Appointment(timeslotId, (int) citizenAmka, (int) doctorAmka);
				service.createAppointment(a);
				return new ResponseEntity<>(a, HttpStatus.CREATED);
			}
		} else {
			/* Create citizen as it doesn't exist */
			Citizen c = new Citizen((int) citizenAmka, (int) citizenAmka, (String) firstName, (String) lastName,
					((String) firstName).toLowerCase() + "@citizen.uom.gr");
			service.addCitizen(c);
			Long timeslotId = service.findTimeslotByStartDate(new Timestamp((long) timeslot), (int) doctorAmka);
			if (timeslotId == 0) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			} else {
				a = new Appointment(timeslotId, (int) citizenAmka, (int) doctorAmka);
				service.createAppointment(a);
				return new ResponseEntity<>(a, HttpStatus.CREATED);
			}
		}
	}

	@PutMapping(path = "/appointment/{citizen}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long citizen,
			@RequestBody HashMap<String, Object> dataBody) throws Exception {
		Object doctorAmka = dataBody.get("doctor");
		Object citizenAmka = dataBody.get("citizen");
		Object timeslot = dataBody.get("timeslot");
		Object firstName = dataBody.get("firstName");
		Object lastName = dataBody.get("lastName");
		/* Check if citizen with given amka exists */
		boolean citizenExists = service.citizenExists((int) citizenAmka);
		if (citizenExists) {
			Long timeslotId = service.findTimeslotByStartDate(new Timestamp((long) timeslot), (int) doctorAmka);
			if (timeslotId == 0) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			} else {
				Long appointmentId = service.findPreviousAppointmentByCitizenAmka((int) citizenAmka);
				if (appointmentId == 0) {
					return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				} else {
					Appointment appointment = service.updateAppointment(appointmentId,
							new Appointment(timeslotId, (int) citizenAmka, (int) doctorAmka));
					return new ResponseEntity<>(appointment, HttpStatus.CREATED);
				}
			}
		} else {
			/* Create citizen as it doesn't exist */
			Citizen c = new Citizen((int) citizenAmka, (int) citizenAmka, (String) firstName, (String) lastName,
					((String) firstName).toLowerCase() + "@citizen.uom.gr");
			service.addCitizen(c);
			/*
			 * Citizen was created just now, previous appointment does not exist return null
			 * response
			 */
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/vaccination/{citizenId}")
	public Optional<Vaccination> getCitizenVaccination(@PathVariable Integer citizenId) throws Exception {
		return service.getCitizenVaccination(citizenId);
	}

	@PostMapping(path = "/vaccination")
	public ResponseEntity<Vaccination> createVaccination(@RequestBody HashMap<String, Object> dataBody)
			throws Exception {
		Object doctorAmka = dataBody.get("doctor");
		Object doctorFirstName = dataBody.get("doctorFirstName");
		Object doctorLastName = dataBody.get("doctorLastName");
		Object vaccDate = dataBody.get("vaccinationDate");
		Object expDate = dataBody.get("expiryDate");
		Object citizenAmka = dataBody.get("citizenAmka");
		Object citizenName = dataBody.get("citizenName");
		Object citizenSurname = dataBody.get("citizenSurname");
		Object citizenAfm = dataBody.get("citizenAfm");
		Object citizenEmail = dataBody.get("citizenEmail");
		/* Check if doctor with given amka exists */
		boolean doctorExists = service.doctorExists((int) doctorAmka);
		if (doctorExists) {
			/* Check if citizen exists */
			boolean citizenExists = service.citizenExists((int) citizenAmka);
			Vaccination v;
			if (citizenExists) {
				v = new Vaccination(new Timestamp((long) vaccDate), (int) citizenAmka, (int) doctorAmka,
						new Timestamp((long) expDate));
				service.addVaccination(v);
			} else {
				/* Create citizen as it doesn't exist */
				Citizen c = new Citizen((int) citizenAmka, (int) citizenAfm, (String) citizenName,
						(String) citizenSurname, ((String) citizenEmail).toLowerCase());
				service.addCitizen(c);
				v = new Vaccination(new Timestamp((long) vaccDate), (int) citizenAmka, (int) doctorAmka,
						new Timestamp((long) expDate));
				service.addVaccination(v);
			}
			return new ResponseEntity<>(v, HttpStatus.CREATED);
		} else {
			Doctor d = new Doctor((int) doctorAmka, (String) doctorFirstName, (String) doctorLastName);
			service.addDoctor(d);
			/* Check if citizen exists */
			boolean citizenExists = service.citizenExists((int) citizenAmka);
			Vaccination v;
			if (citizenExists) {
				v = new Vaccination(new Timestamp((long) vaccDate), (int) citizenAmka, (int) doctorAmka,
						new Timestamp((long) expDate));
				service.addVaccination(v);
			} else {
				/* Create citizen as it doesn't exist */
				Citizen c = new Citizen((int) citizenAmka, (int) citizenAfm, (String) citizenName,
						(String) citizenSurname, ((String) citizenEmail).toLowerCase());
				service.addCitizen(c);
				v = new Vaccination(new Timestamp((long) vaccDate), (int) citizenAmka, (int) doctorAmka,
						new Timestamp((long) expDate));
				service.addVaccination(v);
			}
			return new ResponseEntity<>(v, HttpStatus.CREATED);
		}
	}

	@GetMapping(path = "/appointments/today/{doctorId}")
	public List<Appointment> getDoctorAppointmentsForToday(@PathVariable Integer doctorId) throws Exception {
		return service.getDoctorAppointmentsForToday(doctorId);
	}

	@GetMapping(path = "/appointments/all/{doctorId}")
	public List<Appointment> getDoctorAppointments(@PathVariable Integer doctorId) throws Exception {
		return service.getDoctorAppointments(doctorId);
	}
}
