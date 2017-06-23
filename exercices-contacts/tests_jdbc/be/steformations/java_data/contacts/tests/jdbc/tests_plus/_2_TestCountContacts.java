package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;

public class _2_TestCountContacts {

	@Test
	public void TestNombreTotalDeTags() {
		try {
			ContactJdbcManagerPlus db = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("ContexteExercicesSupplementaires.getGestionContactJDBCSupplementaire()", db);
			
			int nbMembres = 0;
			try {
				nbMembres = db.countContacts();
			} catch(Exception e) {}
			assertTrue( "IGestionContactJDBCSupplementaire.selectNombreTotalDeContacts() > 0", nbMembres > 0 );
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
