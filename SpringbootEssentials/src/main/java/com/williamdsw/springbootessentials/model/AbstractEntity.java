package com.williamdsw.springbootessentials.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author William
 */

@MappedSuperclass
public class AbstractEntity implements Serializable
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    //------------------------------------------------------------------------//
    // GETTERS / SETTERS
    
    public Long getId ()
    {
        return id;
    }

    public void setId (Long id)
    {
        this.id = id;
    }

    //------------------------------------------------------------------------//
    // OVERRIDED FUNCTIONS
    
    @Override
    public int hashCode ()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode (this.id);
        return hash;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass () != obj.getClass ())
        {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (!Objects.equals (this.id, other.id))
        {
            return false;
        }
        return true;
    }
}