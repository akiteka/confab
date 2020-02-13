package model;

public class Users {
	private String name;
	private String pass;

public Users(String name) {
	this.name=name;
}

public Users(String name, String pass) {
	this.name=name;
	this.pass=pass;
}
public String getName() {return name;}
public String getPass() {return pass;}

}
