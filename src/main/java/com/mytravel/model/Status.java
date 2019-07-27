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

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

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
    
    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    //private Set<Role> roles;
    
    @ManyToOne
    private Location location;
    
    @ManyToOne
    private User user;

}
