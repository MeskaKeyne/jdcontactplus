package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;

public class _1_TestCountTags {

	@Test
	public void TestNombreTotalDeTags() {
		try {
			ContactJdbcManagerPlus db = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("ContexteExercicesSupplementaires.getGestionContactJDBCSupplementaire()", db);
			
			int nbTags = 0;
			try {
				nbTags = db.countTags();
			} catch(Exception e) {}
			assertTrue( "IGestionContactJDBCSupplementaire.selectNombreTotalDeTags() > 0", nbTags > 0 );
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
