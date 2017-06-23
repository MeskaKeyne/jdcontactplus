package be.steformations.java_data.contacts.tests.jdbc.tests_plus;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import be.steformations.java_data.contacts.interfaces.jdbc.ContactJdbcManager;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.java_data.contacts.tests.jdbc.tests.ContactJdbcTestFactory;

public class _6_TestGetAllContactsTagsRelationships {

	@Test
	public void TestNombreTotalDeTags() {
		try {
			ContactJdbcManager gestionContact = ContactJdbcTestFactory.getContactJdbcManager();
			assertNotNull("FabriqueJDBC.getGestionContactJDBC()", gestionContact);
			
			ContactJdbcManagerPlus gestionSupplementaire = ContactJdbcTestPlusFactory.getGestionContactJDBCSupplementaire();
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire()", gestionSupplementaire);
			
			String[] tags = new String[] { "sexy", "féline" };
			long millis = System.currentTimeMillis();
			
			int pkHolly = 0;
			int nbTags = 0;
			int nbContacts = 0;
			Map<String, Map<String, Boolean>> relations = null;
			
			try {
				pkHolly = gestionContact.createAndSaveContact("Holly" + millis, "Robinson", "catwoman2@gotham.com", "US", tags);
				nbTags = gestionSupplementaire.countTags();
				nbContacts = gestionSupplementaire.countContacts();
				relations = gestionSupplementaire.getAllContactsTagsRelationships();
			} catch(Exception e) {}
			
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags()", relations);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().size()", nbContacts, relations.size());
			
			Map<String, Boolean> relationsHolly = relations.get("Holly" + millis);
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ")", relationsHolly);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").size()", nbTags, relationsHolly.size());
			assertTrue("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"sexy\")", relationsHolly.get("sexy"));
			assertTrue("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"féline\")", relationsHolly.get("féline"));
			assertFalse("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"vamp\")", relationsHolly.get("vamp"));
			
			relations = null;
			nbTags = 0;
			nbContacts = 0;
			try {
				gestionContact.removeContact(pkHolly);
				pkHolly = gestionContact.createAndSaveContact("Holly" + millis, "Robinson", "catwoman2@gotham.com", "US", null);
				nbTags = gestionSupplementaire.countTags();
				nbContacts = gestionSupplementaire.countContacts();
				relations = gestionSupplementaire.getAllContactsTagsRelationships();
			} catch(Exception e) {
				e.printStackTrace();
			}
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags()", relations);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().size()", nbContacts, relations.size());
			
			relationsHolly = relations.get("Holly" + millis);
			assertNotNull("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ")", relationsHolly);
			assertEquals("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").size()", nbTags, relationsHolly.size());
			assertFalse("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"sexy\")", relationsHolly.get("sexy"));
			assertFalse("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"féline\")", relationsHolly.get("féline"));
			assertFalse("FabriqueJDBCSupplementaire.getGestionContactJDBCSupplementaire().selectRelationsEntreContactsEtTags().get(\"Holly\"" + millis + ").get(\"vamp\")", relationsHolly.get("vamp"));
			
			gestionContact.removeContact(pkHolly);

		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
