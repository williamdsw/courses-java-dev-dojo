package com.williamdsw.springbootessentials.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

/**
 * @author William
 */

@Entity
public class User extends AbstractEntity
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    @NotEmpty (message = "Username is required")
    @Column (unique = true)
    private String username;
    
    @NotEmpty (message = "Password is required")
    @JsonIgnore
    private String password;
    
    @NotEmpty (message = "Name is required")
    private String name;
    
    @NotEmpty (message = "Permission is required")
    private boolean isAdmin;

    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    public User () {}
    public User (String username, String password, String name, boolean isAdmin)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    //------------------------------------------------------------------------//
    // GETTERS / SETTERS
    
    public String getUsername ()
    {
        return username;
    }

    public void setUsername (String username)
    {
        this.username = username;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public boolean getIsAdmin ()
    {
        return isAdmin;
    }

    public void setIsAdmin (boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }
}