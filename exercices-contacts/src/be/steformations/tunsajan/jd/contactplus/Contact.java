package be.steformations.tunsajan.jd.contactplus;

import be.steformations.java_data.contacts.interfaces.jdbc_plus.JdbcContact;

public class Contact implements JdbcContact {
	private int _id;
	private String _firstname;
	private String _name;
	private String _email;
	private int _pays;

	public Contact(int id, String fn, String name, String email, int pays){
		this._id = id;
		this._firstname = fn;
		this._name = name;
		this._email = email;
		this._pays = pays;
	}
	@Override
	public String getFirstname() {
		// TODO Auto-generated method stub
		return this._firstname;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this._name;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return this._email;
	}
	public int getID(){
		return this._id;
	}

}
