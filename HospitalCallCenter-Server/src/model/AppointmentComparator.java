package model;

import java.util.Comparator;

public class AppointmentComparator implements Comparator<Appointment> {
	@Override
	public int compare(Appointment a1, Appointment a2) {
		int dateComparison = a1.getDate().compareTo(a2.getDate());

		if (dateComparison == 0) {
			return a1.getTime().compareTo(a2.getTime());
		}

		return dateComparison;
	}
}
