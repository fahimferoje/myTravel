/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.repository;

import com.mytravel.model.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Fahim Feroje Al Jami
 */
public interface LocationRepository extends JpaRepository<Location, Integer>{
    
    List<Location> findAll();
    
}
