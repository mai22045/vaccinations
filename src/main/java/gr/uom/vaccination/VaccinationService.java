package gr.uom.vaccination;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.uom.vaccination.model.*;

@Service
public class VaccinationService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private CenterRepository centerRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private TimeslotRepository timeslotRepository;

	@Autowired
	private VaccinationRepository vaccinationRepository;

	public String test() {
		return "TEST-Test";
	}

	public List<Doctor> getAllDoctors() throws Exception {
		return doctorRepository.findAll();
	}

	public void addDoctor(Doctor d) throws Exception {
		Optional<Doctor> byId = doctorRepository.findById(d.getAmka());
		if (!byId.isPresent())
			doctorRepository.save(d);
	}
	
	public boolean doctorExists(int amka) throws Exception {
		Optional<Doctor> byId = doctorRepository.findById(amka);
		return byId.isPresent();
	}

	public List<Citizen> getAllCitizens() throws Exception {
		return citizenRepository.findAll();
	}

	public void addCitizen(Citizen c) throws Exception {
		Optional<Citizen> byId = citizenRepository.findById(c.getAmka());
		if (!byId.isPresent())
			citizenRepository.save(c);
	}

	public boolean citizenExists(int amka) throws Exception {
		Optional<Citizen> byId = citizenRepository.findById(amka);
		return byId.isPresent();
	}

	public List<Center> getAllCenters() throws Exception {
		return centerRepository.findAll();
	}

	public void addCenter(Center c) throws Exception {
		Optional<Center> byId = centerRepository.findById(c.getCode());
		if (!byId.isPresent())
			centerRepository.save(c);
	}

	public List<Timeslot> getAllFreeTimeslots(int day, int month, int year) throws Exception {
		List<Timeslot> tList = timeslotRepository.findAll();
		List<Appointment> aList = appointmentRepository.findAll();
		List<Timeslot> response = new ArrayList<>();

		Long[] bookedTimeslots = new Long[aList.size()];
		for (int j = 0; j < aList.size(); j++) {
			bookedTimeslots[j] = aList.get(j).getTimeslot();
		}

		for (int i = 0; i < tList.size(); i++) {
			Timestamp certainDate = Timestamp.valueOf(year + "-" + month + "-" + day + " 11:11:11");
			Timestamp timeslotStart = tList.get(i).getStart();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			boolean timeslotIsForCertainDate = fmt.format(certainDate).equals(fmt.format(timeslotStart));
			if (!Arrays.asList(bookedTimeslots).contains(tList.get(i).getId()) && timeslotIsForCertainDate) {
				Integer dAmka = tList.get(i).getDoctorAmka();
				Timeslot t = tList.get(i);
				Optional<Doctor> d = doctorRepository.findById(dAmka);
				t.setDoctor(d);
				response.add(t);
			}
		}
		return response;
	}

	public long findTimeslotByStartDate(Timestamp timestamp, int doctor) throws Exception {
		List<Timeslot> tList = timeslotRepository.findAll();
		long id = 0;

		for (int i = 0; i < tList.size(); i++) {
			Calendar c1 = Calendar.getInstance();
			c1.setTimeInMillis(timestamp.getTime());
			Calendar c2 = Calendar.getInstance();
			c2.setTimeInMillis(tList.get(i).getStart().getTime());
			boolean sameDayHourAndTime = c1.get(Calendar.MINUTE) == c2.get(Calendar.MINUTE)
					&& c1.get(Calendar.HOUR) == c2.get(Calendar.HOUR)
					&& c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)
					&& c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
					&& c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
			if (sameDayHourAndTime && tList.get(i).getDoctorAmka().equals(doctor)) {
				return tList.get(i).getId();
			}
		}
		return id;
	}

	public long findPreviousAppointmentByCitizenAmka(int citizenAmka) throws Exception {
		List<Appointment> aList = appointmentRepository.findAll();
		long id = 0;
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getCitizen().equals(citizenAmka)) {
				return aList.get(i).getId();
			}
		}
		return id;
	}

	public void addTimeslot(Timeslot t) throws Exception {
		timeslotRepository.save(t);
		t.setDoctor(doctorRepository.findById(t.getDoctorAmka()));
	}

	public void addVaccination(Vaccination v) throws Exception {
		vaccinationRepository.save(v);
	}

	public Optional<Vaccination> getCitizenVaccination(Integer citizenId) {
		return vaccinationRepository.findById(citizenId);
	}

	public Appointment createAppointment(Appointment a) {
		Appointment a1 = appointmentRepository.save(a);
		a1.setAppointmentCitizen(citizenRepository.findById(a.getCitizen()));
		a1.setAppointmentTimeslot(timeslotRepository.findById(a.getTimeslot()));
		a1.setAppointmentDoctor(doctorRepository.findById(a.getDoctor()));
		return a1;
	}

	public Appointment updateAppointment(Long id, Appointment a) {
		appointmentRepository.deleteById(id);
		appointmentRepository.save(a);
		return a;
	}

	public List<Appointment> getDoctorAppointments(Integer doctorId) {
		List<Appointment> aList = appointmentRepository.findAll();
		List<Appointment> response = new ArrayList<>();
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getDoctor().equals(doctorId)) {
				aList.get(i).setAppointmentCitizen(citizenRepository.findById(aList.get(i).getCitizen()));
				aList.get(i).setAppointmentTimeslot(timeslotRepository.findById(aList.get(i).getTimeslot()));
				aList.get(i).setAppointmentDoctor(doctorRepository.findById(aList.get(i).getDoctor()));
				response.add(aList.get(i));
			}
		}
		return response;
	}

	public List<Appointment> getDoctorAppointmentsForToday(Integer doctorId) {
		List<Timeslot> tList = timeslotRepository.findAll();
		List<Appointment> aList = appointmentRepository.findAll();
		List<Appointment> response = new ArrayList<>();

		for (int i = 0; i < aList.size(); i++) {
			for (int j = 0; j < tList.size(); j++) {
				if (aList.get(i).getTimeslot().equals(tList.get(j).getId())) {
					Timestamp today = new Timestamp(System.currentTimeMillis());
					Timestamp timeslotStart = tList.get(j).getStart();
					SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
					boolean appointmentIsForToday = fmt.format(today).equals(fmt.format(timeslotStart));
					if (aList.get(i).getDoctor().equals(doctorId) && appointmentIsForToday) {
						aList.get(i).setAppointmentCitizen(citizenRepository.findById(aList.get(i).getCitizen()));
						aList.get(i).setAppointmentTimeslot(timeslotRepository.findById(aList.get(i).getTimeslot()));
						aList.get(i).setAppointmentDoctor(doctorRepository.findById(aList.get(i).getDoctor()));
						response.add(aList.get(i));
						break;
					} else {
						break;
					}
				}
			}
		}
		return response;
	}

}
