/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytravel.service;

/**
 *
 * @author Fahim Feroje Al Jami
 */
//import com.user.login.model.Role;
import com.mytravel.model.Location;
import com.mytravel.model.Role;
import com.mytravel.model.Status;
import com.mytravel.model.StatusForm;
import com.mytravel.model.User;
import com.mytravel.repository.LocationRepository;
import com.mytravel.repository.RoleRepository;
import com.mytravel.repository.StatusRepository;
import com.mytravel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    private final LocationRepository locationRepository;
    
    private final StatusRepository statusRepository;
    
    @Autowired
    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            LocationRepository locationRepository1,
            StatusRepository statusRepository1,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.locationRepository = locationRepository1;
        this.roleRepository = roleRepository;
        
        this.statusRepository = statusRepository1;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        
        userRepository.save(user);
    }
    
    public List<User> findDummyUsers(){
        System.out.println("Lookinng for dummy ysers");
        return userRepository.findDummyUsers();
    }
    
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }
    
    public void saveStatus(StatusForm statusForm, User user, List<Location> locations) {
        
        if(statusForm.getLocation() == null || statusForm.getLocation().isEmpty()){
            return;
        }
        
        Optional<Location> locOp = locations.stream()
                .filter(loc -> loc.getName().equals(statusForm.getLocation()))
                .findFirst();
        
        if(!locOp.isPresent()){
            return;
        }
        
        Location location = locOp.get();
        
        System.out.println("Found location "+location.getName());
        System.out.println("Found location id "+location.getId());
        
        Status status = new Status();
        status.setStatusDescription(statusForm.getStatusDesc());
        status.setUser(user);
        status.setLocation(location);
        status.setStatusVisibility(statusForm.getStatusVisibility() == 0 ? Status.StatusVisibility.PRIVATE 
                : Status.StatusVisibility.PUBLIC);
        
        statusRepository.save(status);
        
    }
    
    public List<Status> getAllStatusByUserId(int userId) {
        return statusRepository.findStatusesByUserId(userId);
    }
    
    
    

}
