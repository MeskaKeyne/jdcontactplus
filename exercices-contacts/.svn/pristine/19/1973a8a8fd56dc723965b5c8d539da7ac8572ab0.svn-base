package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc.ContactJdbcManager;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.JdbcContact;
import be.steformations.java_data.contacts.tests.jdbc.tests.ContactJdbcTestFactory;


public class _5_TestGetContactsByEmailDomains {

	@Test
	public void testSelectContactParDomaine() {
		try {
			ContactJdbcManager gestionContact = ContactJdbcTestFactory.getContactJdbcManager();
			assertNotNull("FabriqueJDBC.getGestionContactJDBC()", gestionContact);
			
			ContactJdbcManagerPlus gestionSupplementaire = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire()", gestionSupplementaire);
			
			long millis = System.currentTimeMillis();
			int pkHolly = 0;
			int pkPrincess = 0;
			Map<String, List<JdbcContact>> domaines = null;
			
			try {
				pkHolly = gestionContact.createAndSaveContact("Holly" + millis, "Robinson", "catwoman2@comics.com", "US", null);
				pkPrincess = gestionContact.createAndSaveContact("Princesse" + millis, "Pandha", "pandha@comics.com", "US", null);
				domaines = gestionSupplementaire.getContactsByEmailDomains();
			} catch(Exception e) { 
				e.printStackTrace(); 
			} finally {
				try {
					gestionContact.removeContact(pkPrincess);
					gestionContact.removeContact(pkHolly);
				} catch(Exception e) {}
			}
			
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectContactParDomaine() != null", domaines);
			List<JdbcContact> comicsdotcom = domaines.get("comics.com");
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectContactParDomaine().get(\"comics.com\") != null", comicsdotcom);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectContactParDomaine().get(\"comics.com\").size() == 2", 2, comicsdotcom.size());
			for (JdbcContact contactJDBC : comicsdotcom) {
				String prenom = contactJDBC.getFirstname();
				assertTrue( "FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectContactParDomaine().get(\"comics.com\") ne contient que 'Holly'" + millis + "' et 'Princesse" + millis + "'", 
						    prenom.equals("Holly" + millis) || prenom.equals("Princesse" + millis) );
			}
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
