/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.repository;

import com.mytravel.model.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Fahim Feroje Al Jami
 */
public interface StatusRepository extends JpaRepository<Status, Long>{
    
    @Query("SELECT st FROM Status st WHERE st.user.id = ?1")
    List<Status> findStatusesByUserId(int userid);
    
    @Query("SELECT st FROM Status st WHERE st.statusVisibility = 1")
    List<Status> findAllPublicStatuses();
    
}
