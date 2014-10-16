package jk.web.workers;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class UserWorkerTest {

	@Test
	public void parseBirthdayTest() throws ParseException {
		Date date = UserWorker.parseBirthday(2014, 9, 13);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		assertEquals(cal.get(Calendar.YEAR), 2014);
		assertEquals(cal.get(Calendar.MONTH), 9);
		assertEquals(cal.get(Calendar.DAY_OF_MONTH), 13);
	}

}
