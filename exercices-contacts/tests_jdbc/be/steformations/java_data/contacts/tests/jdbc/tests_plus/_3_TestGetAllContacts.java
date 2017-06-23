package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.JdbcContact;

public class _3_TestGetAllContacts {

	@Test
	public void TestNombreTotalDeTags() {
		try {
			ContactJdbcManagerPlus db = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("ContexteExercicesSupplementaires.getGestionContactJDBCSupplementaire()", db);
			
			List<JdbcContact> contacts = db.getAllContacts();
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectAllContacts() != null", contacts);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectAllContacts().size() == ContexteExercicesSupplementaires.getGestionContactJDBCSupplementaire().selectNombreTotalDeContacts()", 
					     contacts.size(), 
					     db.countContacts());
			
			int count = 0;
			for (JdbcContact c : contacts) {
				assertNotNull(c);
				assertNotNull("Un object ContactJDBC doit avoir un prenom", c.getFirstname());
				assertNotNull("Un object ContactJDBC doit avoir un nom", c.getName());
				assertNotNull("Un object ContactJDBC doit avoir un email", c.getEmail());
				if ( c.getFirstname().equalsIgnoreCase("Betty")
					 || c.getFirstname().equalsIgnoreCase("Sally") 
					 || c.getFirstname().equalsIgnoreCase("Jessica")) {
					count++;
				}
			}
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectAllContacts() contient les contacts 'Betty', 'Sally' et 'Jessica'", 
					     3, count);
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
