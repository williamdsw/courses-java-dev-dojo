package com.williamdsw.springbootessentials.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author William
 */
public class PageableResponse<T> extends PageImpl<T>
{
    //------------------------------------------------------------------------//
    // FIELDS
    
    private boolean last;
    private boolean first;
    private int totalPages;
    
    //------------------------------------------------------------------------//
    // CONSTRUCTORS
    
    public PageableResponse ()
    {
        super (new ArrayList<> ());
    }
    
    @JsonCreator (mode = JsonCreator.Mode.PROPERTIES)
    public PageableResponse 
    (
        @JsonProperty ("content") List<T> content, 
        @JsonProperty ("number") Integer number,
        @JsonProperty ("size") Integer size,
        @JsonProperty ("totalElements") Long totalElements, 
        @JsonProperty ("pageable") JsonNode pageable, 
        @JsonProperty ("first") boolean first, 
        @JsonProperty ("last") boolean last, 
        @JsonProperty ("totalPages") Integer totalPages, 
        @JsonProperty ("sort") JsonNode sort, 
        @JsonProperty ("numberOfElements") Integer numberOfElements)
    {
        //super (content, PageRequest.of (number, size), totalElements);
        super (content, PageRequest.of (number, size, Sort.by (sort.toString ())), totalElements);
    }
    
    //------------------------------------------------------------------------//
    // OVERRIDED GETTERS / SETTERS

    @Override
    public boolean isLast ()
    {
        return last;
    }

    public void setLast (boolean last)
    {
        this.last = last;
    }

    @Override
    public boolean isFirst ()
    {
        return first;
    }

    public void setFirst (boolean first)
    {
        this.first = first;
    }

    @Override
    public int getTotalPages ()
    {
        return totalPages;
    }

    public void setTotalPages (int totalPages)
    {
        this.totalPages = totalPages;
    }
}