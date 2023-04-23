/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author swapn
 */
public class FundingEntity implements Serializable{
    String name;
    int establishedIn;

    public FundingEntity() {
        this.name = "";
        this.establishedIn = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEstablishedIn() {
        return establishedIn;
    }

    public void setEstablishedIn(int establishedIn) {
        this.establishedIn = establishedIn;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + (this.establishedIn+20) ;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FundingEntity other = (FundingEntity) obj;
        if (this.establishedIn != other.establishedIn) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
    
    
}
