package be.steformations.java_data.contacts.interfaces.jdbc_plus;

import java.util.List;
import java.util.Map;

public interface ContactJdbcManagerPlus {
	
	/**
	 * Recherche le nombre total de tags
	 * @return le nombre total de tags
	 */
	int countTags();
	
	/**
	 * Recherche le nombre total de contacts
	 * @return le nombre total de contacts
	 */
	int countContacts();

	/**
	 * Recherche de tous les contacts
	 * @return une java.util.List de tous les ste.contacts.interfaces.jdbc.ContactJDBC 
	 */
	List<JdbcContact> getAllContacts();
	
	/**
	 * Recherche les contacts dont l'email contient les prénom et nom
	 * @return une java.util.List de tous les ste.contacts.interfaces.jdbc.ContactJDBC dont l'email contient les prénom et nom
	 */
	List<JdbcContact> getContactsWithNamedEmail();
	
	/**
	 * Recherche les contacts par nom de domaines
	 * @return un HashMap dont les clefs sont tous les noms de domaines des adresses emails et les valeurs les java.util.List des ste.contacts.interfaces.jdbc.ContactJDBC dont le domaine de l'email correspond � la clef 
	 */
	Map<String, List<JdbcContact>> getContactsByEmailDomains();
	
	/**
	 * Recherche les relations d'association entre tous les contacts et tous les tags
 	 * @return un HashMap dont les clefs sont tous les prénoms de TOUS les contacts et la valeur associée a chacun de ces pr�noms est un java.util.Map reprenant comme clefs TOUS les noms de tags et comme valeur soit true si le contact est associ� au tag soit false dans le cas contraire
	 */
	Map<String, Map<String, Boolean>> getAllContactsTagsRelationships();
}
