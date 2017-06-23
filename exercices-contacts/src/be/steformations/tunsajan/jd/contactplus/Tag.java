package be.steformations.tunsajan.jd.contactplus;

public class Tag {
	int _id;
	String _name;
	
	public Tag(int id, String nom){
		this._id = id;
		this._name = nom;
	}
	public int id(){
		return this._id;
	}
	public String name(){
		return this._name;
	}

}
