package de.htw.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Users {

    @Id
    private int id;
    
    private String password;
    private String firstname;
    private String lastname;
    private boolean isAdmin;
    
    public Users() {}
    
    public Users(int id, String lastname, String firstname, String password, boolean isadmin) {
    	this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.password = password;
        this.isAdmin = isadmin;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
   
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }   
}

