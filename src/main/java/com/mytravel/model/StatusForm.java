/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.model;

/**
 *
 * @author Fahim Feroje Al Jami
 */
public class StatusForm {
    private int statusVisibility;
    private String location;
    
    private String statusDesc;
    
    private String statusVisibilityString;
    
    private String userName;
    
    private int locationId;
    
    private int id;

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

    public String getStatusVisibilityString() {
        return statusVisibilityString;
    }

    public void setStatusVisibilityString(String statusVisibilityString) {
        this.statusVisibilityString = statusVisibilityString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
}
