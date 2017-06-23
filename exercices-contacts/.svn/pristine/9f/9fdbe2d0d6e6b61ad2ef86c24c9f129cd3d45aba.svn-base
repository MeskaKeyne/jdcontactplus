package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.JdbcContact;

public class _4_TestGetContactsWithNamedEmail {

	@Test
	public void TestNombreTotalDeTags() {
		try {
			ContactJdbcManagerPlus db = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire()", db);
			
			List<JdbcContact> contacts = db.getContactsWithNamedEmail();
			assertNotNull(contacts);
			
			int count = 0;
			for (JdbcContact c : contacts) {
				assertNotNull(c);
				if ( c.getFirstname().equalsIgnoreCase("Betty")
					 || c.getFirstname().equalsIgnoreCase("Jessica")) {
					count++;
				}
			}
			
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectContactsAvecEmailNominatif() contient les contacts 'Betty' et 'Jessica'", 
					     2, count);
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
