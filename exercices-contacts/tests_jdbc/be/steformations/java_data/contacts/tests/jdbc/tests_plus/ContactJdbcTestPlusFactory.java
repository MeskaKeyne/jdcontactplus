package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.tunsajan.jd.contactplus.ContactManager;

public class ContactJdbcTestPlusFactory {
	
	public static ContactJdbcManagerPlus getGestionContactJDBCSupplementaire() {
		return new ContactManager();
	}
}
