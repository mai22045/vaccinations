package gr.uom.vaccination;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import gr.uom.vaccination.model.*;

@Configuration
public class VaccinationConfig implements CommandLineRunner {

	@Autowired
	private VaccinationService appService;

	@Override
	public void run(String... args) throws Exception {

		/* Create Doctors */
		Doctor d1 = new Doctor(111, "John", "Doe");
		appService.addDoctor(d1);
		Doctor d2 = new Doctor(222, "Stephen", "Greene");
		appService.addDoctor(d2);
		Doctor d3 = new Doctor(333, "Keith", "Abraham");
		appService.addDoctor(d3);
		Doctor d4 = new Doctor(444, "Cameron", "Scott");
		appService.addDoctor(d4);
		Doctor d5 = new Doctor(555, "Thomas", "Russel");
		appService.addDoctor(d5);
		Doctor d6 = new Doctor(666, "Jason", "Campbell");
		appService.addDoctor(d6);

		/* Create Citizens */
		Citizen citizen1 = new Citizen(111, 111, "Christopher", "Sutherland", "christopher@uom.gr");
		appService.addCitizen(citizen1);
		Citizen citizen2 = new Citizen(222, 222, "George", "Smith", "george@uom.gr");
		appService.addCitizen(citizen2);
		Citizen citizen3 = new Citizen(333, 333, "Warren", "Martin", "warren@uom.gr");
		appService.addCitizen(citizen3);
		Citizen citizen4 = new Citizen(444, 444, "Nathan", "Avery", "nathan@uom.gr");
		appService.addCitizen(citizen4);
		Citizen citizen5 = new Citizen(555, 555, "Justin", "Kerr", "justin@uom.gr");
		appService.addCitizen(citizen5);
		Citizen citizen6 = new Citizen(666, 666, "Jason", "Skinner", "jason@uom.gr");
		appService.addCitizen(citizen6);
		Citizen citizen7 = new Citizen(777, 777, "Connor", "Morgan", "connor@uom.gr");
		appService.addCitizen(citizen7);
		Citizen citizen8 = new Citizen(888, 888, "Cameron", "Arnold", "cameron@uom.gr");
		appService.addCitizen(citizen8);
		Citizen citizen9 = new Citizen(999, 999, "Keith", "oliver", "keith@uom.gr");
		appService.addCitizen(citizen9);
		Citizen citizen10 = new Citizen(101010, 101010, "James", "Milner", "james@uom.gr");
		appService.addCitizen(citizen10);
		Citizen citizen11 = new Citizen(111111, 111111, "Frank", "Linkers", "frank@uom.gr");
		appService.addCitizen(citizen11);
		Citizen citizen12 = new Citizen(121212, 121212, "Phil", "Jackson", "phil@uom.gr");
		appService.addCitizen(citizen12);

		/* Create Centers */
		Center center1 = new Center("C1", "Egnatia 105");
		appService.addCenter(center1);
		Center center2 = new Center("C2", "Tsimiski 22");
		appService.addCenter(center2);
		Center center3 = new Center("C3", "Mitropoleos 45");
		appService.addCenter(center3);
		Center center4 = new Center("C4", "Plastira 88");
		appService.addCenter(center4);

		/* Create Timeslots */

		int numberOfDays = 10;
		int numberOfTimeslotsPerDayForDoctors = 3;

		long counterStart = System.currentTimeMillis();
		long counterEnd = counterStart + 300000;

		for (int j = 0; j < numberOfDays; j++) {
			/* Randomly skip some days and dont add timeslots */
			/* Timeslots for 1st doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d1.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Timeslots for 2nd doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d2.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Timeslots for 3rd doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d3.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Timeslots for 4th doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d4.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Timeslots for 5th doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d5.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Timeslots for 6th doctor */
			for (int i = 0; i < numberOfTimeslotsPerDayForDoctors; i++) {
				Timestamp timestampStart = new Timestamp(counterStart);
				Timestamp timestampEnd = new Timestamp(counterEnd);
				Timeslot t = new Timeslot(timestampStart, timestampEnd, d6.getAmka());
				appService.addTimeslot(t);
				counterStart += 360000;
				counterEnd += 360000;
			}

			/* Change to next day */
			counterStart = System.currentTimeMillis() + ((j + 1) * 86400000);
			counterEnd = counterStart + 300000;
		}


		/* Create Vaccination */
		Timestamp vaccinationDate;
		LocalDateTime sixMonthsFromNow;
		Timestamp vaccinationExpiryDate;

		vaccinationDate = new Timestamp(System.currentTimeMillis());
		sixMonthsFromNow = LocalDate.now().plusMonths(6).atTime(0, 0);
		vaccinationExpiryDate = Timestamp.valueOf(sixMonthsFromNow);
		Vaccination v1 = new Vaccination(vaccinationDate, citizen7.getAmka(), d1.getAmka(), vaccinationExpiryDate);
		appService.addVaccination(v1);

	}

}
