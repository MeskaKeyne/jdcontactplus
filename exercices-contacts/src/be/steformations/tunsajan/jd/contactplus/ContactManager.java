package be.steformations.tunsajan.jd.contactplus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.ContactJdbcManagerPlus;
import be.steformations.java_data.contacts.interfaces.jdbc_plus.JdbcContact;

public class ContactManager implements ContactJdbcManagerPlus {
	
	private String url ="jdbc:postgresql://prim2014-14.fapse.priv:5432/contact";
	//private String url ="jdbc:postgresql://localhost:5432/contact";
	private String user = "postgres";
	private String pwd = "postgres";

	
	
	
	private Connection open(){
		Connection con=null;
		try {
			con = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {e.printStackTrace();}
		return con;
	}
	private void close(Connection con){
		try {
			con.close();
		} catch (SQLException e) {e.printStackTrace();}
	}
	@Override
	public int countTags() {
		Connection con = this.open();
		PreparedStatement req =null;
		String sql = 
					"SELECT count(tags.id) "
				+ 	"FROM tags ";
		try {
			req = con.prepareStatement(sql);
			ResultSet r = req.executeQuery();
			close(con);
			if(r.next())
			return r.getInt(1);
			else return -1;
		} catch (SQLException e) {e.printStackTrace();}
		return -1;
	}
	@Override
	public int countContacts() {
		Connection con = this.open();
		PreparedStatement req =null;
		String sql = 
					"SELECT count(contacts.id) "
				+ 	"FROM contacts ";
		try {
			req = con.prepareStatement(sql);
			ResultSet r = req.executeQuery();
			close(con);
			if(r.next())
			return r.getInt(1);
			else return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	

	/**
	 * Recherche de tous les contacts
	 * @return une java.util.List de tous les ste.contacts.interfaces.jdbc.ContactJDBC 
	 */


	@Override
	public List<JdbcContact> getAllContacts() {
		return new ArrayList<JdbcContact>(this.getContacts());
	}
	
	
	public List<Contact> getContacts() {
		Connection con = this.open();
		PreparedStatement req =null;
		String sql = 
					"SELECT * "
				+ 	"FROM contacts ";
		try {
			req = con.prepareStatement(sql);
			ResultSet r = req.executeQuery();
			List<Contact>  liste= new ArrayList<Contact>();
			close(con);
			while(r.next()){
				int id =r.getInt("id");
				String nom= r.getString("nom");
				String prenom = r.getString("prenom");
				String email = r.getString("email");
				int pays = r.getInt("pays");
				if(nom!= null && prenom !=null && email != null)
					liste.add(new Contact(id, prenom, nom, email , pays));
				
			}
			return liste;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	@Override
	public List<JdbcContact> getContactsWithNamedEmail() {
		List<JdbcContact> liste = new ArrayList<JdbcContact>();
		List<JdbcContact> listeContact=this.getAllContacts();
		Iterator<JdbcContact>  it =listeContact.iterator();
		while(it.hasNext())
		{
			JdbcContact c = it.next();
			if(c.getEmail().contains(c.getName().toLowerCase())
					&& c.getEmail().contains(c.getFirstname().toLowerCase())) 
				liste.add(c);
		}
		return liste;
	}

	@Override
	public Map<String, List<JdbcContact>> getContactsByEmailDomains() {
	
		Map<String, List<JdbcContact>> map = new HashMap<String, List<JdbcContact>>();
		List<JdbcContact> liste ;
		List<JdbcContact> listeContact = this.getAllContacts();
		Iterator<JdbcContact>  it =listeContact.iterator();
		while(it.hasNext())
		{
			JdbcContact c = it.next();
			String email = c.getEmail();
			int start = email.indexOf("@");
			String key = email.substring(start+1, email.length());
			if(key.contains(".")){
				if(map.containsKey(key)){
					liste = map.get(key);
					liste.add(c);
				}
				else {
					List<JdbcContact> newListe =new ArrayList<JdbcContact>();
					newListe.add(c);
					map.put(key, newListe);
				}
			}
			
		}
		return map;
	}

	@Override
	public Map<String, Map<String, Boolean>> getAllContactsTagsRelationships() {
		Map<String, Map<String, Boolean>>  map = new HashMap<String, Map<String, Boolean>>();
		List<Tag> listeTag = this.getAllTags();
		List<Contact> listeContact = this.getContacts();
		Iterator<Contact>  it =listeContact.iterator();
		while(it.hasNext())
		{
			Contact c = it.next();
			List<Integer> listeIDTag = this.idTagByContact(c.getID());
			Map<String, Boolean> mapTag = new HashMap<String, Boolean>();
			for(int i = 0; i< listeTag.size(); i++){
				Tag t = listeTag.get(i);
				mapTag.put(t.name(), listeIDTag.contains(t.id()));
			}
			map.put(c.getFirstname(), mapTag);
		}
		return map;
	}
	public List<Tag> getAllTags() {
		Connection con = this.open();
		PreparedStatement req =null;
		String sql = 
					"SELECT * "
				+ 	"FROM tags ";
		try {
			req = con.prepareStatement(sql);
			ResultSet r = req.executeQuery();
			List<Tag>  liste= new ArrayList<Tag>();
			close(con);
			while(r.next()){
				int id =r.getInt("id");
				String nom= r.getString("tag");
				liste.add(new Tag(id, nom));
			}
			return liste;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	public List<Integer> idTagByContact(int idContact){

		Connection con = this.open();
		PreparedStatement req =null;
		String sql = 
					"SELECT tag "
				+ 	"FROM contacts_tags "
				+ "WHERE contact = ?";
		try {
			req = con.prepareStatement(sql);
			req.setInt(1, idContact);
			ResultSet r = req.executeQuery();
			List<Integer>  liste= new ArrayList<Integer>();
			close(con);
			while(r.next()){
				int id =r.getInt("tag");
				liste.add(id);
			}
			return liste;
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
}
