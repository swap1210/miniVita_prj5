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
public class Funding implements Serializable{
    // Attributes of the Funding class
    private String name;
    private double amount;
    private FundingEntity fundingEnity;
        
    // Getters and setters for the attributes of the Funding class
    public String getName() {
        return name;
    }

    public Funding() {
        this.name = "";
        this.amount = 0;
        this.fundingEnity = new FundingEntity();
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FundingEntity getFundingEnity() {
        return fundingEnity;
    }

    public void setFundingEnity(FundingEntity fundingEnity) {
        this.fundingEnity = fundingEnity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.amount+10));
        hash = 79 * hash + Objects.hashCode(this.fundingEnity);
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
        final Funding other = (Funding) obj;
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.fundingEnity, other.fundingEnity);
    }
    
}
