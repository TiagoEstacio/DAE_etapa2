/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import auxCategories.UserType;
import static auxCategories.UserType.UNDEFINED;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Entity
@Table(name = "USERS",
uniqueConstraints =
@UniqueConstraint(columnNames = {"USERNAME"}))
@NamedQueries({
    @NamedQuery(
        name="getAllUsers",
        query="SELECT us FROM User us ORDER BY us.id"
    )
})
public abstract class User implements Serializable {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    
    @NotNull
    protected String username;
    
    @NotNull
    protected String password;
   
    @NotNull
    protected String name;
    
    @NotNull
    protected final UserType type;
    
    @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    protected String email;
        
    public User() {
        this.type = UNDEFINED;
    }

    public User(String username, String password, String name, String email, UserType type ){
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }
    
    
    
    @Override
    public String toString() {
        return "entities.User[id=" + id + "]: "+ name;
    }

}
