/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.model;

import lombok.Data;

/**
 *
 * @author Fahim Feroje Al Jami
 */
public class StatusForm {
    private int statusVisibility;
    private String location;
    
    private String statusDesc;

    public int getStatusVisibility() {
        return statusVisibility;
    }

    public void setStatusVisibility(int statusVisibility) {
        this.statusVisibility = statusVisibility;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    
    
    
    
}
