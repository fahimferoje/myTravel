/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    
    @Column(name = "status_description", columnDefinition = "TEXT")
    private String statusDescription;
    
    public enum StatusVisibility {
        PRIVATE,
        PUBLIC
    }
    
    @Enumerated(EnumType.ORDINAL)
    private StatusVisibility statusVisibility;
    
    @ManyToOne
    private Location location;
    
    @ManyToOne
    private User user;
    
    @Column(columnDefinition = "boolean default false", nullable = false)
    private Boolean pinned = false;

}
